package com.banking.app.Discoverybankingapp.repository;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Admin
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;


    @Test
    public void bjgjhghgh() {
//        List<Long> clients = clientRepository.getAllClients(); --
//        //List<Client> clientss = clientRepository.findAll();
//        assertThat(clients.size()).isGreaterThan(0); --
//        //assertThat(clientss.size()).isGreaterThan(0);
    }
}
