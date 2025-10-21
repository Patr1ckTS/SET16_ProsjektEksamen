<<<<<<<< HEAD:develop/core/database/src/main/java/org/develop/service/UserListFormatter.java
package com.database.service;

import java.util.ArrayList;

import com.database.domain.interfaces.User;
========
package org.develop.service;

import java.util.ArrayList;

import org.develop.domain.model.User;

>>>>>>>> b34d500a5946829d7308cf50f2a57fef7f63a1f2:develop/core/web/src/main/java/org/develop/service/UserListFormatter.java

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