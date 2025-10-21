package org.develop.service;

import java.util.ArrayList;

import org.develop.domain.model.User;


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