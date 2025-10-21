package com.database.service;

import java.util.ArrayList;

import com.database.domain.interfaces.User;

public class UserListFormatter {
    public String formatUserList(ArrayList<User> users){
        if(users.isEmpty()){
            return "<li>Ingen brukere funnet</li>";
        }

        StringBuilder result = new StringBuilder();
        for(User user : users){
            result.append("<li>")
                .append(user.getFullName())
                .append(" | ").append(user.getEmail())
                .append(" | ").append(user.getPhoneNumber())
                .append("</li>");
        }
        return result.toString();
    }
}