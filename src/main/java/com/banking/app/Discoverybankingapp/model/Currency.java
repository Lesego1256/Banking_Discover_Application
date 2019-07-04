/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.app.Discoverybankingapp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Admin
 */
@Entity
@Data
@Table(name = "Currency")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CURRENCY_CODE", nullable = false, referencedColumnName = "CURRENCY_CODE")
    private CurrencyConversionRate currencyCode;

    @Column(name = "DECIMAL_PLACES")
    private Double decimalPlaces;
    @Column(name = "DESCRIPTION")
    private String description;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public CurrencyConversionRate getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyConversionRate currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Double getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(Double decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }
}
