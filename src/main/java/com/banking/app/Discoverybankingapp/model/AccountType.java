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
@Table(name = "Account_Type")
public class AccountType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "account_type_code")
    private String accountTypeCode;
    private boolean transactional;

    private String description;
//    @OneToMany(mappedBy = "accountType")
//    private ClientAccount clientAccount;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getTransactional() {
        return transactional;
    }

    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }
}
