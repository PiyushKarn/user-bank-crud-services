package com.project.first.userbankcrud.Domain;

public class CombinedObject {

    private Long bankId;
    private Long userId;
    private String name;
    private Long phoneNumber;
    private String address;
    private String additionalDetailsUser;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String additionalDetailsBank;

    public CombinedObject() {
    }

    public CombinedObject(UserDomain userDomain, BankDomain bankDomain)
    {
        this.bankId = bankDomain.getId();
        this.userId = userDomain.getId();
        this.name = userDomain.getName();
        this.phoneNumber = userDomain.getPhoneNumber();
        this.address = userDomain.getAddress();
        this.additionalDetailsUser = userDomain.getAdditionalDetailsUser();
        this.bankName = bankDomain.getBankName();
        this.accountNumber = bankDomain.getAccountNumber();
        this.ifscCode = bankDomain.getIfscCode();
        this.additionalDetailsBank = bankDomain.getAdditionalDetailsBank();
    }


    public CombinedObject(Long bankId, Long userId, String name, Long phoneNumber, String address, String bankName, String accountNumber, String ifscCode, String additionalDetailsUser, String additionalDetailsBank) {
        this.bankId = bankId;
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.additionalDetailsUser = additionalDetailsUser;
        this.additionalDetailsBank = additionalDetailsBank;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getAdditionalDetailsUser() {
        return additionalDetailsUser;
    }

    public void setAdditionalDetailsUser(String additionalDetailsUser) {
        this.additionalDetailsUser = additionalDetailsUser;
    }

    public String getAdditionalDetailsBank() {
        return additionalDetailsBank;
    }

    public void setAdditionalDetailsBank(String additionalDetailsBank) {
        this.additionalDetailsBank = additionalDetailsBank;
    }
}
