package com.nwo.demo.auth.dao;

import com.nwo.demo.auth.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserDAO {

    public Optional<ApplicationUser> selectApplicationUserByUsername(String username);

}
