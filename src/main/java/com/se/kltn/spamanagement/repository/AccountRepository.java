package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByUsername(String username);

    Optional<Account> findAccountByEmployee_Id(Long idEmployee);

    Optional<Account> findAccountByCustomer_Id(Long idCustomer);
}
