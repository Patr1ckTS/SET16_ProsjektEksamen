package com.web.service;

import com.database.service.UserService;
import com.web.interfaces.WebUserService;

public class DatabaseWebUserService implements WebUserService {
    @Override
    public String getUsersAsHtml() {  // Changed from getUserFullname
        return UserService.getUserFullname();
    }

    @Override
    public boolean loginUser(String email, String password) {
        return UserService.loginUser(email, password);
    }

    @Override
    public String registerUser(String firstname, String lastname, String email, String phoneNumber, String password) {
        return UserService.registerUser(firstname, lastname, email, phoneNumber, password);  // Fixed method name
    }

    @Override
    public String getUserNameByEmail(String email) {
        return UserService.getUserNameByEmail(email);
    }
}