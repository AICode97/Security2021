
package facades;

import dto.FlowDTO;
import dto.FlowsDTO;
import entities.Flow;
import entities.User;
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
    
    public Flow AddFlow(FlowDTO dto, User user){
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Flow flow = new Flow(user, dto.getName(), dto.getDescription());
        em.persist(flow);
        em.getTransaction().commit();
        return flow;
    }
    
    public FlowsDTO getAllFlows(){
        EntityManager em = emf.createEntityManager();
        
        FlowsDTO flows = new FlowsDTO(em.createQuery("SELECT f FROM Flow f").getResultList());
        return flows;
  
    }
}
