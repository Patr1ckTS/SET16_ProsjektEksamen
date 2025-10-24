package org.develop;

import org.develop.domain.DatabaseSetup;
import org.develop.service.Routes;
import org.develop.service.UserService;
import org.develop.Database.SQLDatabaseConnection;
import org.develop.Database.DatabaseUserRepository;

import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        // Initialiser Database 
        DatabaseSetup dbSetup = new DatabaseSetup(
            "se25_G16",
            "itstud.hiof.no",
            "3306",
            "gruppe16",
            "Summer35"
        );

        System.out.println("Konfigurerer database...");
        System.out.println(dbSetup.showDatabaseInfo());
        
        // Koble til Database (Krever skolenettverk / VPN)
        SQLDatabaseConnection dbConnection = new SQLDatabaseConnection(
            dbSetup.getDbUrl(),
            dbSetup.getDbUsername(),
            dbSetup.getDbPassword()
        );
        
        // Initialiser UserService med repository 
        DatabaseUserRepository userRepository = new DatabaseUserRepository(dbConnection);
        UserService.initialize(userRepository);
        
        // Starter Javalin Web Server 
        System.out.println("Starter web server på port 7000...");
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/static"); 
        }).start(7000);

        // Konfigurer alle routes 
        Routes.configureRoutes(app);
        
        System.out.println("Web server kjører på http://localhost:7000");
    }
}