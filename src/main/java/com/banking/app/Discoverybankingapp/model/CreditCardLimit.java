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
@Table(name="Credit_Card_Limit")
public class CreditCardLimit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String clientAccountNumber;
    @OneToOne
    @JoinColumn
    @MapsId
    private ClientAccount clientAccount;

    private double accountLimit;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }




}
