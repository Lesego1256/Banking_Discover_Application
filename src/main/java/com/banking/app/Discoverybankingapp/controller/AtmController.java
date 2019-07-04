package com.banking.app.Discoverybankingapp.controller;

import com.banking.app.Discoverybankingapp.Services.AccountServices;
import com.banking.app.Discoverybankingapp.model.ClientAccount;
import com.banking.app.Discoverybankingapp.repository.AtmAllocationRepository;
import com.banking.app.Discoverybankingapp.repository.ClientAccountRepository;
import com.banking.app.Discoverybankingapp.repository.ClientRepository;
import com.banking.app.Discoverybankingapp.repository.CurrencyConversionRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/atm")
public class AtmController
{
    @Autowired
    private AccountServices accountServices;


    @Autowired
    private ClientRepository repo;

    @Autowired
    private ClientAccountRepository cl;


    @Autowired
    private AtmAllocationRepository atm;


    @Autowired
    private CurrencyConversionRateRepository currencyConversionRateRepository;

//    @GetMapping("/makeAWithdrawalll/{atm_id}/{client_id}/{account}/{amount}")
//    public boolean makeWithdrawa1l(@PathVariable("atm_id") int atm_id, @PathVariable("client_id") int client_id,
//                                         @PathVariable("account") String account, @PathVariable("amount") double amount)
//    {
//        System.out.println("In method");
//        return accountServices.makeWithDrawal(atm_id,client_id,account,amount);
//    }


//    @GetMapping("/getStatusesssss/{amount}/{atm_id}")
//    public boolean  getStatussss(@PathVariable("amount") double amount, @PathVariable("atm_id") int atm_id)
//    {
//        return accountServices.checkingNotes(amount,atm_id);
//    }
}
