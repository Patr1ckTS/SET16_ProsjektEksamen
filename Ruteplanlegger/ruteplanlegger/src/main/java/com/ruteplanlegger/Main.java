package com.ruteplanlegger;

import com.ruteplanlegger.domain.DatabaseSetup;
import com.ruteplanlegger.service.UserService;
import com.ruteplanlegger.webRelated.TemplateLoaders;

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
        
        // ===== Server Oppstart ===== //
        System.out.println("Starter Server...");
        System.out.println(DatabaseConfig.showDatabaseInfo());
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/static"); 
        }).start(7000);
        

        // =============================================== //
        //      Page routers
        // =============================================== //

        // ===== Hovedside ===== //
        app.get("/", ctx -> {
            String html = TemplateLoaders.groupedLoader("index.html", "Ruter - Hjem");
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        // ===== Brukere ===== //
        app.get("/users", ctx -> {
            String template = TemplateLoaders.loadTemplate("users.html");
            String headerHTML = TemplateLoaders.loadHeaderHTML();
            String footerHTML = TemplateLoaders.loadFooterHTML();
            String customHeader = headerHTML.replace("{{PAGE_TITLE}}", "Ruter - brukere");
            WeatherController weatherController = new WeatherController();

            String html = template
                // Generiske templates
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML)
                
                // Spesifikke templates
                .replace("{{USER_FULLNAME}}", UserService.getUserFullname())
                .replace("{{WEATHER_INFO}}", weatherController.checkWeather());
            ctx.contentType("text/html; charset=utf-8").result(html);
        });
        
        // ===== add-users ===== //
        app.get("/add-user", ctx -> {
            String html = TemplateLoaders.groupedLoader("add-user.html", "Ruter - Lägg till användare");
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        app.post("/add-user", ctx -> {
            String firstname = ctx.formParam("firstname");
            String lastname = ctx.formParam("lastname");
            String email = ctx.formParam("email");
            String phoneNumber = ctx.formParam("phonenumber");
            String password = ctx.formParam("password");

            if (firstname != null && lastname != null && email != null && phoneNumber != null && password != null && !firstname.trim().isEmpty() && !lastname.trim().isEmpty()) {
                boolean success = UserService.addUser(firstname.trim(), lastname.trim(), email.trim(), phoneNumber.trim(), password.trim());
                if (success) {
                    System.out.println("Bruker lagret: " + firstname + " " + lastname);
                    ctx.redirect("/users");
                } else {
                    ctx.status(500).result("Feil ved lagring av bruker");
                }
            } else {
                ctx.status(400).result("Fornavn, etternavn, e-post og telefonnummer er påkrevd");
            }
        });

        // ===== Favoritter ===== //
        app.get("/favoritter", ctx -> {
            String html = TemplateLoaders.groupedLoader("favorite-route.html", "Ruter - Favoritter");
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        // =============================================== //
        //      Tests
        // =============================================== //
        
        // ===== Database Test ===== //
        System.out.println("Tester database tilkobling...");
        if (DatabaseConfig.testConnection()) {
            System.out.println("Database tilkoblet!");
        } else {
            System.out.println("Database tilkobling feilet!");
        }
    }
}