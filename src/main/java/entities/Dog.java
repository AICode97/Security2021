/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Bruger
 */
@Entity
public class Dog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String dateOfBirth;
    private String info;
    private String breed;

   
    
    

    public Dog( String name, String dateOfBirth, String info, String breed) {
        
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.info = info;
        this.breed = breed;
    }

    public Dog() {
    }

    
    
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getInfo() {
        return info;
    }

    public String getBreed() {
        return breed;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
    
}
