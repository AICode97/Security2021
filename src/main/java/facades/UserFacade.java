package facades;

import dto.UserDTO;
import entities.Role;
import entities.User;
import errorhandling.InvalidInputException;
import errorhandling.NotFoundException;
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

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid username or password");
            }
        } finally {
            em.close();
        }
        return user;
    }


    public String getUserByEmail(String email) {
      EntityManager em = emf.createEntityManager();
      String result = null; 
      try{
          em.getTransaction().begin();
          Query query = em.createQuery("SELECT s.userName FROM User s WHERE s.email =:email", User.class)
          .setParameter("email", email);
          result = (String) query.getSingleResult();
          em.getTransaction().commit();
          
      }finally{
          em.close();
      }
        return result;
    }



    public UserDTO addUser(UserDTO userDTO) throws InvalidInputException {
        EntityManager em = emf.createEntityManager();
        String name = null;
        try {
            Query query = em.createQuery("SELECT u.userName FROM User u WHERE u.userName = :name");
            query.setParameter("name", userDTO.getUsername());
            name = (String) query.getSingleResult();
        } catch (Exception e) {
        }

        if (name != null) {
            throw new InvalidInputException(String.format("The username %s is taken", name));
        }

        try {
            User user = new User(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());

            for (String role : userDTO.getRoles()) {
                user.addRole(new Role(role));
            }

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

            return new UserDTO(user);

        } finally {
            em.close();
        }
    }

    /*public User updateUser(User user) {
    EntityManager em = emf.createEntityManager();
    
    try {
    em.getTransaction().begin();
    em.merge(user);
    em.getTransaction().commit();
    
    return user;
    }
    
    
    */
    

    

    

    

    

    

    

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
            List<User> allUsers = em.createQuery("SELECT  u.userName from User u", User.class
            )
                    .getResultList();
            return allUsers;
        } finally {
            em.close();
        }

    }

}
