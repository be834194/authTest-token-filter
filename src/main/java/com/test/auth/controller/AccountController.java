package com.test.auth.controller;

import com.test.auth.dto.AccountResponseDto;
import com.test.auth.form.AccountForm;
import com.test.auth.service.AccountService;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ResponseEntity<?> registAccount(@RequestBody AccountForm request) {
        accountService.registAccount(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/account/{userName}", method = RequestMethod.GET)
    public ResponseEntity<?> getAccount(@PathVariable("userName") String userName) {
        try {
            AccountResponseDto response = accountService.findAccount(userName);
            if(Objects.isNull(response)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
