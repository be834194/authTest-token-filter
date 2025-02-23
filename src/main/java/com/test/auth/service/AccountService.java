package com.test.auth.service;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.auth.dto.AccountResponseDto;
import com.test.auth.entity.Account;
import com.test.auth.form.AccountForm;
import com.test.auth.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

	private final PasswordEncoder passwordEncoder;

	public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
	}

    public void registAccount(AccountForm form) {
        form.setPassword(passwordEncoder.encode(form.getPassword()));

        Account account = new Account();
        account.setUserName(form.getUserName());
        account.setPassword(form.getPassword());
        account.setRoleName("USER");

        accountRepository.insertAccount(account);
    }

    public AccountResponseDto findAccount(String userName) {
        Account account = accountRepository.findAccount(userName);
        if(Objects.isNull(account)) {
            return null;
        }
        return new AccountResponseDto(account);
    }

}
