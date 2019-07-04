package com.banking.app.Discoverybankingapp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name="ATM")
public class Atm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Integer atm_Id;

    private String name;//10
    private String location; //255




    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getAtm_Id() {
        return atm_Id;
    }

    public void setAtm_Id(Integer atm_Id) {
        this.atm_Id = atm_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}