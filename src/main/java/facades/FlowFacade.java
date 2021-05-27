
package facades;

import dto.FlowDTO;
import dto.FlowsDTO;
import entities.Flow;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author danie
 */
public class FlowFacade {
    
    private static EntityManagerFactory emf;
    private static FlowFacade instance;

    public FlowFacade() {
    }
    
    public static FlowFacade getFlowFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FlowFacade();
        }
        return instance;
    }

    public Flow addFlow(String name, String description) {
        EntityManager em = emf.createEntityManager();
        Flow flow;
        try {
            flow = new Flow(name, description);
            em.getTransaction().begin();
            em.persist(flow);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return flow;
    }
    
    public FlowsDTO getAllFlows(){
        EntityManager em = emf.createEntityManager();
        
        FlowsDTO flows = new FlowsDTO(em.createQuery("SELECT f FROM Flow f").getResultList());
        return flows;
  
    }
}
