package com.banking.app.Discoverybankingapp.Services;

import com.banking.app.Discoverybankingapp.model.ClientAccount;
import com.banking.app.Discoverybankingapp.repository.ClientAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyAccountServices
{
    @Autowired
    private AccountServices accountServices;

    @Autowired
    private ClientAccountRepository client;

    public List<ClientAccount> getForCurrenyAccounts(int id) {
        List<ClientAccount> accountsCurrency = new ArrayList<>();

        List<ClientAccount> accounts = accountServices.getAccounts(id);


        for (int i = 0; i < accounts.size(); i++) {

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
        if (accountsCurrency == null) {
            System.out.println("No accounts to display");
        }
        //To sort based on the calculated rand value
        accountsCurrency = accountServices.sortCurrencylAccs(accountsCurrency);
        return accountsCurrency;
    }
}
