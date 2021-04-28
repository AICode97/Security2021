/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.shaded.json.JSONObject;
import entities.Searches;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;

import java.util.concurrent.ExecutorService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 *
 * @author Bruger
 */
public class SearchFacade {

    private static EntityManagerFactory emf;
    private static SearchFacade instance;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final ExecutorService threadPool = HttpUtils.getThreadPool();

    private SearchFacade() {

    }

    public static SearchFacade getSearchFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SearchFacade();

        }
        return instance;

    }

    public Long getSearches(String breed) {
      EntityManager em = emf.createEntityManager();
      Long result = null; 
      try{
          em.getTransaction().begin();
          Query query = em.createQuery("SELECT COUNT(s) FROM Searches s WHERE s.breedName =:breed", Searches.class)
          .setParameter("breed", breed);
          result = (Long) query.getSingleResult();
          em.getTransaction().commit();
          
      }finally{
          em.close();
      }
      
         
        
        return result;
    }
    
    
    public Long getAllSearches() {
      EntityManager em = emf.createEntityManager();
      Long result = null; 
      try{
          em.getTransaction().begin();
          Query query = em.createQuery("SELECT COUNT(s) FROM Searches s", Searches.class);
          result = (Long) query.getSingleResult();
          em.getTransaction().commit();
          
      }finally{
          em.close();
      }
      
         
        
        return result;
    }
    
    public List<HashMap> getAllDogSearches() {
        EntityManager em = emf.createEntityManager();
        List<HashMap> grandList = new ArrayList();
        try{
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT  distinct(s.breedName) FROM Searches s", Searches.class);
            List<String> response = query.getResultList();
            for(String s : response){
                HashMap newMap = new HashMap();
                long result = getSearches(s);
                newMap.put("breed", s);
                newMap.put("value", result);
                grandList.add(newMap);
            }
        }finally{
            em.close();
        }
        return grandList;
    }

}
