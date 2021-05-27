
package entities;

import org.eclipse.persistence.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author danie
 */
@Entity
@UuidGenerator(name="uuid2")
@Table(name = "flow")
public class Flow implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "flowId", columnDefinition = "VARCHAR(255)")
    private String id;
    
    
    @Size(min = 1, max = 40)
    @Column(name = "name")
    private String name;
    
    
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    

    
    public Flow(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Flow() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
