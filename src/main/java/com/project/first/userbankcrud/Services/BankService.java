package com.project.first.userbankcrud.Services;

import com.project.first.userbankcrud.Domain.BankDomain;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

public interface BankService {
    List<BankDomain> findAll();

    ResponseEntity<String> saveAccount(BankDomain accountData);

    ResponseEntity<String> updateAccount(BankDomain newAccountData);

    Optional<BankDomain> findById(Long id);

    ResponseEntity<String> deleteAccount(BankDomain deleteAccountData);

    Page<BankDomain> findAccountsWithPagination(int offset, int limit);
}

