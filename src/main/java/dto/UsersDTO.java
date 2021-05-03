
package dto;

import entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author danie
 */
public class UsersDTO {
    
    private List<UserDTO> all = new ArrayList();
    
    public UsersDTO(List<User> users) {
        for (User user : users ){
            all.add(new UserDTO(user));
        }
    }
    
    public List<UserDTO> getUserDTO(){
        return all;
    }
}
