package com.banking.app.Discoverybankingapp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name="Denomination_Type")
public class DenominationType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String denominationTypeCode;

    private String description;




    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

