package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.UuidGenerator;
import org.mindrot.jbcrypt.BCrypt;


@Entity
@UuidGenerator(name="uuid2")
@Table(name = "users")

public class User implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "userId", columnDefinition = "VARCHAR(255)" )
    private String id;


    @Column( name = "useremail", unique = true)
    private String email;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "username", unique = true)
    private String username;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;

    
    @JoinTable(name = "user_roles", joinColumns = {
    @JoinColumn(name = "id", referencedColumnName = "userId")}, inverseJoinColumns = {
    @JoinColumn(name = "user_role", referencedColumnName = "role_name")})
    
    
    
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();
    


    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }
    public User() {
    }

    //TODO Change when password is hashed
    public boolean verifyPassword(String userPass) {
        return BCrypt.checkpw(userPass, this.password);
    }



    public User(String username, String password, String email) {
        this.username = username;
        this.password = BCrypt.hashpw(password , BCrypt.gensalt(12));
        this.email = email;
      
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getUserPass() {
        return this.password;
    }

    public void setUserPass(String userPass) {
        this.password = BCrypt.hashpw(userPass, BCrypt.gensalt(12));
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }




    

}