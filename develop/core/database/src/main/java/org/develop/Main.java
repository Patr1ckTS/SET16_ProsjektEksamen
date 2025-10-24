package org.develop;

import org.develop.domain.DatabaseSetup;

public class Main {
    
    public static void main(String[] args) {
        DatabaseSetup dbSetup = new DatabaseSetup(
            "se25_G16",
            "itstud.hiof.no",  
            "3306",
            "gruppe16",
            "Summer35"
        );
        
        System.out.println("Database Info:");
        System.out.println(dbSetup.showDatabaseInfo());
        
        System.out.println("\nTester database tilkobling...");
        if (dbSetup.testConnection()) {
            System.out.println("Database tilkoblet!");
        } else {
            System.out.println("Database tilkobling feilet!");
        }
    }
}
