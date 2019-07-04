package com.banking.app.Discoverybankingapp.repository;

import com.banking.app.Discoverybankingapp.model.AtmAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtmAllocationRepository extends JpaRepository<AtmAllocation, Integer> {

    //AtmAllocation  findAtmAllocationByAtm_Atm_Id(int id);
}
