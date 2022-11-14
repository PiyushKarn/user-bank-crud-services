package com.project.first.userbankcrud.Services;

import com.project.first.userbankcrud.Domain.BankDomain;
import com.project.first.userbankcrud.Domain.CombinedObject;
import com.project.first.userbankcrud.Domain.UserDomain;
import com.project.first.userbankcrud.Exception.UserNotFoundException;
import com.project.first.userbankcrud.Repositories.BankRepository;
import com.project.first.userbankcrud.Repositories.UserRepository;
import com.project.first.userbankcrud.Response.Failure;
import com.project.first.userbankcrud.Response.Response;
import com.project.first.userbankcrud.Response.Success;
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
    public Response save(CombinedObject combinedObject) {

        Response response = createUser(combinedObject);
        if(response instanceof Failure)
            return response;

        UserDomain userDomain = (UserDomain) response.getPayLoad();
        combinedObject.setUserId(userDomain.getId());

        response = createBank(combinedObject);
        //incase of failure the newly created user will be deleted
        if(response instanceof Failure)
        {
            userRepository.deleteById(userDomain.getId());
            return response;
        }

        return (Response) new Success("User and Bank Account created Successfully",combinedObject);
    }

    private Response createUser(CombinedObject userDomain)
    {
        //check requirements for User
        if(userDomain.getName()==null) return (Response) new Failure("User Name should not be null");
        if(userDomain.getPhoneNumber()==null) return (Response) new Failure("Phone number should not be null");

        //Edge Cases
        if(userDomain.getName().isEmpty()) return (Response) new Failure("User Name should not be empty");
        if(userDomain.getPhoneNumber()<=0) return (Response) new Failure("Invalid Phone Number");
        if(userDomain.getPhoneNumber().toString().trim().length()>15)
            return (Response) new Failure("Phone Number should not exceed 15 digits");

        //check if already exists
        if(userRepository.findByPhoneNumber(userDomain.getPhoneNumber()).size()>0)
            return (Response) new Failure("User with this phone number already exists.");

        try{
            UserDomain userDomainTemp = new UserDomain(
                    userDomain.getName(),
                    userDomain.getPhoneNumber(),
                    userDomain.getAddress(),
                    userDomain.getAdditionalDetailsUser()
            );

            return (Response) new Success(
                    "User Added Successfully",
                    userRepository.save(userDomainTemp)
                    );
        }catch (Exception e){
            return (Response) new Failure(e.getMessage());
        }

    }

    private Response createBank(CombinedObject bankDomain)
    {
        //check requirements for Bank
        if(bankDomain.getBankName()==null) return (Response) new Failure("Bank Name should not be null.");
        if(bankDomain.getAccountNumber()==null) return (Response) new Failure("Account number should not be null.");
        if(bankDomain.getUserId()==null) return (Response) new Failure("UserId should not be null.");

        //check if userId exists
        if(!userRepository.findById(bankDomain.getUserId()).isPresent())
            return (Response) new Failure("User does not exist. Foreign key violation!");

        // check if already exists
        if (bankRepository.findByUserIdAndAccountNumber(bankDomain.getUserId(), bankDomain.getAccountNumber()).size()>0)
            return (Response) new Failure("Duplicate entry for UserId and AccountNumber");

        try{
            BankDomain bankDomainTemp = new BankDomain(
                    bankDomain.getUserId(),
                    bankDomain.getBankName(),
                    bankDomain.getAccountNumber(),
                    bankDomain.getIfscCode(),
                    bankDomain.getAdditionalDetailsBank()
            );

            return (Response) new Success(
                    "Bank added Successfully.",
                    bankRepository.save(bankDomainTemp)
            );

        }catch (Exception e){
            return (Response) new Failure(e.getMessage());
        }
    }

    @Override
    public Response getCombinedDomainByPhoneNumber(Long phoneNumber) {

        try {
            UserDomain userDomain = userRepository.findByPhoneNumber(phoneNumber).get(0);
            List<BankDomain> bankDomains = bankRepository.findByUserId(userDomain.getId());

            List<CombinedObject> combinedObjects = new ArrayList<>();
            for(BankDomain bankDomain:bankDomains)
            {
                combinedObjects.add(new CombinedObject(userDomain,bankDomain));
            }
            return (Response) new Success("Success", combinedObjects);
        } catch (Exception e){
            return (Response) new Failure("User with given phone number does not exist");
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
            if (combinedObject.getName()!=null && !combinedObject.getName().isBlank()) userDomain.setName(combinedObject.getName());
            if (combinedObject.getAddress() != null) userDomain.setAddress(combinedObject.getAddress());
            if (combinedObject.getPhoneNumber()!=null && combinedObject.getPhoneNumber()>0) userDomain.setPhoneNumber(combinedObject.getPhoneNumber());
            if (combinedObject.getAdditionalDetailsUser()!= null)
                userDomain.setAdditionalDetailsUser(combinedObject.getAdditionalDetailsUser());

            userDomain = userRepository.save(userDomain);

            if(combinedObject.getBankId()!=null){
                BankDomain bankDomain = bankRepository.findById(combinedObject.getBankId()).orElse(null);
                if(combinedObject.getAccountNumber()!=null) {
                    if (combinedObject.getIfscCode().length() <= 17)
                        bankDomain.setAccountNumber(combinedObject.getAccountNumber());
                    else return "Account number should not exceed 11 characters";
                }

                if(combinedObject.getBankName()!=null) {
                    if (combinedObject.getIfscCode().length() <= 30)
                        bankDomain.setBankName(combinedObject.getBankName());
                    else return "Bank name should not exceed 30 characters";
                }


                if(combinedObject.getAdditionalDetailsBank()!=null)
                    bankDomain.setAdditionalDetailsBank(combinedObject.getAdditionalDetailsBank());

                if(combinedObject.getIfscCode()!=null) {
                    if (combinedObject.getIfscCode().length() <= 11)
                        bankDomain.setIfscCode(combinedObject.getIfscCode());
                    else return "Ifsc code should not exceed 11 characters";
                }

                bankRepository.save(bankDomain);
            }
            else if (isBankRequirementsAvailable(combinedObject)) {
                BankDomain bankDomain = new BankDomain(0l,combinedObject.getUserId(),combinedObject.getBankName(),combinedObject.getAccountNumber()
                ,combinedObject.getIfscCode(),combinedObject.getAdditionalDetailsBank());
                bankRepository.save(bankDomain);
            }

        } catch (Exception e) {
            return e.getMessage();
        }
        return "User updated!";
    }

    boolean isBankRequirementsAvailable(CombinedObject combinedObject)
    {
        if(combinedObject.getBankName()==null) return false;
        if(combinedObject.getAccountNumber()==null) return false;

        return true;
    }
}


/* VALIDATIONS
* create User
* requirements: phoneNumber -> phone number should not be null,
*               name -> Username should not be null,
* already exists: phone number -> duplicate entry for phoneNumber
*
* create Bank
* requirements: accountNumber -> accNo should not be null,
*               bankName -> Bank name should not be null
*               userId -> user Id should not be null
*
* already exists: userid -> user does not exist (foreign key violation)
*                  accountNumber, userid -> duplicate entry for (accNo, userId)
*
*
* Update User by userId
* requirements: userId -> userId should not be null
*
* already exists: userId -> user with given id does not exist,
*
* update bank by bankId
* requirements: userId -> userId should not be null
*               bankId -> bankId should not be null
*
* already exists: userId -> user with given id does not exist,
*                  (bankId -> check requirements to create new bank account)
* check create Bank()
*
* retrieve user by phone number
* requirement : phone number ->
* */

