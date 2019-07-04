/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.app.Discoverybankingapp.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Admin
 */
@Entity
@Data
@Table(name = "Currency_Conversion_Rate")
public class CurrencyConversionRate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;


    @Column(name = "CONVERSION_INDICATOR")
    private String conversionIndicator;

    private double rate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    public String getConversionIndicator() {
        return conversionIndicator;
    }

    public void setConversionIndicator(String conversionIndicator) {
        this.conversionIndicator = conversionIndicator;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
