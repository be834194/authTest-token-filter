package com.test.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.auth.entity.Account;
import com.test.auth.entity.UserDetailsImpl;
import com.test.auth.repository.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		if (userName == null || "".equals(userName)) {
			throw new UsernameNotFoundException(userName + "is not found");
		}

		Account account = accountRepository.findAccount(userName);
		if (account != null) {
			return new UserDetailsImpl(account); 
		}
		throw new UsernameNotFoundException(userName + "is not found");
		
	}

}
