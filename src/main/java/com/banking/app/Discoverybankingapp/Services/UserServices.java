package com.banking.app.Discoverybankingapp.Services;

import com.banking.app.Discoverybankingapp.model.Client;
import com.banking.app.Discoverybankingapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServices
{
    @Autowired
    private ClientRepository clientRepository;

    public Client getClient(Long id)
    {
       //Optional<Client> client = clientRepository.findById(id);
        return clientRepository.findById(id).get();
    }
}
