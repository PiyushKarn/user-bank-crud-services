package com.project.first.userbankcrud.Tests;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.first.userbankcrud.Domain.BankDomain;
import com.project.first.userbankcrud.Services.BankService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(UserController.class)
@SpringBootTest
@AutoConfigureMockMvc

public class BankControllerTest {

    @MockBean
    private BankService bankServices;

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    private BankDomain bankDomain;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    public void setup(){
        bankDomain = new BankDomain(101l,101l,"HDFC","101","ifscTest","Additional Details Test");
    }

    @Test
    public void testFindAllAccounts() throws Exception {
        BankDomain bankDomain = new BankDomain(101l,101l,"HDFC","101","ifscTest","Additional Details Test");
        List<BankDomain> banks = Arrays.asList(bankDomain);
        when(bankServices.findAll()).thenReturn(banks);
        mockMvc.perform(get("http://localhost:9090/accounts/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testIfAccountsListisGettingGenerated() throws Exception{
        when(bankServices.findAll()).thenReturn(Collections.singletonList(bankDomain));
        mockMvc.perform(get("http://localhost:9090/accounts/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testIfUserIdMatchesForCreatedAccount() throws Exception {
        when(bankServices.saveAccount(bankDomain)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        Assertions.assertNotNull(bankDomain);
        Assertions.assertEquals(101, bankDomain.getId());
    }

    @Test
    public void testGetAccountById() throws Exception {
        when(bankServices.findById(101L)).thenReturn(Optional.ofNullable(bankDomain));
        mockMvc.perform(get("http://localhost:9090/accounts/101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId", is(101)))
                .andExpect(jsonPath("$.id", is(101)))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testIfAccountNumberNotEmpty() throws Exception {
        when(bankServices.findById(101L)).thenReturn(Optional.ofNullable(bankDomain));
        mockMvc.perform(get("http://localhost:9090/accounts/101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountNumber").isNotEmpty());
    }

    @Test
    public void testIfAccountIsGettingDeleted() throws Exception {
        when(bankServices.deleteAccount(bankDomain)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(delete("http://localhost:9090/accounts/")
                        .content(objectMapper.writeValueAsString(bankDomain.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testIfAccountNumberNotPassed() throws Exception{

    }
    @Test
    public void testIfUserIdNotPassed() throws Exception{

    }
    @Test
    public void testIfBankNameNotPassed() throws Exception{

    }

}
