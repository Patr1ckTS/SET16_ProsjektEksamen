package org.develop.web;

import org.develop.Database.DatabaseUserAdapter;
import org.develop.Database.SQLDatabaseConnection;
import org.develop.Entur.EnturAdapter;
import org.develop.Service.RouteApplicationService;
import org.develop.Service.RouteLogic;
import org.develop.Service.StopLogic;
import org.develop.UserComponent.domain.DatabaseSetup;
import org.develop.UserComponent.service.UserService;
import org.develop.web.service.Routes;

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

        // Opprett UserService instans med adapter
        DatabaseUserAdapter userAdapter = new DatabaseUserAdapter(dbConnection);
        UserService userService = null;

        try {
            userService = new UserService(userAdapter, dbConnection.getConnection());
        } catch (java.sql.SQLException e) {
            System.err.println("Kunne ikke opprette UserService: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Opprett RouteApplicationService med dependencies
        StopLogic stopLogic = new StopLogic();
        RouteLogic routeLogic = new RouteLogic(stopLogic);
        EnturAdapter enturAdapter = new EnturAdapter();
        RouteApplicationService routeAppService = new RouteApplicationService(routeLogic, stopLogic, enturAdapter);

        // Starter Javalin Web Server
        System.out.println("Starter web server på port 7000...");
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/static");
        }).start(7000);

        // Konfigurer alle routes med UserService og RouteApplicationService
        Routes.configureRoutes(app, userService, routeAppService);
        
        System.out.println("Web server kjører på http://localhost:7000");
    }
}