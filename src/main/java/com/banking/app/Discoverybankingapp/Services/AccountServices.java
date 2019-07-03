package com.banking.app.Discoverybankingapp.Services;

import com.banking.app.Discoverybankingapp.model.*;
import com.banking.app.Discoverybankingapp.repository.AtmAllocationRepository;
import com.banking.app.Discoverybankingapp.repository.AtmRepository;
import com.banking.app.Discoverybankingapp.repository.ClientAccountRepository;
import com.banking.app.Discoverybankingapp.repository.ClientRepository;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServices {

    @Autowired
    private ClientAccountRepository client;

    @Autowired
    private AtmRepository atmRepo;


    @Autowired
    private AtmAllocationRepository atmAllocationRepository;

    @Autowired
    private CurrencyConversionRateServices currencyConversionRateServices;

    public List<ClientAccount> getAllTransactionalAccs(int id) {

        return client.findAllByClient_ClientId(id);
    }

    //Used
    //Get a list of all transactional account from a users
    public List<ClientAccount> getAllAccountsForUserT(int id) {
        List<ClientAccount> transactionAccounts = new ArrayList<>();
        List<ClientAccount> accounts = client.findAllByClient_ClientId(id);

        for (int i = 0; i < accounts.size(); i++) {
            System.out.println("In Loop");
            if (accounts.get(i).getAccountType().getTransactional() == true) {
                System.out.println("TRUE");
                transactionAccounts.add(accounts.get(i));

            }


        }

        transactionAccounts = sortTransactionalAccs(transactionAccounts);

        return transactionAccounts;

    }
    //To sort the accounts in ascending
    //Used



    public List<ClientAccount> sortTransactionalAccs(List<ClientAccount> accounts)
    {
//        List<ClientAccount> sortAccounts = accounts.stream()
//                .sorted(Comparator.comparing(ClientAccount::getDisplay_balance))
//                .collect(Collectors.toList());

        System.out.println("In side sort method");
        List<ClientAccount> sortAccounts = accounts.stream()
                .sorted(Comparator.comparing(ClientAccount::getDisplay_balance).reversed())
                .collect(Collectors.toList());
        return sortAccounts;
    }








































    ///////////////////////
    public List<ClientAccount> getForCurrenyAccounts(int id){
        List<Double> currencyBalanceExchanged =new ArrayList<>();

        List<ClientAccount> accountsCurrency = new ArrayList<>();

        List<ClientAccount> accounts = getAllTransactionalAccs(id);



        for(int i = 0; i<accounts.size();i++) {

            //Testing if the accounts currency code maps the currency conversion code
            if (accounts.get(i).getAccountType().getAccountTypeCode().equalsIgnoreCase("CFCA")) {

                if (accounts.get(i).getCurrency().getCurrencyCode().getConversionIndicator().equalsIgnoreCase("/")) {
                    double total = accounts.get(i).getDisplay_balance() / accounts.get(i).getCurrency().getCurrencyCode().getRate();
//                        currencyBalanceExchanged.add(i,total);
//                        System.out.println("Rate : " + accounts.get(i).getCurrency().getCurrencyCode().getRate());
//                        currencyRate.add(i,accounts.get(i).getCurrency().getCurrencyCode().getRate());
                    accounts.get(i).setRandValue(total);
                    accountsCurrency.add(accounts.get(i));

                    System.out.println(" -  - - -  - - -  - - -  - - -  - - - - - - - - - -  - - - - - - - - ");
                            System.out.println("Account No : " + accounts.get(i).getClientAccountNumber());
                    System.out.println("Current Balance : R " + accounts.get(i).getDisplay_balance());
                    System.out.println("Conversion Rate : " + accounts.get(i).getCurrency().getCurrencyCode().getRate());
                    System.out.println("Convert : R " + total);
                    System.out.println(" -  - - -  - - -  - - -  - - -  - - - - - - - - - -  - - - - - - - - ");

                }
                if (accounts.get(i).getCurrency().getCurrencyCode().getConversionIndicator().equalsIgnoreCase("*")) {
                    double total = accounts.get(i).getDisplay_balance() * accounts.get(i).getCurrency().getCurrencyCode().getRate();

                    accounts.get(i).setRandValue(total);
                    accountsCurrency.add(accounts.get(i));


                    //currencyBalanceExchanged.add(i,total);
                    System.out.println(" -  - - -  - - -  - - -  - - -  - - - - - - - - - -  - - - - - - - - ");
                    System.out.println("Account No : " + accounts.get(i).getClientAccountNumber());
                    System.out.println("Current Balance : R " + accounts.get(i).getDisplay_balance());
                    System.out.println("Conversion Rate : " + accounts.get(i).getCurrency().getCurrencyCode().getRate());
                    System.out.println("Convert : R " + total);
                    System.out.println(" -  - - -  - - -  - - -  - - -  - - - - - - - - - -  - - - - - - - - ");

                }
            }
        }
        //To sort based on the calculated rand value!
        accountsCurrency = sortCurrencylAccs(accountsCurrency);


        return accountsCurrency;
    }

    public List<ClientAccount> sortCurrencylAccs(List<ClientAccount> accounts)
    {

        System.out.println("In side sort method");
        List<ClientAccount> sortAccounts = accounts.stream()
                .sorted(Comparator.comparing(ClientAccount::getRandValue).reversed())
                .collect(Collectors.toList());
        return sortAccounts;
    }




















    public ClientAccount makeWithDrawal(int atm_id, int client_id, String account,double amount) {
        ClientAccount clientAccountCl = client.findByClientAccountNumberAndClient_ClientId(account, client_id);

        Atm atm = atmRepo.findById(atm_id).get();

        //
        if (clientAccountCl.getAccountType().getTransactional() == true)
            if (clientAccountCl != null)
                if (atm != null) {
                    //Check if you can make withdraw
                    if (clientAccountCl.getDisplay_balance() >= amount) {
                        double balance = clientAccountCl.getDisplay_balance() - amount;
                        clientAccountCl.setDisplay_balance(balance);
                        client.save(clientAccountCl);
                    }//check if account is cheque and balance is less than -10000
                    else if (clientAccountCl.getDisplay_balance() < amount)
                        if (clientAccountCl.getAccountType().getAccountTypeCode().equalsIgnoreCase("CHQ"))
                            if (clientAccountCl.getDisplay_balance() <= -10000)
                                if (amount - clientAccountCl.getDisplay_balance() == -10000) {
                                    double balance = clientAccountCl.getDisplay_balance() - amount;
                                    clientAccountCl.setDisplay_balance(balance);
                                    client.save(clientAccountCl);

                                }


                }

        return clientAccountCl;
    }

    public boolean isAmountIsCoin(int atm_id, double amount) {
            boolean result = false;

        if (amount % 10 == 0) {
            result =   false;
        }

        if (amount <= 10) {
            result = true;
        } else if ((amount % 10 >= 1 )||(amount % 10 >= 2 )||(amount % 10 >= 3 )||(amount % 10 >= 4 )||(amount % 10 >= 5)||(
        amount % 10 >= 6 )||(amount % 10 >= 7 )||(amount % 10 >= 8 )||(amount % 10 >= 9 )) {
            result = true;
        }
        return result;
    }

    public boolean doesHaveEnoughNotes(double amount, int id, int atm_id)
    {
        List<AtmAllocation> locations = atmAllocationRepository.findAll();

        List<AtmAllocation> locationsAtm = new ArrayList<>();

        for(int i = 0; i<locations.size();i++)
        {

            if(locations.get(i).getAtm().getAtm_Id() == atm_id)
            {
                locationsAtm.add(locations.get(i));
                //locationsAtm.get(i).getDenomination().
            }
        }

        return false;
    }


    public boolean getStatusOfAtmNotes200(List<AtmAllocation> atmAllocations, double amount)
    {
        boolean status = false;
        for(int i = 0; i<atmAllocations.size();i++)
        {
            if(atmAllocations.get(i).getDenomination().getValue() == 200)
            {
                double cashAtm200 = 200 * atmAllocations.get(i).getCount();

                if(cashAtm200 % amount == 0)
                {
                    status = true;
                    break;
                }
            }


        }


        return status;
    }

}


