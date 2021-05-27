package utils;


import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    User user = new User("BrianPedersen123", "detheltBrian","BrianPedersen@gmail.com");
    User user1 = new User("JensMikkelsen99", "jensjens123","JensMikkelsen@gmail.com");
    User user2 = new User("SusanneOlsen23", "SusOl","SusanneOlsen@gmail.com");

    User admin = new User("MikkelHansen23", "elskerhåndbold","MikkelH@business.com");


    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");


    user.addRole(userRole);
    user1.addRole(userRole);
    user2.addRole(userRole);

    admin.addRole(adminRole);



    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(user1);
    em.persist(user2);
    em.persist(admin);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("detheltBrian"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
