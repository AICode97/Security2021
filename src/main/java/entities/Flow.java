
package entities;

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
@Table(name = "flow")
public class Flow implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flow_id", updatable = false, nullable = false)
    private Long id;
    
    
    @Size(min = 1, max = 40)
    @Column(name = "name")
    private String name;
    
    
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    
    @ManyToMany
    private List<User> user;

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

   
    
    public Flow(List<User> user, String name, String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }

    public Flow() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
