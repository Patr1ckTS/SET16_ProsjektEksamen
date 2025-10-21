<<<<<<<< HEAD:Ruteplanlegger/ruteplanlegger/src/main/java/com/database/domain/interfaces/CreateUser.java
package com.database.domain.interfaces;
========
package org.develop.domain.model;
>>>>>>>> b34d500a5946829d7308cf50f2a57fef7f63a1f2:develop/core/database/src/main/java/org/develop/domain/model/CreateUserCommand.java

//    -Klasse for opprettelse av brukere-

public class CreateUser {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final String userType;

    public CreateUser(String firstName, String lastName, String email, String phoneNumber, String password, String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}