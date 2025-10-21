package com.web;
import com.database.service.UserService;

import io.javalin.Javalin;

public class Routes {

    // ================================= // 
    //      Render header HTML med login status
    // ================================= //
    private static String getHeaderHTML(io.javalin.http.Context ctx, String pageTitle) {
        String loggedInEmail = ctx.sessionAttribute("userEmail");
        String headerHTML = TemplateLoader.loadHeaderHTML();
        String headerAuth;
        if (loggedInEmail != null) {
            String fullName = UserService.getNameByEmail(loggedInEmail);
            headerAuth =
                "<li><a href='/logout'>Logg ut</a></li>" +
                "<li><a href='/favoritter'>Favoritter</a></li>" +
                "<li><b>" + fullName + "</b></li>";
        } else {
            headerAuth =
                "<li><a href='/login'>Logg inn</a></li>" +
                "<li><a href='/registrer'>Registrer</a></li>";
        }
        headerHTML = headerHTML.replace("{{HEADER_AUTH}}", headerAuth);
        headerHTML = headerHTML.replace("{{PAGE_TITLE}}", pageTitle);
        return headerHTML;
    }

    public static void configureRoutes(Javalin app) {
        

        // ================================= //
        //      Hovedsside (index.html)
        // ================================= //
        app.get("/", ctx -> {
            String template = TemplateLoader.loadTemplate("index.html");
            String customHeader = getHeaderHTML(ctx, "Ruter - Hjem");
            String footerHTML = TemplateLoader.loadFooterHTML();

            String html = template
                // Generiske templatesq
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML);

                // Spesifikke templates
                
            ctx.contentType("text/html; charset=utf-8").result(html);
        });





        // ================================= //
        //      Brukere
        // ================================= //
        app.get("/brukere", ctx -> {
            String template = TemplateLoader.loadTemplate("brukere.html");
            String customHeader = getHeaderHTML(ctx, "Ruter - Brukere");
            String footerHTML = TemplateLoader.loadFooterHTML();

            String html = template
                // Generiske templates
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML)
                
                // Spesifikke templates
                .replace("{{USERS_INFO}}", UserService.getUserFullname());
            ctx.contentType("text/html; charset=utf-8").result(html);
        });


        // =============================================== //
        //      Login
        // =============================================== // 
        app.get("/login", ctx -> {

            String loggedInEmail = ctx.sessionAttribute("userEmail");
            if (loggedInEmail != null) {
                ctx.redirect("/");
                return;
            }

            String template = TemplateLoader.loadTemplate("login.html");
            String footerHTML = TemplateLoader.loadFooterHTML();
            String customHeader = getHeaderHTML(ctx, "Ruter - Hjem");

            String html = template
                // Generiske templates
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML);
                
                // Spesifikke templates
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        app.post("/login", ctx -> {
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");

            if (email == null || password == null) {
                ctx.status(400).result("<p style='color:red;'>E-post og passord må fylles ut</p>");
                return;
            }

            if (UserService.loginUser(email.trim(), password)) {
                ctx.sessionAttribute("userEmail", email.trim());
                ctx.redirect("/");
            } else {
                ctx.status(401).result("<p style='color:red;'>Feil e-post eller passord</p>");
            }
        });
 
        // =============================================== //
        //      Logout
        // =============================================== //
        app.get("/logout", ctx -> {
            ctx.sessionAttribute("userEmail", null);
            ctx.redirect("/");
        });


        // =============================================== //
        //      Registrering
        // =============================================== //
        app.get("/registrer", ctx -> {

            String loggedInEmail = ctx.sessionAttribute("userEmail");
            if (loggedInEmail != null) {
                ctx.redirect("/");
                return;
            }

            String template = TemplateLoader.loadTemplate("registrer.html");
            String customHeader = getHeaderHTML(ctx, "Ruter - registrer");
            String footerHTML = TemplateLoader.loadFooterHTML();

            String html = template
                // Generiske templates
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML);
                
                // Spesifikke templates
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        app.post("/registrer", ctx -> {
            String firstname = ctx.formParam("firstname");
            String lastname = ctx.formParam("lastname");
            String email = ctx.formParam("email");
            String phoneNumber = ctx.formParam("phonenumber");
            String password = ctx.formParam("password");
            String repeatPassword = ctx.formParam("repeatPassword");

            if (firstname != null && lastname != null && email != null && phoneNumber != null && password != null && !firstname.trim().isEmpty() && !lastname.trim().isEmpty()) {

                if (repeatPassword == null || !password.equals(repeatPassword)) {
                    ctx.status(400).result("<p style='color:red;'>Passordene er ikke like</p>");
                    return;
                }

                String errors = UserService.registrerUser(firstname.trim(), lastname.trim(), email.trim(), phoneNumber.trim(), password.trim());
                if (errors == null) {
                    System.out.println("Bruker lagret: " + firstname + " " + lastname);
                    ctx.redirect("/");
                } else {
                    ctx.status(400).result("<p style='color:red;'>" + errors + "</p>");
                }
            } else {
                ctx.status(400).result("<p style='color:red;'>Vennligst fyll ut alle feltene</p>");
            }
        });



        // =============================================== //
        //      Favoritter
        // =============================================== //
        app.get("/favoritter", ctx -> {
            
// Utkommentert på grunn a kjøre problemer
/*
            String loggedInEmail = ctx.sessionAttribute("userEmail");
            if (loggedInEmail == null) {
                ctx.redirect("/login");
                return;
            }
*/

            String template = TemplateLoader.loadTemplate("favoritter.html");
            String customHeader = getHeaderHTML(ctx, "Ruter - favoritter");
            String footerHTML = TemplateLoader.loadFooterHTML();

            String html = template
                // Generiske templates
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML);
                
                // Spesifikke templates
            ctx.contentType("text/html; charset=utf-8").result(html);
        });


    }
    
}