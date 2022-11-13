package com.project.first.userbankcrud.Tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.first.userbankcrud.Domain.CombinedObject;
import com.project.first.userbankcrud.Services.CombinedService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CombinedControllerTest {
    @MockBean
    private CombinedService combinedServices;

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    private CombinedObject combinedObject;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    public void setup(){
        combinedObject = new CombinedObject(123l,123l,"Piyush",7540056206l,"Bangalore","bankName","accountNumber","ifscCode","additionalDetailsUser","additionalDetailsBank");
    }

//    @Test
//    public void testFindAllCombinedUsersAndBanks() throws Exception {
//        CombinedObject combinedObject = new CombinedObject(123l,123l,"Piyush",7540056206l,"Bangalore","bankName","accountNumber","ifscCode","additionalDetailsUser","additionalDetailsBank");
//        List<CombinedObject> users = Arrays.asList(combinedObject);
//        Long phoneNumber = combinedObject.getPhoneNumber();
//        when(combinedServices.getCombinedDomainByPhoneNumber(phoneNumber)).thenReturn(users);
//        mockMvc.perform(get("http://localhost:9090/combined/{phoneNumber}"))
//                .andExpect(status().isOk());
//    }

//    @Test
//    public void testIfGetUsersListGettingGenerated() throws Exception{
//        when(userServices.findAll()).thenReturn(Collections.singletonList(userDomain));
//        mockMvc.perform(get("http://localhost:9090/users/"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$").isArray());
//    }
//
//    @Test
//    public void testCreateUser() throws Exception {
//        when(userServices.saveUser(userDomain)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        Assertions.assertNotNull(userDomain);
//        Assertions.assertEquals(123, userDomain.getId());
//    }
//
//    @Test
//    public void testGetUserById_IsUserIdMatching() throws Exception {
//        when(userServices.findById(123L)).thenReturn(Optional.ofNullable(userDomain));
//        mockMvc.perform(get("http://localhost:9090/users/123"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(123)));
//    }
//
//    @Test
//    public void testIfUserPhoneNumberNotEmpty() throws Exception {
//        when(userServices.findById(123L)).thenReturn(Optional.ofNullable(userDomain));
//        mockMvc.perform(get("http://localhost:9090/users/123"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.phoneNumber").isNotEmpty());
//    }
//
//    @Test
//    public void testIfUserWithGivenIdIsPresent() throws Exception {
//        when(userServices.findById(123L)).thenReturn(Optional.ofNullable(userDomain));
//        mockMvc.perform(get("http://localhost:9090/users/123"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(123)));
//    }
//
//    @Test
//    public void testIfUserIsGettingDeleted() throws Exception {
//        when(userServices.deleteUser(userDomain)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        mockMvc.perform(delete("http://localhost:9090/users/")
//                        .content(objectMapper.writeValueAsString(userDomain.getId()))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().is4xxClientError());
//    }

}