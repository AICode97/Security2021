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
    @Transient
    private String secret = "enmegetlangoghemmeligkode";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "userId", columnDefinition = "VARCHAR(255)")
    private String id;


    @Column(name = "userEmail")
    private String email;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "userName")
    private String userName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "userPassword")
    private String userPass;
    
    @Basic(optional = true)
    @Column(name = "profileImg")
    private String profileImg;
    
    @JoinTable(name = "userRoles", joinColumns = {
    @JoinColumn(name = "id", referencedColumnName = "userId")}, inverseJoinColumns = {
    @JoinColumn(name = "userRole", referencedColumnName = "roleId")})
    
    
    
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();
    
    @ManyToMany(mappedBy = "user")
    private List<Flow> flows;


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
    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.userPass);
    }



    public User(String userName, String userPass, String email) {
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass.concat(secret) , BCrypt.gensalt(12));
        this.email = email;
      
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return this.userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt(12));
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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public List<Flow> getFlows() {
        return flows;
    }

    public void setFlows(List<Flow> flows) {
        this.flows = flows;
    }
    

}
