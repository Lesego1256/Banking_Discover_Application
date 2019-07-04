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





}


