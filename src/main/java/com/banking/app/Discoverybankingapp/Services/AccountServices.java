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







    public List<ClientAccount> getAllTransactionalAccs(int id) {

        return client.findAllByClient_ClientId(id);
    }


    public HashMap getCurrencyValueWithAccounts(int id) {
        List<Double> currencyBalanceExchanged =new ArrayList<>();
        List<Double> currencyRate =new ArrayList<>();
        //To get the List for the account mapping
        HashMap map = new HashMap();

        List<CurrencyConversionRate> currencyConversionRates = currencyConversionRateServices.getListCurrencyRateList();

        List<ClientAccount> accounts = getAllAccountsForUserT(id);

        //accounts.get(0).getCurrency().getCurrencyCode();

        for(int i = 0; i<accounts.size();i++)
        {
            for(int j = 0; j< currencyConversionRates.size(); j++)
            {
                //Testing if the accounts currency code maps the currency conversion code
                if(accounts.get(i).getCurrency().getCurrencyCode().equalsIgnoreCase(currencyConversionRates.get(j).getCurrencyCode()))
                {
                    //Calculate the rate based on the ConversionIndicator
                    if(currencyConversionRates.get(j).getConversionIndicator().equalsIgnoreCase("/"))
                    {
                        double total =  accounts.get(i).getDisplay_balance() / currencyConversionRates.get(j).getRate();
                        currencyBalanceExchanged.add(i,total);
                        System.out.println("Rate : " + currencyConversionRates.get(j).getRate());
                        currencyRate.add(i,currencyConversionRates.get(j).getRate());
                        break;
                    }
                    else if (currencyConversionRates.get(j).getConversionIndicator().equalsIgnoreCase("*"))
                    {
                        double total =  accounts.get(i).getDisplay_balance() * currencyConversionRates.get(j).getRate();
                        currencyBalanceExchanged.add(i,total);
                        System.out.println("Rate : " + currencyConversionRates.get(j).getRate());
                        currencyRate.add(i,currencyConversionRates.get(j).getRate());
                        break;
                    }

                }
            }
        }
        map.put("currencyRate", currencyRate);
        map.put("currencyBalanceExchanged",currencyBalanceExchanged);
        map.put("accountsTransactional", accounts);

        return map;
    }





    public ClientAccount makeWithDrawal(int atm_id, int client_id, String account, Timestamp date, double amount) {
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




        //List<Denomination> denoForATM = atmAllocation.getDenomination().;

        return false;
    }


}


