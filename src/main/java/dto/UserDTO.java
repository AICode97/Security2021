
package dto;

import entities.User;
import java.util.List;

/**
 *
 * @author danie
 */
public class UserDTO {
    
    private String username; 
    private String password;
    private String email;
    private List<String> roles;

    

    public UserDTO(String username, String password, String email,  List<String> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }
    
    public UserDTO (User user) {
        this.username = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getUserPass();
        this.roles = user.getRolesAsStrings();
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    
    
}
