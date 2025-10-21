package com.web.interfaces;

public interface WebUserService {
    String getUsersAsHtml();
    boolean loginUser(String email, String password);
    String registerUser(String firstname, String lastname, String email, String phoneNumber, String password);
    String getUserNameByEmail(String email);
}