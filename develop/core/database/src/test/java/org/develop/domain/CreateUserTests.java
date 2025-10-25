package org.develop.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateUserTests {
    @Test
    @DisplayName("Control of the User class constructor")
    public void createUser_checkThatUserAreCreatedWithAllValuesStored(){
// Arrange
        String firstName = "Arthur";
        String lastName = "Pym";
        String email = "a.pym@coldmail.mock";
        String phoneNumber = "+1 234 567 8901";
        String password = "mysteriousPass";

// Act
        CreateUser aPym = new CreateUser(firstName, lastName, email, phoneNumber, password);
        
// Assert
        Assertions.assertEquals(firstName, aPym.getFirstName());
        Assertions.assertEquals(lastName, aPym.getLastName());
        Assertions.assertEquals(email, aPym.getEmail());
        Assertions.assertEquals(phoneNumber, aPym.getPhoneNumber());
        Assertions.assertEquals(password, aPym.getPassword());
    }


    @Test
    @DisplayName("Check if user type is correctly set")
    public void createUser_checkIfUserTypeIsCorrectlySet(){
// Arrange
        CreateUser aSimpson = new CreateUser(
            "Adolphus",
            "Simpson",
            "a.simpson@coldmail.mock",
            "+98 765 432 1098",
            "defaultPass");

// Act & Assert
        Assertions.assertEquals("user", aSimpson.getUserType());
    }


    @Test
    @DisplayName("Check if values are immutable when set")
    public void createUser_checkIfValuesAreImmutable(){
// Arrange
        CreateUser defaultUser = new CreateUser(
            "Adolphus",
            "Simpson",
            "a.simpson@coldmail.mock",
            "+98 765 432 1098",
            "defaultPass");
    }
}