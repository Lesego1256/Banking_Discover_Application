package com.banking.app.Discoverybankingapp.repository;


import com.banking.app.Discoverybankingapp.model.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * @author Admin
 */
@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccount, String> {

    List<ClientAccount> findAllByClient_ClientId(int id);

    ClientAccount findByClientAccountNumberAndClient_ClientId(String account, int id);


//    @Query("Select ca.displayBalance, cr.rate, cu.currencyCode,ac.description,cr.conversionIndicator \n" +
//            "from ClientAccount ca, Currency cu, CurrencyConversionRate cr, AccountType ac\n" +
//            "where cu.currencyCode =  ca.currencyCode\n" +
//            "and cu.currencyCode = cr.currencyCode\n" +
//            "and ac.transactional = true\n" +
//            "and ca.client_id = 1")
//    List<Object[]> getAccountsConverted(int id);


}
