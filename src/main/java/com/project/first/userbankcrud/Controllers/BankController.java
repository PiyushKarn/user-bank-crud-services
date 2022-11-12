package com.project.first.userbankcrud.Controllers;

import java.util.List;
import java.util.Optional;

import com.project.first.userbankcrud.Domain.BankDomain;
import com.project.first.userbankcrud.Services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")

public class BankController {
    @Autowired
    private BankService bankServices;

    @GetMapping() //getAllAccounts
    public List<BankDomain> allAccounts() {
        return bankServices.findAll();

    }
    @PostMapping()  //AddNewAccount
    public ResponseEntity<String> addAccount(@RequestBody BankDomain accountData) {
        return bankServices.saveAccount(accountData);
    }

    @PutMapping()   //updateExistingAccount
    public ResponseEntity<String> updateUser(@RequestBody BankDomain newAccountData) {
        return bankServices.updateAccount(newAccountData);
    }

    @GetMapping("/{id}")    //findAccountById
    public Optional<BankDomain> getAccountById(@PathVariable Long id) {
        Optional<Optional<BankDomain>> bankDomain = Optional.ofNullable(bankServices.findById(id));
        return bankDomain.get();
    }

    @DeleteMapping()       //deleteUserById
    public ResponseEntity<String> deleteUser(@RequestBody BankDomain deleteAccountData) {
        return bankServices.deleteAccount(deleteAccountData);
    }

    @GetMapping("/pagination")    //findUserByPagination
    public Page<BankDomain> getUserById(@RequestParam int offset, @RequestParam int limit) {
        return bankServices.findAccountsWithPagination(offset,limit);
    }
}