package org.develop.UserComponent.domain;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String userType;

    public User(String firstName, String lastName, String email, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userType = "1"; // Default til standard bruker
    }

    public User(String firstName, String lastName, String email, String phoneNumber, String password, String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userType = userType;
    }

    public int getId() {
        return id;
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

    public String getFullName() {
        return firstName + " " + lastName;
    }
 
    public String getUserType() {
        return userType;
    }

    public String getUserTypeText() {
        if (userType == null || userType.trim().isEmpty()) return "Standard bruker";
        
        // Sjekk om det er et tall (1, 2, 3) eller tekst (user, admin, developer)
        switch (userType.trim()) {
            case "1":
            case "user":
                return "Standard bruker";
            case "2":
            case "admin":
                return "Admin bruker";
            case "3":
            case "developer":
                return "Utvikler bruker";
            default:
                return "Standard bruker";
        }
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }       
}
