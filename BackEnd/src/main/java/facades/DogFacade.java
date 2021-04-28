/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import dto.BreedDTO;
import entities.Dog;
import entities.Searches;
import entities.User;
import fetch.BreedFetcher;
import fetch.BreedImageFetcher;
import fetch.FactsFetcher;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utils.HttpUtils;

/**
 *
 * @author Bruger
 */
public class DogFacade {

    private static EntityManagerFactory emf;
    private static DogFacade instance;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final ExecutorService threadPool = HttpUtils.getThreadPool();

    private DogFacade() {
    }

    public static DogFacade getDogFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogFacade();

        }
        return instance;

    }

    public JSONObject getDog(String breed) throws MalformedURLException, IOException, ParseException {
        EntityManager em = emf.createEntityManager();

        JSONObject obj = BreedFetcher.getBreedByName(breed);
        JSONObject facts = FactsFetcher.getFact();
        JSONObject img = BreedImageFetcher.getBreedImage(breed);
        Searches search = new Searches(breed);

        try {
            em.getTransaction().begin();
            em.persist(search);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        //List list = new ArrayList();

        Object fact = facts.get("facts");

        obj.put("fact", fact);
        obj.put("img", img.get("image"));

        return obj;

    }

    public Dog addDog(String name, String dateOfBirth, String info, String breed, String activeUser) {
        EntityManager em = emf.createEntityManager();
        Dog dog;
        
        try {
            em.getTransaction().begin();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.userName =:name", User.class)
            .setParameter("name", activeUser);    
            User owner = query.getSingleResult();
            dog = new Dog(name, dateOfBirth, info, breed);
            owner.getDogList().add(dog);
            
            em.persist(dog);
            em.merge(owner);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return dog;

    }
   
    public List<User> getDogsByPerson(String activeUser){
        EntityManager em = emf.createEntityManager();
        List<User> list = new ArrayList<>();
        try {
            em.getTransaction().begin();
      
            TypedQuery<User> query2 = em.createQuery("SELECT u.dogList FROM User u WHERE u.userName =:name", User.class).setParameter("name", activeUser);
            list = query2.getResultList();
            
        
        } finally {
            em.close();
        }
        return list;
    }
}
