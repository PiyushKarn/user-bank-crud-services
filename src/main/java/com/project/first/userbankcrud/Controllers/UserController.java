package com.project.first.userbankcrud.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.first.userbankcrud.Domain.UserDomain;
import com.project.first.userbankcrud.Repositories.UserRepository;
import com.project.first.userbankcrud.Response.Response;
import com.project.first.userbankcrud.Response.ResponseMessage;
import com.project.first.userbankcrud.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@RestController
@RequestMapping("/users")

public class UserController {
    @Autowired
    private UserService userServices;



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


    @PostMapping("/bulk/json")
    public String bulkUploadJson(@RequestBody List<UserDomain> userDomains) {
        String successString ="";
        try {
            return userServices.saveUserBulkJson(userDomains);
        } catch (Exception e){
            successString = "Failure";

        }
        return successString;
    }


    @PostMapping("/bulk/csv")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        String successString = "";
        try {
            Scanner scanner = new Scanner(new InputStreamReader(file.getInputStream(),"UTF-8"));

            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                try {

                    String[] strs = line.split(",");
                    UserDomain userDomain = new UserDomain(
                            strs[0],
                            Long.parseLong(strs[1]),
                            strs[2],
                            strs[3]
                    );
                    ResponseEntity<String> response= userServices.saveUser(userDomain);
                    successString += (line+" -- "+ response.toString() +"\n");
                }catch (Exception e)
                {
                    successString += (line+" -- "+"Invalid\n");
                }
            }
            scanner.close();

        } catch (Exception e) {
            //throw new RuntimeException(e);
            successString = "Failure";
        }
        return successString;
    }



}