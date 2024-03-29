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
@Table(name = "CLIENT_TYPE")
public class ClientType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CLIENT_TYPE_CODE")
    private String clientTypeCode;
    @Column(name = "DESCRIPTION")
    private String description;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}
