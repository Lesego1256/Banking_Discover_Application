/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.app.Discoverybankingapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Admin
 */
@Entity
@Data
@Table(name="CLIENT")
@NoArgsConstructor
public class Client implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="CLIENT_ID")
    private Integer clientId;

    @Column(name="TITLE")
    private String title;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="SURNAME")
    private String surname;
    
    @Column(name="DOB")
    private Date dob;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
//    private List<ClientAccount> clientAccounts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_SUB_TYPE_CODE")
    private ClientSubType clientSubType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }



}
