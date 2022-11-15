package com.project.first.userbankcrud.Services;

import com.project.first.userbankcrud.Domain.UserDomain;
import com.project.first.userbankcrud.Exception.UserNotFoundException;
import com.project.first.userbankcrud.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<UserDomain> findAll() {
        List<UserDomain> allUsers = userRepository.findAll();
        //System.out.println(allUsers);
        return allUsers;
    }
    @Override
    public ResponseEntity<String> saveUser(UserDomain user) {

        try {
            userRepository.save(user);
            return new ResponseEntity<String>("User Added Successfully", HttpStatus.OK);
        }
        catch (DataIntegrityViolationException e) {
            System.out.println("Error!");
        }
        return new ResponseEntity<String>("Duplicate entry exists!!", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<String> updateUser(UserDomain newUserData) {

        UserDomain existingUser
                = userRepository.findById(newUserData.getId())
                .orElse(null);
        if (existingUser == null) {
            return new ResponseEntity<String>("No such user exists!!", HttpStatus.OK);
        }
        else {
            userRepository.save(newUserData);
            return new ResponseEntity<String>("User data updated!!", HttpStatus.OK);
        }
    }


    @Override
    public Optional<UserDomain> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public ResponseEntity<String> deleteUser(UserDomain deleteUserData) {

        UserDomain existingUser
                = userRepository.findById(deleteUserData.getId())
                .orElse(null);
        if (existingUser != null) {
            userRepository.delete(deleteUserData);
            return new ResponseEntity<String>("User deleted successfully!", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("No such user exists!", HttpStatus.OK);
        }
    }

    @Override
    public Page<UserDomain> findUsersWithPagination(int offset,int limit){

        try {
            Page<UserDomain> users = userRepository.findAll(PageRequest.of(offset, limit));
            System.out.println(users);
            return users;
        }
        catch (IllegalArgumentException e) {
            System.out.println("Please enter valid offset and limit");

        }
        Page<UserDomain> users = userRepository.findAll(PageRequest.of(0, 5));
        System.out.println("Passing default values to limit and offset");

        return users;
    }

    @Override
    public Iterable<UserDomain> list() {
        return userRepository.findAll();
    }
    @Override
    public void save(List<UserDomain> users){
        userRepository.saveAll(users);
    }
    String line = "";

    @Override
    public String saveUserBulkJson(List<UserDomain> userDomain) {
        try{
            userRepository.saveAll(userDomain);
            return "User Added Successfully";
        } catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public String saveUserBulkCsv(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/csv/users.csv"));
            while((line=br.readLine())!=null){
                String [] data = line.split(",");
                UserDomain u = new UserDomain();
                u.setName(data[0]);
                u.setPhoneNumber(Long.valueOf(data[1]));
                u.setAddress(data[2]);
                u.setAdditionalDetailsUser(data[3]);

                userRepository.save(u);
            }
            return "Successfully updated";
        }catch(Exception e){
            return e.getMessage();
        }
    }



}
