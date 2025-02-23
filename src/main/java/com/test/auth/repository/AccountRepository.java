package com.test.auth.repository;

import org.apache.ibatis.annotations.Mapper;

import com.test.auth.entity.Account;

@Mapper
public interface AccountRepository {
    
    void insertAccount(Account account);

    Account findAccount(String userName);
}
