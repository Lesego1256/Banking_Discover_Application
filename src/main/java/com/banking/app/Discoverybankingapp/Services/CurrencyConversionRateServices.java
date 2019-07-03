package com.banking.app.Discoverybankingapp.Services;


import com.banking.app.Discoverybankingapp.model.CurrencyConversionRate;
import com.banking.app.Discoverybankingapp.repository.CurrencyConversionRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.List;

@Service
public class CurrencyConversionRateServices
{


    @Autowired
    private CurrencyConversionRateRepository currencyConversionRateRepository;

    public List<CurrencyConversionRate> getListCurrencyRateList()
    {
        return currencyConversionRateRepository.findAll();
    }



}
