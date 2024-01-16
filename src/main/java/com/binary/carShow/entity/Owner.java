package com.binary.carShow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long OwnerId;
    private String firstName;
    private String lastname;
    @OneToMany(cascade= CascadeType.ALL,mappedBy = "owner")
    @JsonIgnore
    private List<Car>cars;

    public Owner() {
    }

    public Owner(String firstName, String lastname) {
        this.firstName = firstName;
        this.lastname = lastname;
    }

    public Long getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(Long ownerId) {
        OwnerId = ownerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    //One to One
    // Many to One
    //Many to Many

}
