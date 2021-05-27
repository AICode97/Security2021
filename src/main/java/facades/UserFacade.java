package facades;

import dto.UserDTO;
import entities.Role;
import entities.User;
import errorhandling.InvalidInputException;

import java.rmi.UnexpectedException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User addUser(String username, String password, String email) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {

            user = new User(username, password, email);
            Role userRole = new Role("user");
            user.addRole(userRole);

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return user;
    }



    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);

            user = (User) query.getSingleResult();
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid username or password");
            }
        } finally {
            em.close();
        }
        return user;
    }



    public User getUserByUsername(String username) throws UnexpectedException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);

            user = (User) query.getSingleResult();
            if (user == null) {
                throw new UnexpectedException("Invalid username");
            }
        } finally {
            em.close();
        }
        return user;
    }







    public User deleteUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id ", User.class
            );
            query.setParameter(
                    "id", userId);
            User p = query.getSingleResult();

            em.remove(p);

            em.getTransaction()
                    .commit();
            return p;
        } finally {
            em.close();
        }

    }

    public List<User> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            List<User> allUsers = em.createQuery("SELECT  u.email from User u", User.class
            )
                    .getResultList();
            return allUsers;
        } finally {
            em.close();
        }

    }

}
