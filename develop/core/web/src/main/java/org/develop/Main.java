package org.develop;

import org.develop.domain.DatabaseSetup;
import org.develop.service.Routes;
import org.develop.service.UserService;

import io.javalin.Javalin;

public class Main {
    
    public static void main(String[] args) {
        
        DatabaseSetup dbSetup = new DatabaseSetup(
            "se25_G16",
            "itstud.hiof.no",  
            "3306",
            "gruppe16",
            "Summer35"
        );
        
        // ===== Initialize UserService with DatabaseSetup ===== //
        UserService.initialize(dbSetup);
        
        // ===== Server Oppstart ===== //
        System.out.println("Starter Server...");
        System.out.println(dbSetup.showDatabaseInfo());
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/static"); 
        }).start(7000);

        Routes.configureRoutes(app);

        // =============================================== //
        //      Tests
        // =============================================== //
        
        // ===== Database Test ===== //
        System.out.println("Tester database tilkobling...");
        if (dbSetup.testConnection()) {
            System.out.println("Database tilkoblet!");
        } else {
            System.out.println("Database tilkobling feilet!");
        }
    }
}