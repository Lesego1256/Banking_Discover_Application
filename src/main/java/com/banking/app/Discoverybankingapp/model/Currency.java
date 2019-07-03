/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.app.Discoverybankingapp.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

/**
 *
 * @author Admin
 */
@Entity
@Data
@Table(name="Currency")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="CURRENCY_CODE")
    private String currencyCode;
    @Column(name="DECIMAL_PLACES")
    private Double decimalPlaces;
    @Column(name="DESCRIPTION")
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(Double decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }
}
