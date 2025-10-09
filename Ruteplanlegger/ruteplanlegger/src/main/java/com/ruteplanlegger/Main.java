package com.ruteplanlegger;
import io.javalin.Javalin;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Main {

    // =============================================== //
    //      Template Loaders
    // =============================================== //
    
    // ===== Hjelpefunksjon for å laste HTML templates ===== //
    private static String loadTemplate(String templateName) {
        try {
            InputStream is = Main.class.getResourceAsStream("/templates/" + templateName);
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "<html><body><h1>Feil ved lasting av template: " + e.getMessage() + "</h1></body></html>";
        }
    }

    // ===== Header Template Loader ===== //
    private static String loadHeaderHTML() {
        try {
            InputStream is = Main.class.getResourceAsStream("/templates/components/header.html");
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "<header><!-- Header kunne ikke lastes: " + e.getMessage() + " --></header>";
        }
    }

    // ===== Footer Template Loader ===== //
    private static String loadFooterHTML() {
        try {
            InputStream is = Main.class.getResourceAsStream("/templates/components/footer.html");
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "<footer><!-- Footer kunne ikke lastes --></footer>";
        }
    }

    public static void main(String[] args) {

        // ===== Server Oppstart ===== //
        System.out.println("Starter Server...");
        System.out.println(DatabaseConfig.showDatabaseInfo());
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/static"); //Rooter til CSS/JS fra resources/static
        }).start(7000);
        

        // =============================================== //
        //      Page routers
        // =============================================== //

        // ===== Hovedside ===== //
        app.get("/", ctx -> {
            String template = loadTemplate("index.html");
            String headerHTML = loadHeaderHTML();
            String footerHTML = loadFooterHTML();
            String customHeader = headerHTML.replace("{{PAGE_TITLE}}", "Ruter - Hjem");

            String html = template
                // Generiske templates
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML);

                // Spesifikke templates
                
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        // ===== Brukere ===== //
        app.get("/users", ctx -> {
            String template = loadTemplate("users.html");
            String headerHTML = loadHeaderHTML();
            String footerHTML = loadFooterHTML();
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
            String template = loadTemplate("add-user.html");
            String html = template;
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
            String template = loadTemplate("favorite-route.html");
            String headerHTML = loadHeaderHTML();
            String footerHTML = loadFooterHTML();
            String customHeader = headerHTML.replace("{{PAGE_TITLE}}", "Ruter - favoritter");

            String html = template
                // Generiske templates
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML);
                
                // Spesifikke templates
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


