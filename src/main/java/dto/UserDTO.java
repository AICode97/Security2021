
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
    private int roleId;


    

    public UserDTO(String username, String password, String email, int roleId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
    }
    
    public UserDTO (User user) {
        this.username = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getUserPass();
        this.roleId = user.getRoleId();

        
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
