package com.banking.app.Discoverybankingapp.Services;

import com.banking.app.Discoverybankingapp.Services.AccountServices;
import com.banking.app.Discoverybankingapp.model.ClientAccount;
import com.banking.app.Discoverybankingapp.repository.ClientAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TransactionalAccountServices
{

    @Autowired
    private AccountServices accountServices;

    @Autowired
    private ClientAccountRepository client;

    public List<ClientAccount> getAllAccountsForUserT(int id) {
        List<ClientAccount> transactionAccounts = new ArrayList<>();
        List<ClientAccount> accounts = client.findAllByClient_ClientId(id);


        if (accounts == null) {
            System.out.println("No accounts to display");
        } else if (accounts != null) {
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println("In Loop");
                if (accounts.get(i).getAccountType().getTransactional() == true) {
                    System.out.println("TRUE");
                    transactionAccounts.add(accounts.get(i));
                }
            }
        }
        transactionAccounts = accountServices.sortTransactionalAccs(transactionAccounts);
        return transactionAccounts;
    }

}
