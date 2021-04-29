
package dto;

import entities.Flow;

/**
 *
 * @author danie
 */
public class FlowDTO {
    
    private String name;
    private String description;

    public FlowDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public FlowDTO(Flow flow) {
        this.name = flow.getName();
        this.description = flow.getDescription();      
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
