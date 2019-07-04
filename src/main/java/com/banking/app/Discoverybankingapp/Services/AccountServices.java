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
        if(accounts  == null)
        {
            System.out.println("No accounts to display");
        }
        else if ( accounts !=null)
        {
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println("In Loop");
                if (accounts.get(i).getAccountType().getTransactional() == true) {
                    System.out.println("TRUE");
                    transactionAccounts.add(accounts.get(i));

                }


            }
        }


        transactionAccounts = sortTransactionalAccs(transactionAccounts);

        return transactionAccounts;

    }
    public List<ClientAccount> sortTransactionalAccs(List<ClientAccount> accounts)
    {
        System.out.println("In side sort method");
        List<ClientAccount> sortAccounts = accounts.stream()
                .sorted(Comparator.comparing(ClientAccount::getDisplayBalance).reversed())
                .collect(Collectors.toList());
        return sortAccounts;
    }








































    /////////////////////// Get All Currency for a User
    public List<ClientAccount> getForCurrenyAccounts(int id){
        List<ClientAccount> accountsCurrency = new ArrayList<>();

        List<ClientAccount> accounts = getAllTransactionalAccs(id);


        for(int i = 0; i<accounts.size();i++) {

            //Testing if the accounts currency code maps the currency conversion code
            if (accounts.get(i).getAccountType().getAccountTypeCode().equalsIgnoreCase("CFCA")) {

                if (accounts.get(i).getCurrency().getCurrencyCode().getConversionIndicator().equalsIgnoreCase("/")) {
                    double total = accounts.get(i).getDisplayBalance() / accounts.get(i).getCurrency().getCurrencyCode().getRate();

                    accounts.get(i).setRandValue(total);
                    accountsCurrency.add(accounts.get(i));

                    System.out.println(" -  - - -  - - -  - - -  - - -  - - - - - - - - - -  - - - - - - - - ");
                            System.out.println("Account No : " + accounts.get(i).getClientAccountNumber());
                    System.out.println("Current Balance : R " + accounts.get(i).getDisplayBalance());
                    System.out.println("Conversion Rate : " + accounts.get(i).getCurrency().getCurrencyCode().getRate());
                    System.out.println("Convert : R " + total);
                    System.out.println(" -  - - -  - - -  - - -  - - -  - - - - - - - - - -  - - - - - - - - ");

                }
                if (accounts.get(i).getCurrency().getCurrencyCode().getConversionIndicator().equalsIgnoreCase("*")) {
                    double total = accounts.get(i).getDisplayBalance() * accounts.get(i).getCurrency().getCurrencyCode().getRate();

                    accounts.get(i).setRandValue(total);
                    accountsCurrency.add(accounts.get(i));


                    //currencyBalanceExchanged.add(i,total);
                    System.out.println(" -  - - -  - - -  - - -  - - -  - - - - - - - - - -  - - - - - - - - ");
                    System.out.println("Account No : " + accounts.get(i).getClientAccountNumber());
                    System.out.println("Current Balance : R " + accounts.get(i).getDisplayBalance());
                    System.out.println("Conversion Rate : " + accounts.get(i).getCurrency().getCurrencyCode().getRate());
                    System.out.println("Convert : R " + total);
                    System.out.println(" -  - - -  - - -  - - -  - - -  - - - - - - - - - -  - - - - - - - - ");

                }
            }
        }

        if(accountsCurrency == null)
        {
            System.out.println("No accounts to display");
        }
        //To sort based on the calculated rand value
        accountsCurrency = sortCurrencylAccs(accountsCurrency);


        return accountsCurrency;
    }

    public List<ClientAccount> sortCurrencylAccs(List<ClientAccount> accounts)
    {
        List<ClientAccount> sortAccounts = accounts.stream()
                .sorted(Comparator.comparing(ClientAccount::getRandValue).reversed())
                .collect(Collectors.toList());
        return sortAccounts;
    }



















    //
    public ClientAccount makeWithDrawal(int atm_id, int client_id, String account,double amount) {
        ClientAccount clientAccountCl = client.findByClientAccountNumberAndClient_ClientId(account, client_id);
        Atm atm = atmRepo.findById(atm_id).get();
        if(clientAccountCl == null)
            {
                System.out.println("Client account not found!!");

            }
        else if (atm == null)
        {
            System.out.println("ATM not found!!");
        }
        else if (clientAccountCl.getAccountType().getTransactional() == true)
            if (clientAccountCl != null)
                if (atm != null) {
                    //Check if you can make withdraw

                    if (clientAccountCl.getDisplayBalance() >= amount)
                        if (checkingNotes(amount, atm_id)) {
//                        boolean status200 = getStatusOfAtmNotes200(amount,atm_id);
//                        System.out.println("200 Status : " + status200);
                            double balance = clientAccountCl.getDisplayBalance() - amount;
                            System.out.println("New Balance " + balance);
                            clientAccountCl.setDisplayBalance(balance);
                            clientAccountCl.setRandValue(balance);
                            System.out.println("Update the details");
                            client.save(clientAccountCl);
                        }//check if account is cheque and balance is less than -10000
                        else if (clientAccountCl.getDisplayBalance() < amount)
                            if (clientAccountCl.getAccountType().getAccountTypeCode().equalsIgnoreCase("CHQ"))
                            {
                                if (clientAccountCl.getDisplayBalance() <= -10000)
                                    if (amount - clientAccountCl.getDisplayBalance() == -10000) {
                                        double balance = clientAccountCl.getDisplayBalance() - amount;
                                        clientAccountCl.setDisplayBalance(balance);
                                        client.save(clientAccountCl);

                                    }
                                    else{
                                        System.out.println("You have exceeded the limit");
                                    }
                            }
                            else
                            {
                                System.out.println("Amount is not a cheque account");
                            }




                    }
        return clientAccountCl;
    }










    public List<AtmAllocation>  getListLocations(double amount, int atm_id)
    {
        List<AtmAllocation> locations = atmAllocationRepository.findAll();

        List<AtmAllocation> locationsAtm = new ArrayList<>();



        for(int i = 0; i<locations.size();i++)
        {

            if(locations.get(i).getAtm().getAtm_Id() == atm_id)
            {
                locationsAtm.add(locations.get(i));
            }
        }
        locationsAtm = sortLocationToHighest(locationsAtm);

        return locationsAtm;
    }



    public List<AtmAllocation> sortLocationToHighest(List<AtmAllocation> atmList)
    {
        System.out.println("In side sort method");
        List<AtmAllocation> sortAccounts = atmList.stream()
                .sorted(Comparator.comparing(AtmAllocation::getAtmAllocationId).reversed())
                .collect(Collectors.toList());
        return sortAccounts;
    }



    //check the note in the atm
    public boolean checkingNotes(double amount, int atm_id)
    {
        List<AtmAllocation>  atmList =  getListLocations(amount, atm_id);
        boolean status = false;

        int count = 0;

        //for(int i = atmList.size(); i-- > 0; )
        for(int i = 0;i<atmList.size();i++)
        {
            //System.out.println("Value for the if " + atmList.get(i).getDenomination().getValue());
            if(amount >= atmList.get(i).getDenomination().getValue())
            {


                if(amount % atmList.get(i).getDenomination().getValue()== 0)
                if(amount / atmList.get(i).getDenomination().getValue() <= atmList.get(i).getCount())
                {
                   // int num = (int) ((int) (atmList.get(i).getDenomination().getValue() *  atmList.get(i).getCount()) - amount);


                    System.out.println("Value : " + atmList.get(i).getDenomination().getValue() + " Count is " + atmList.get(i).getCount());

                    int  newCounter = (int) (amount /atmList.get(i).getDenomination().getValue());
                    System.out.println("Count " + newCounter);
                   int  num = (int) (atmList.get(i).getCount() - (amount/atmList.get(i).getDenomination().getValue()));
                   System.out.println(amount/atmList.get(i).getDenomination().getValue());

                    int newCount  =  atmList.get(i).getCount() - newCounter;


                    status = true;
                    //calculate the count that is left
                    //int co  = (int) (amount / atmList.get(i).getDenomination().getValue());
                    System.out.println(" ATM Count : " + atmList.get(i).getDenomination().getValue());
                    //int calculatedCount = amount / atmList.get(i).getDenomination().getValue();
                    //count  = atmList.get(i).getCount() - Math.floor(calculatedCount);
                    atmList.get(i).setCount(newCount);
                    System.out.println("New count : " + newCount + " of : " + atmList.get(i).getDenomination().getValue());

                    break;
                }
                else {
                    amount = amount - atmList.get(i).getDenomination().getValue();
                    count = atmList.get(i).getCount()-1;
                    atmList.get(i).setCount(count);
                    status = true;

                }


            }


        }




        if(amount == 0)
        {
            status = true;
        }
        else if(amount >0)
        {
            System.out.println("Atm does not have enough notes");
        }
        saveATmLocationCount(status,amount,atm_id);
        return status;
    }





    // to save the state of the atm example the count s
    public void saveATmLocationCount(boolean status, double amount,int atm_id)
    {
        if(status)
        {
            List<AtmAllocation>  atmList =  getListLocations(amount, atm_id);


            int count = 0;

            //for(int i = atmList.size(); i-- > 0; )
            for(int i = 0;i<atmList.size();i++)
            {
                //System.out.println("Value for the if " + atmList.get(i).getDenomination().getValue());
                if(amount >= atmList.get(i).getDenomination().getValue())
                {


                    if(amount % atmList.get(i).getDenomination().getValue()== 0)
                        if(amount / atmList.get(i).getDenomination().getValue() <= atmList.get(i).getCount())
                        {
                            int  newCounter = (int) (amount /atmList.get(i).getDenomination().getValue());

                            int  num = (int) (atmList.get(i).getCount() - (amount/atmList.get(i).getDenomination().getValue()));


                            int newCount  =  atmList.get(i).getCount() - newCounter;


                            status = true;





                            atmList.get(i).setCount(newCount);

                            atmAllocationRepository.save(atmList.get(i));
                            break;
                        }
                        else {
                            amount = amount - atmList.get(i).getDenomination().getValue();
                            count = atmList.get(i).getCount()-1;
                            atmList.get(i).setCount(count);

                            status = true;
                            atmAllocationRepository.save(atmList.get(i));
                        }


                }


            }
        }
    }
}


