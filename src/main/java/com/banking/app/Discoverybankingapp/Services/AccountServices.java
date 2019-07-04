package com.banking.app.Discoverybankingapp.Services;

import com.banking.app.Discoverybankingapp.CustomException.ATMException;
import com.banking.app.Discoverybankingapp.CustomException.CustomException;
import com.banking.app.Discoverybankingapp.model.Atm;
import com.banking.app.Discoverybankingapp.model.AtmAllocation;
import com.banking.app.Discoverybankingapp.model.ClientAccount;
import com.banking.app.Discoverybankingapp.repository.AtmAllocationRepository;
import com.banking.app.Discoverybankingapp.repository.AtmRepository;
import com.banking.app.Discoverybankingapp.repository.ClientAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServices {

    static final int MAX_WITH_DRAW = -10000;


    @Autowired
    private ClientAccountRepository client;

    @Autowired
    private AtmRepository atmRepo;


    @Autowired
    private AtmAllocationRepository atmAllocationRepository;

    @Autowired
    private CurrencyConversionRateServices currencyConversionRateServices;

    public List<ClientAccount> getAccounts(int id)
    {
        return client.findAllByClient_ClientId(id);
    }

    public List<ClientAccount> sortTransactionalAccs(List<ClientAccount> accounts) {
        System.out.println("In side sort method");
        List<ClientAccount> sortAccounts = accounts.stream()
                .sorted(Comparator.comparing(ClientAccount::getDisplayBalance).reversed())
                .collect(Collectors.toList());
        return sortAccounts;
    }


    public List<ClientAccount> sortCurrencylAccs(List<ClientAccount> accounts) {
        List<ClientAccount> sortAccounts = accounts.stream()
                .sorted(Comparator.comparing(ClientAccount::getRandValue).reversed())
                .collect(Collectors.toList());
        return sortAccounts;
    }

    public ClientAccount makeWithDrawal(int atm_id, int client_id, String account, double amount) throws ATMException {
        ClientAccount clientAccountCl = client.findByClientAccountNumberAndClient_ClientId(account, client_id);
        Atm atm = atmRepo.findById(atm_id).get();


        if (clientAccountCl == null) {
            System.out.println("Client account not found!!");
            //throw new ATMException();
        } else if (atm == null) {
            System.out.println("ATM not found!!");
            throw new ATMException();
        } else if (clientAccountCl.getAccountType().getTransactional() == true)
            if (clientAccountCl != null)
                if (atm != null) {
                    //Check if you can make withdraw

                    if (clientAccountCl.getDisplayBalance() >= amount)
                        if (checkingNotes(amount, atm_id)) {
                            double balance = clientAccountCl.getDisplayBalance() - amount;
                            System.out.println("New Balance " + balance);
                            clientAccountCl.setDisplayBalance(balance);
                            clientAccountCl.setRandValue(balance);
                            System.out.println("Update the details");
                            client.save(clientAccountCl);
                        }
                    double balance = clientAccountCl.getDisplayBalance() - amount;
                    double postiveNumber = Math.abs(balance);
                    System.out.println(clientAccountCl.getDisplayBalance() - amount);
                    if (MAX_WITH_DRAW <= clientAccountCl.getDisplayBalance() - amount)
                        if (clientAccountCl.getAccountType().getAccountTypeCode().equalsIgnoreCase("CHQ"))
                            if (checkingNotes(postiveNumber, atm_id)) {
                                //double balance = clientAccountCl.getDisplayBalance() - amount;
                                clientAccountCl.setDisplayBalance(balance);
                                clientAccountCl.setRandValue(balance);
                            }
                }
        else
                {
                    throw new ATMException();
                }
        return clientAccountCl;
    }
    public List<AtmAllocation> getListLocations(double amount, int atm_id) {
        List<AtmAllocation> locations = atmAllocationRepository.findAll();

        List<AtmAllocation> locationsAtm = new ArrayList<>();


        for (int i = 0; i < locations.size(); i++) {

            if (locations.get(i).getAtm().getAtm_Id() == atm_id) {
                locationsAtm.add(locations.get(i));
            }
        }
        locationsAtm = sortLocationToHighest(locationsAtm);

        return locationsAtm;
    }

    public List<AtmAllocation> sortLocationToHighest(List<AtmAllocation> atmList) {
        System.out.println("In side sort method");
        List<AtmAllocation> sortAccounts = atmList.stream()
                .sorted(Comparator.comparing(AtmAllocation::getAtmAllocationId).reversed())
                .collect(Collectors.toList());

        return sortAccounts;
    }
    //check the note in the atm
    public boolean checkingNotes(double amount, int atm_id) {
        List<AtmAllocation> atmList = getListLocations(amount, atm_id);
        boolean status = false; HashMap dispenser =  new HashMap();  int count = 0;

        atmList = sortLocationToHighest(atmList);


        for (int i = 0; i < atmList.size(); i++) {

            if (amount >= atmList.get(i).getDenomination().getValue()) {
                if (amount % atmList.get(i).getDenomination().getValue() == 0)

                    {

                        //System.out.println("Value : " + atmList.get(i).getDenomination().getValue() + " Count is " + atmList.get(i).getCount());

                        int newCounter = (int) (amount / atmList.get(i).getDenomination().getValue());

                        //System.out.println("Counter " + newCounter);
                        int num = (int) (atmList.get(i).getCount() - (amount / atmList.get(i).getDenomination().getValue()));
                       // System.out.println(amount / atmList.get(i).getDenomination().getValue());
                        int newCount = atmList.get(i).getCount() - newCounter;


                        if(atmList.get(i).getDenomination().getValue() ==10)
                        {
                            dispenser.put("10 Rand " , newCounter);
                        }
                        if(atmList.get(i).getDenomination().getValue() ==20)
                        {
                            dispenser.put("20 Rand" , newCounter);
                        }
                        if(atmList.get(i).getDenomination().getValue() ==50)
                        {
                            dispenser.put("50 Rand" , newCounter);
                        }if(atmList.get(i).getDenomination().getValue() ==100)
                        {
                            dispenser.put("100 Rand" , newCounter);
                        }if(atmList.get(i).getDenomination().getValue() ==200)
                        {
                            dispenser.put("200 Rand" , newCounter);
                        }


                        System.out.println(dispenser);

                        status = true;
                        atmAllocationRepository.save(atmList.get(i));
                        atmList.get(i).setCount(newCount);
                        break;
                    }
//                else
//                    {
//
//                        amount = amount - atmList.get(i).getDenomination().getValue();
//                        System.out.println("Amount : " + amount);
//                        count = atmList.get(i).getCount() - 1;
//                        atmList.get(i).setCount(count);
//                        status = true;
//                    }
            }
        }
        if (amount == 0) {
            status = true;
        }
        //saveATmLocationCount(status, amount, atm_id);
        return status;
    }



}


