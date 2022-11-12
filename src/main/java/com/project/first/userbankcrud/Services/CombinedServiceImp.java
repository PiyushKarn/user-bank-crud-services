package com.project.first.userbankcrud.Services;

import com.project.first.userbankcrud.Domain.BankDomain;
import com.project.first.userbankcrud.Domain.CombinedObject;
import com.project.first.userbankcrud.Domain.UserDomain;
import com.project.first.userbankcrud.Exception.UserNotFoundException;
import com.project.first.userbankcrud.Repositories.BankRepository;
import com.project.first.userbankcrud.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CombinedServiceImp implements CombinedService{
    @Autowired
    BankRepository bankRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public String save(CombinedObject combinedObject) {

        try {
            UserDomain userDomain = new UserDomain(
                    -1L,
                    combinedObject.getName(),
                    combinedObject.getPhoneNumber(),
                    combinedObject.getAddress(),
                    combinedObject.getAdditionalDetailsUser()
            );
            userDomain = userRepository.save(userDomain);

            BankDomain bankDomain = new BankDomain(
                    -1L,
                    userDomain.getId(),
                    combinedObject.getBankName(),
                    combinedObject.getAccountNumber(),
                    combinedObject.getIfscCode(),
                    combinedObject.getAdditionalDetailsBank()
            );
            bankRepository.save(bankDomain);
        }
        catch(Exception e){
            return "Error! Duplicate entry!";
        }
        return "User and Bank details added successfully";
    }

    @Override
    public List<CombinedObject> getCombinedDomainByPhoneNumber(Long phoneNumber) {

        try {
            UserDomain userDomain = userRepository.findByPhoneNumber(phoneNumber).get(0);
            List<BankDomain> bankDomains = bankRepository.findByUserId(userDomain.getId());

            List<CombinedObject> combinedObjects = new ArrayList<>();
            for(BankDomain bankDomain:bankDomains)
            {
                combinedObjects.add(new CombinedObject(userDomain,bankDomain));
            }
            return combinedObjects;
        } catch (Exception e){
            System.out.println("User with given phone number does not exist");
            return null;
        }
    }

    @Override
    public String delete(Long id){

        try {
            bankRepository.deleteByUserId(id);
            userRepository.deleteById(id);
        }
        catch (Exception e){
            return "User Id doesn't exist!";
        }
        return "User deleted Successfully";
    }

    @Override
    public String update(CombinedObject combinedObject) {
        try {
            UserDomain userDomain = userRepository.findById(combinedObject.getUserId()).orElse(null);
            if(userDomain==null) throw new UserNotFoundException("User Not Found");
            if (combinedObject.getName() != null) userDomain.setName(combinedObject.getName());
            if (combinedObject.getAddress() != null) userDomain.setAddress(combinedObject.getAddress());
            if (combinedObject.getPhoneNumber() != null) userDomain.setPhoneNumber(combinedObject.getPhoneNumber());
            if (combinedObject.getAdditionalDetailsUser() != null)
                userDomain.setAdditionalDetailsUser(combinedObject.getAdditionalDetailsUser());

            userDomain = userRepository.save(userDomain);

            if(combinedObject.getBankId()!=null){
                BankDomain bankDomain = bankRepository.findById(combinedObject.getBankId()).orElse(null);
                if(combinedObject.getAccountNumber()!=null) bankDomain.setAccountNumber(combinedObject.getAccountNumber());
                if(combinedObject.getBankName()!=null) bankDomain.setBankName(combinedObject.getBankName());
                if(combinedObject.getAdditionalDetailsBank()!=null) bankDomain.setAdditionalDetailsBank(combinedObject.getAdditionalDetailsBank());
                if(combinedObject.getIfscCode()!=null) bankDomain.setIfscCode(combinedObject.getIfscCode());
                bankRepository.save(bankDomain);
            }

        } catch (Exception e) {
            return e.getMessage();
        }
        return "User updated!";
    }

}
