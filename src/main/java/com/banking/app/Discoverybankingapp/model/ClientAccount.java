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
@Table(name="Client_Account")
public class ClientAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //Credit_Card_LIMIT PK/FK
    @Id
    @Column(name="CLIENT_ACCOUNT_NUMBER")
    private String clientAccountNumber;
    //Client FK (1 to many) 
    //Add the user information based on this class
    @ManyToOne(fetch = FetchType.LAZY)//(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_type_code")
    private AccountType accountType;
    //Currency PK | Currency_Conversion_rate (PK/FK)
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CURRENCY_CODE" , nullable = false, referencedColumnName = "CURRENCY_CODE")
    private Currency currency;



    @Column(name = "DISPLAY_BALANCE")
    private double displayBalance;
    @Column(name="RAND_VALUE")
    private double randValue;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getClientAccountNumber() {
        return clientAccountNumber;
    }

    public void setClientAccountNumber(String clientAccountNumber) {
        this.clientAccountNumber = clientAccountNumber;
    }

    public double getDisplayBalance() {
        return displayBalance;
    }

    public void setDisplayBalance(double displayBalance) {
        this.displayBalance = displayBalance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getDisplay_balance() {
        return displayBalance;
    }

    public void setDisplay_balance(double display_balance) {
        this.displayBalance = displayBalance;
    }

    public double getRandValue() {
        return randValue;
    }

    public void setRandValue(double randValue) {
        this.randValue = randValue;
    }
}
