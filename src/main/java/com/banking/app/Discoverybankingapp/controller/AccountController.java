package com.banking.app.Discoverybankingapp.controller;


import com.banking.app.Discoverybankingapp.Services.AccountServices;
import com.banking.app.Discoverybankingapp.model.AtmAllocation;
import com.banking.app.Discoverybankingapp.model.Client;
import com.banking.app.Discoverybankingapp.model.ClientAccount;
import com.banking.app.Discoverybankingapp.model.CurrencyConversionRate;
import com.banking.app.Discoverybankingapp.repository.AtmAllocationRepository;
import com.banking.app.Discoverybankingapp.repository.ClientAccountRepository;
import com.banking.app.Discoverybankingapp.repository.ClientRepository;
import com.banking.app.Discoverybankingapp.repository.CurrencyConversionRateRepository;
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

//    @GetMapping("/getAllTransactional/{id}")
//    public List<ClientAccount> getAllTransactionalAccs(@PathVariable("id") int id)
//    {
//        return accountServices.getAllTransactionalAccs(id);
//    }


    @GetMapping("/getATMs")
    public List<AtmAllocation> getATMs()//(@PathVariable("id") int id)
    {
        return atm.findAll();
    }

    @GetMapping("/getCurrencyOfAccounts/{id}")
    public HashMap getCurrencyValueWithAccounts(@PathVariable("id") int id)
    {
        return accountServices.getCurrencyValueWithAccounts(id);
    }

    @GetMapping("/getConver")
    public List<CurrencyConversionRate> getConver()
    {
        return currencyConversionRateRepository.findAll();
    }

}
