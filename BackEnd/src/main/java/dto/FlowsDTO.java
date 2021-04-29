
package dto;

import entities.Flow;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author danie
 */
public class FlowsDTO {
    
    private List<FlowDTO> all = new ArrayList();
    
    public FlowsDTO(List<Flow> flows) {
        for (Flow flow : flows) {
            all.add(new FlowDTO(flow));
        }
    }
    
    public List<FlowDTO> getFlowDTO(){
        return all;
    }
    
}
