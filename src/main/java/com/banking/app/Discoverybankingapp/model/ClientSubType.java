/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.app.Discoverybankingapp.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Admin
 */
@Entity
@Data
@NoArgsConstructor
@Table(name="CLIENT_SUB_TYPE")
public class ClientSubType implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="CLIENT_SUB_TYPE_CODE")
    private String clientSubTypeCode;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_TYPE_CODE")
    private ClientType clientType;
    
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
