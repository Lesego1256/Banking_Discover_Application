package com.banking.app.Discoverybankingapp.repository;

import com.banking.app.Discoverybankingapp.model.Atm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtmRepository extends JpaRepository<Atm, Integer> {

    //Atm findByAtm_Id(int id);

}
