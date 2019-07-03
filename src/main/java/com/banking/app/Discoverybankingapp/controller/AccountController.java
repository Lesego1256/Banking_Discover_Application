package com.banking.app.Discoverybankingapp.controller;


import com.banking.app.Discoverybankingapp.Services.AccountServices;
import com.banking.app.Discoverybankingapp.model.*;
import com.banking.app.Discoverybankingapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController
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



//    @GetMapping("/getAccounts/{id}")
//    public List<ClientAccount> getAccounts(@PathVariable("id") Long id)
//    {
//        return accountServices.getAccounts(id);
//    }


    @GetMapping("/getAll")
    List<Client> getAll()
    {
        return repo.findAll();
    }

    @GetMapping("/getAllAc")
    List<ClientAccount> getAccounts()
    {
        return cl.findAll();
    }

    @GetMapping("/getAccounts/{id}")
    public List<ClientAccount> getAccounts(@PathVariable("id") int id)
    {
        return accountServices.getAllAccountsForUserT(id);
    }

    @GetMapping("/getAllTransactionalAccs/{id}")
    public List<ClientAccount> getAllTransactionalAccs(@PathVariable("id") int id)
    {
        return accountServices.getAllTransactionalAccs(id);
    }




    @GetMapping("/getATMs")
    public List<AtmAllocation> getATMs()//(@PathVariable("id") int id)
    {
        return atm.findAll();
    }

    @GetMapping("/getCurrencyValueWithAccounts/{id}")
    public List<ClientAccount> getCurrencyValueWithAccounts(@PathVariable("id") int id)
    {
        return accountServices.getForCurrenyAccounts(id);
    }

    @GetMapping("/getConver")
    public List<CurrencyConversionRate> getConver()
    {
        return currencyConversionRateRepository.findAll();
    }

//    @GetMapping("/getCurrency")
//    public List<Currency> getC()
//    {
//        return currencyRepository.findAll();
//    }



}
