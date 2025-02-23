package com.test.auth.dto;

import com.test.auth.entity.Account;

public class AccountResponseDto {
    private int id;

    private String userName;

    public AccountResponseDto(Account account){
        this.id = account.getId();
        this.userName = account.getUserName();
    }

    public int getId() {
		return id;
	}

    public String getUserName() {
		return userName;
	}
}
