package com.project.first.userbankcrud.Domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name="accounts")

public class BankDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private long userId;
    @Column
    private String bankName;
    @Column
    private String accountNumber;
    @Column
    private String ifscCode;
    @Column(columnDefinition = "TEXT")
    private String additionalDetailsBank;

    //    @OneToOne(mappedBy = "bank", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "id")
//    private UserDomain user;
    public BankDomain()
    {}
    public BankDomain(Long id, Long userId, String bankName,String accountNumber, String ifscCode, String additionalDetailsBank)
    {
        this.id = id;
        this.userId =userId;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.additionalDetailsBank = additionalDetailsBank;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }


    public String getAdditionalDetailsBank() {
        return additionalDetailsBank;
    }

    public void setAdditionalDetailsBank(String additionalDetailsBank) {
        this.additionalDetailsBank = additionalDetailsBank;
    }

    public BankDomain(long userId, String bankName, String accountNumber, String ifscCode, String additionalDetailsBank) {
        this.id = 0l;
        this.userId = userId;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.additionalDetailsBank = additionalDetailsBank;
    }
}
