package com.project.first.userbankcrud.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.first.userbankcrud.Domain.UserDomain;
import com.project.first.userbankcrud.Repositories.UserRepository;
import com.project.first.userbankcrud.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")

public class UserController {
    @Autowired
    private UserService userServices;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/userList")
    public Iterable<UserDomain> list() {
        return userServices.list();
    }

    @GetMapping() //getALLTickets
    public List<UserDomain> allUsers() {
        return userServices.findAll();

    }

    @PostMapping()  //AddNewUser
    public ResponseEntity<String> addUser(@RequestBody UserDomain userdata)
    {
        return userServices.saveUser(userdata);
    }

    @PutMapping()   //updateExistingUsers
    public ResponseEntity<String> updateUser(@RequestBody UserDomain newUserData)
    {
        return userServices.updateUser(newUserData);
    }

    @GetMapping("/{id}")    //findUserById
    public Optional<UserDomain> getUserById(@PathVariable Long id) {
        Optional<Optional<UserDomain>> userDomain = Optional.ofNullable(userServices.findById(id));
        if (userDomain.isPresent()) {
            return userDomain.get();
        } else {
            return null;
        }
    }

//    @GetMapping("/{id}")
//    public String getUserById(@PathVariable Long id){
//        String userDomain = userServices.findById(id);
//        if (userDomain.isEmpty()){
//            return "User with given Id not present";
//        }
//        else
//            return userDomain.;
//    }



    @DeleteMapping()       //deleteUserById
    public ResponseEntity<String> deleteUser(@RequestBody UserDomain deleteUserData){
        return userServices.deleteUser(deleteUserData);
    }

//@DeleteMapping()       //deleteUserById
//public String deleteUser(@RequestBody UserDomain deleteUserData){
//    return userServices.deleteUser(deleteUserData);
//}

//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
//        boolean deleteUserById = userServices.deleteUserById(id);
//        if (deleteUserById) {
//            return new ResponseEntity<>(("User deleted - Order ID:" + id), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(("User deletion failed - Order ID:" + id), HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/pagination")    //findUserByPagination
    public List<UserDomain> getUserById(@RequestParam int offset, @RequestParam int limit) {
        Page<UserDomain> pages = userServices.findUsersWithPagination(offset,limit);
        return pages.getContent();
    }

    @GetMapping(value = "/callAccounts")
    private String getAccounts(){
        String uri = "http://localhost:9090/accounts/";
        RestTemplate restTemplate = new RestTemplate();
        String results = restTemplate.getForObject(uri, String.class);
        return results;
    }

    @PostMapping("/bulk/csv")
    public String bulkUpload(){
        return userServices.saveUserBulkCsv();
    }

//    @PostMapping("/bulk/json")
//    public String bulkUploadJson(@RequestBody List<UserDomain> userDomain){
//        return userServices.saveUserBulkJson(userDomain);
//    }

}