/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.app.Discoverybankingapp.repository;

import com.banking.app.Discoverybankingapp.model.DenominationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Admin
 */
@Repository
public interface DenominationTypeRepository extends JpaRepository<DenominationType, String> {

}
