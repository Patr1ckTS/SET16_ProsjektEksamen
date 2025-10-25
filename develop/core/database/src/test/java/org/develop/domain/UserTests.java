package org.develop.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTests {
    @Test
    @DisplayName("Control of the User class constructor")
    public void user_checkThatUserAreCreatedWithAllValuesStored(){
// Arrange
        String firstName = "Eugenie";
        String lastName = "Lalande";
        String email = "e.lalande@coldmail.mock";
        String phoneNumber = "+44 0118 999 881 999 119 725 3";
        String password = "passord123";

// Act

        User eLalande = new User(firstName, lastName, email, phoneNumber, password);

// Assert
        Assertions.assertEquals(firstName, eLalande.getFirstName());
        Assertions.assertEquals(password, eLalande.getPassword());
        Assertions.assertEquals(phoneNumber, eLalande.getPhoneNumber());
    } 

    @Test
    @DisplayName("Alteration of User property check")
    public void user_checkIfUserPropertiesCanBeChanged(){
// Arrange
        User user = new User(
            "Roderick", 
            "Usher", 
            "r.usher@coldmail.mock", 
            "+32 93847563", 
            "oldPassword");

// Act
        user.setPassword("newPassword");

// Assert
        Assertions.assertEquals("newPassword", user.getPassword());
    }

    @Test
    @DisplayName("Full name function check")
    public void user_checkIfFullNameIsCorrectlyGenerated(){
// Arrange
        User aLee = new User(
            "Annabel", 
            "Lee",
            "a.lee@coldmail.mock",
            "+12 34567890",
            "somePassword");

// Act
        String fullName = aLee.getFullName();

// Assert
        Assertions.assertEquals("Annabel Lee", fullName);
    }
}

