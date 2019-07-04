package com.banking.app.Discoverybankingapp.repository;

import com.banking.app.Discoverybankingapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

//    @Query("select clientId from Client")
//    List<Long> getAllClients();


//    @Query("Select distinct c from Client c where c.clientId=?1")
//    Client getClient(Long id);


//        @Query("Select ca.display_balance, cr.rate, cu.currency_code,ac.description
//                from client_account ca, client c,currency cu, currency_conversion_rate cr, account_type ac
//                where ca.client_id = c.client_id
//                and cu.currency_code =  ca.currency_code
//                and cu.currency_code = cr.currency_code
//                and ca.account_type_code = ac.account_type_code
//                and ac.transactional = true
//                and c.client_id = 1")
//    List<Object[]> getAccounts(Long id);
}
