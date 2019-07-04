package com.banking.app.Discoverybankingapp.controller;


import com.banking.app.Discoverybankingapp.CustomException.ATMException;
import com.banking.app.Discoverybankingapp.CustomException.CustomException;
import com.banking.app.Discoverybankingapp.Services.CurrencyAccountServices;
import com.banking.app.Discoverybankingapp.Services.TransactionalAccountServices;
import com.banking.app.Discoverybankingapp.Services.AccountServices;
import com.banking.app.Discoverybankingapp.model.ClientAccount;
import com.banking.app.Discoverybankingapp.repository.AtmAllocationRepository;
import com.banking.app.Discoverybankingapp.repository.ClientAccountRepository;
import com.banking.app.Discoverybankingapp.repository.ClientRepository;
import com.banking.app.Discoverybankingapp.repository.CurrencyConversionRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountServices accountServices;


    @Autowired
    private CurrencyAccountServices currencyAccountServices;

    @Autowired
    private ClientRepository repo;

    @Autowired
    private ClientAccountRepository cl;

    @Autowired
    private TransactionalAccountServices transactionalAccountServices;

    @Autowired
    private AtmAllocationRepository atm;


    @Autowired
    private CurrencyConversionRateRepository currencyConversionRateRepository;


    @GetMapping("/getAccounts/{id}")
    public List<ClientAccount> getAccounts(@PathVariable("id") int id) {
        return transactionalAccountServices.getAllAccountsForUserT(id);
    }

    @GetMapping("/getAllTransactionalAccs/{id}")
    public List<ClientAccount> getAllTransactionalAccs(@PathVariable("id") int id) throws CustomException {
        List<ClientAccount> allTransactionalAccs = transactionalAccountServices.getAllAccountsForUserT(id);
        if (allTransactionalAccs.isEmpty() || allTransactionalAccs == null)
        {
            throw new CustomException();
        }
        return allTransactionalAccs;
    }


    @GetMapping("/getCurrencyValueWithAccounts/{id}")
    public List<ClientAccount> getCurrencyValueWithAccounts(@PathVariable("id") int id) throws CustomException {
        List<ClientAccount> currencyList = currencyAccountServices.getForCurrenyAccounts(id);
        if (currencyList.isEmpty()) {
            throw new CustomException();
        }
        return currencyAccountServices.getForCurrenyAccounts(id);
    }


    @GetMapping("/makeAWithdrawalll/{atm_id}/{client_id}/{account}/{amount}")
    public ClientAccount makeWithdrawa1l(@PathVariable("atm_id") int atm_id, @PathVariable("client_id") int client_id,
                                         @PathVariable("account") String account, @PathVariable("amount") double amount) throws ATMException {
        if (atm_id > 7) {
            throw new ATMException();
        }
        return accountServices.makeWithDrawal(atm_id, client_id, account, amount);
    }


}
