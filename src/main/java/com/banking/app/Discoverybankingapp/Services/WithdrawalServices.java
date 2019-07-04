package com.banking.app.Discoverybankingapp.Services;


import com.banking.app.Discoverybankingapp.CustomException.ATMException;
import com.banking.app.Discoverybankingapp.model.Atm;
import com.banking.app.Discoverybankingapp.model.AtmAllocation;
import com.banking.app.Discoverybankingapp.model.ClientAccount;
import com.banking.app.Discoverybankingapp.repository.AtmAllocationRepository;
import com.banking.app.Discoverybankingapp.repository.AtmRepository;
import com.banking.app.Discoverybankingapp.repository.ClientAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class WithdrawalServices {


    static final int MAX_WITH_DRAW = -10000;


    @Autowired
    private ClientAccountRepository client;

    @Autowired
    private AtmRepository atmRepo;


    @Autowired
    private AccountServices accountServices;

    @Autowired
    private AtmAllocationRepository atmAllocationRepository;

    @Autowired
    private CurrencyConversionRateServices currencyConversionRateServices;

    @Autowired
    private WithdrawalServices withdrawalServices;

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
                } else {
                    throw new ATMException();
                }
        return clientAccountCl;
    }

    public boolean checkingNotes(double amount, int atm_id) {

        int countValue10=0;int countValue20=0;int countValue50=0;int countValue100=0;int countValue200=0;
        boolean status = false;
        int count = 0;
        List<AtmAllocation> atmList = accountServices.getListLocations(amount, atm_id);
         HashMap dispenser =  new HashMap();

         dispenser.put("countValue10",countValue10);
        dispenser.put("countValue20",countValue20);
        dispenser.put("countValue50",countValue50);
        dispenser.put("countValue100",countValue100);
        dispenser.put("countValue200",countValue200);


        atmList = accountServices.sortLocationToHighest(atmList);


        for (int i = 0; i < atmList.size(); i++) {

            
            if (amount >= atmList.get(i).getDenomination().getValue()) {
                if (amount % atmList.get(i).getDenomination().getValue() == 0)
                    if (atmList.get(i).getCount() > 1)
                {


                    //System.out.println("Value : " + atmList.get(i).getDenomination().getValue() + " Count is " + atmList.get(i).getCount());

                    int newCounter = (int) (amount / atmList.get(i).getDenomination().getValue());

                    //System.out.println("Counter " + newCounter);
                    int num = (int) (atmList.get(i).getCount() - (amount / atmList.get(i).getDenomination().getValue()));
                    // System.out.println(amount / atmList.get(i).getDenomination().getValue());
                    int newCount = atmList.get(i).getCount() - newCounter;


                    if(atmList.get(i).getDenomination().getValue() ==10)
                    {

                       dispenser.put("countValue10" , newCounter);
                        //int temp = (int) dispenser.get("countValue10");

                    }
                    if(atmList.get(i).getDenomination().getValue() ==20)
                    {
                        dispenser.put("countValue20" , newCounter);
                    }
                    if(atmList.get(i).getDenomination().getValue() ==50)
                    {
                        dispenser.put("countValue50" , newCounter);
                    }if(atmList.get(i).getDenomination().getValue() ==100)
                {
                    dispenser.put("countValue100" , newCounter);
                }if(atmList.get(i).getDenomination().getValue() ==200)
                {
                    dispenser.put("countValue200" , newCounter);

                }




                    status = true;
                    atmAllocationRepository.save(atmList.get(i));
                    atmList.get(i).setCount(newCount);

                }


                        if(amount >= atmList.get(i).getDenomination().getValue() || amount-atmList.get(i).getDenomination().getValue()>=0)

                    {

                         if(atmList.get(i).getDenomination().getValue() == 10)
                        {
                            amount = amount - 10;
                            int temp = (int) dispenser.get("countValue10") + 1;
                            dispenser.put("countValue10" , temp);
                        }
                        else if(atmList.get(i).getDenomination().getValue() == 20)
                        {
                            amount = amount - 20;
                            int temp = (int) dispenser.get("countValue20") + 1;
                            dispenser.put("countValue20" , temp);
                        }
                        else if(atmList.get(i).getDenomination().getValue() == 50)
                        {
                            int temp = (int) dispenser.get("countValue50") + 1;
                            amount = amount - 50;
                            dispenser.put("countValue50" , temp);
                        }
                        else if(atmList.get(i).getDenomination().getValue() == 100)
                        {
                            amount = amount - 100;
                            int temp = (int) dispenser.get("countValue100") + 1;
                            dispenser.put("countValue100" , temp);
                        }
                        else if(atmList.get(i).getDenomination().getValue() == 200)
                        {
                            amount = amount - 200;
                            int temp = (int) dispenser.get("countValue200") + 1;
                            dispenser.put("countValue200" , temp);
                        }
                        //amount = (amount - atmList.get(i).getDenomination().getValue());
                        System.out.println("Amount : " + amount);
                        count = atmList.get(i).getCount() - 1;
                        atmList.get(i).setCount(count);
                        atmAllocationRepository.save(atmList.get(i));
                        System.out.println("Else");
                        status = true;

                    }


            }
        }
        if (amount == 0) {
            status = true;
        }
        System.out.println(dispenser);
        return status;
    }

}

