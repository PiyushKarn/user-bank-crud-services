package com.project.first.userbankcrud.Services;

import com.project.first.userbankcrud.Domain.BankDomain;
import com.project.first.userbankcrud.Domain.UserDomain;
import com.project.first.userbankcrud.Repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankServiceImpl implements BankService{
    @Autowired
    private BankRepository bankRepository;
    @Override
    public List<BankDomain> findAll() {
        List<BankDomain> allAccounts = bankRepository.findAll();
        return allAccounts;
    }

    @Override
    public ResponseEntity<String> saveAccount(BankDomain accountData) {
        try {
            bankRepository.save(accountData);
            return new ResponseEntity<String>("Account added successfully!", HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<String>("Error, possibly a duplicate entry! Please check all the fields", HttpStatus.OK);
        }
        // return "Error!! Please Check all the fields!";
    }
    @Override
    public ResponseEntity<String> updateAccount(BankDomain newAccountData) {

        BankDomain existingUser = bankRepository.findById(newAccountData.getId()).orElse(null);
        if  (existingUser == null){
            return new ResponseEntity<String>("No such account exists!!", HttpStatus.OK);
        }
        else {
            bankRepository.save(newAccountData);
            return new ResponseEntity<String>("Account data updated!", HttpStatus.OK);
        }
    }

    @Override
    public Optional<BankDomain> findById(Long id) {
        return bankRepository.findById(id);
    }

    @Override
    public ResponseEntity<String> deleteAccount(BankDomain deleteAccountData) {
        BankDomain existingAccount
                = bankRepository.findById(deleteAccountData.getId())
                .orElse(null);
        if (existingAccount != null) {
            bankRepository.delete((deleteAccountData));
            return new ResponseEntity<String>("Data deleted", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("No such account exists!!", HttpStatus.OK);
        }
    }

    @Override
    public Page<BankDomain> findAccountsWithPagination(int offset, int limit){

        try {
            Page<BankDomain> accounts = bankRepository.findAll(PageRequest.of(offset, limit));
            System.out.println(accounts);
            return accounts;
        }
        catch (IllegalArgumentException e) {
            System.out.println("Please enter valid offset and limit");

        }
        Page<BankDomain> users = bankRepository.findAll(PageRequest.of(0, 5));
        System.out.println("Passing default values to limit and offset");
        return users;
    }


}

