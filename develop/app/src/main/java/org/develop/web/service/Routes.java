package org.develop.web.service;

import io.javalin.Javalin;
import org.develop.UserComponent.service.UserService;
import org.develop.Service.RouteApplicationService;
import org.develop.Service.RouteLogic;
import org.develop.Service.StopLogic;
import org.develop.Service.dto.ResultDTO;

public class Routes {

    //      Render header HTML med login status
    private static String getHeaderHTML(io.javalin.http.Context ctx, String pageTitle, UserService userService) {
        String loggedInEmail = ctx.sessionAttribute("userEmail");
        String headerHTML = TemplateLoader.loadHeaderHTML();
        String headerAuth;
        String userFirstName = "Profil"; // Default for logged-out users
        if (loggedInEmail != null) {
            String fullName = userService.getNameByEmail(loggedInEmail);
            // Extract first name from full name
            if (fullName != null && !fullName.isEmpty()) {
                String[] nameParts = fullName.split(" ");
                userFirstName = nameParts[0]; // Get first name
            }
            headerAuth =
                "<ul>" +
                "<li><a href='/logout'>Logg ut</a></li>" +
                "<li><a href='/favoritter'>Favoritter</a></li>" +
                "<li><b>" + fullName + "</b></li>" +
                "</ul>";
        } else {
            headerAuth =
                "<ul>" +
                "<li><a href='/login'>Logg inn</a></li>" +
                "<li><a href='/registrer'>Registrer</a></li>" +
                "</ul>";
        }
        headerHTML = headerHTML.replace("{{HEADER_AUTH}}", headerAuth);
        headerHTML = headerHTML.replace("{{USER_FIRST_NAME}}", userFirstName);
        headerHTML = headerHTML.replace("{{PAGE_TITLE}}", pageTitle);
        return headerHTML;
    }

    public static void configureRoutes(Javalin app, UserService userService, RouteApplicationService routeAppService) {
        

        // Hovedsside (index.html) - med rutesøk
        app.get("/", ctx -> {
            String template = TemplateLoader.loadTemplate("index.html");
            String customHeader = getHeaderHTML(ctx, "Ruter - Hjem", userService);
            String footerHTML = TemplateLoader.loadFooterHTML();

            String html = template
                // Generiske templates
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML)
                // Tomt rutesøk-resultat ved første besøk
                .replace("{{ROUTE_RESULTS}}", "");
                
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        //      Brukere
        app.get("/brukere", ctx -> {
            String template = TemplateLoader.loadTemplate("brukere.html");
            String customHeader = getHeaderHTML(ctx, "Ruter - Brukere", userService);
            String footerHTML = TemplateLoader.loadFooterHTML();

            String html = template
                // Generiske templates
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML)

                // Spesifikke templates
                .replace("{{USERS_INFO}}", userService.getUserFullname());
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        // Engelsk alias for /brukere
        app.get("/users", ctx -> {
            String template = TemplateLoader.loadTemplate("brukere.html");
            String customHeader = getHeaderHTML(ctx, "Ruter - Brukere", userService);
            String footerHTML = TemplateLoader.loadFooterHTML();

            String html = template
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML)
                .replace("{{USERS_INFO}}", userService.getUserFullname());
            ctx.contentType("text/html; charset=utf-8").result(html);
        });


        //      Login
        app.get("/login", ctx -> {

            String loggedInEmail = ctx.sessionAttribute("userEmail");
            if (loggedInEmail != null) {
                ctx.redirect("/");
                return;
            }

            String template = TemplateLoader.loadTemplate("login.html");
            String footerHTML = TemplateLoader.loadFooterHTML();
            String customHeader = getHeaderHTML(ctx, "Ruter - Hjem", userService);

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

            if (userService.loginUser(email.trim(), password)) {
                ctx.sessionAttribute("userEmail", email.trim());
                ctx.redirect("/");
            } else {
                ctx.status(401).result("<p style='color:red;'>Feil e-post eller passord</p>");
            }
        });
 
        //      Logout
        app.get("/logout", ctx -> {
            ctx.sessionAttribute("userEmail", null);
            ctx.redirect("/");
        });


        //      Registrering
        app.get("/registrer", ctx -> {

            String loggedInEmail = ctx.sessionAttribute("userEmail");
            if (loggedInEmail != null) {
                ctx.redirect("/");
                return;
            }

            String template = TemplateLoader.loadTemplate("registrer.html");
            String customHeader = getHeaderHTML(ctx, "Ruter - registrer", userService);
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

                String errors = userService.registerUser(firstname.trim(), lastname.trim(), email.trim(), phoneNumber.trim(), password.trim());
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



        //      Favoritter
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
            String customHeader = getHeaderHTML(ctx, "Ruter - favoritter", userService);
            String footerHTML = TemplateLoader.loadFooterHTML();

            String html = template
                // Generiske templates
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML);
                
                // Spesifikke templates
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        //      Kalender
        app.get("/kalender", ctx -> {
            String template = TemplateLoader.loadTemplate("calendar.html");
            String customHeader = getHeaderHTML(ctx, "Ruter - Kalender", userService);
            String footerHTML = TemplateLoader.loadFooterHTML();

            String html = template
                // Generiske templates
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML);
                
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        //      Profil
        app.get("/profil", ctx -> {
            // Sjekk om bruker er logget inn
            String loggedInEmail = ctx.sessionAttribute("userEmail");
            if (loggedInEmail == null) {
                ctx.redirect("/login");
                return;
            }

            String template = TemplateLoader.loadTemplate("profil.html");
            String customHeader = getHeaderHTML(ctx, "Ruter - Min Profil", userService);
            String footerHTML = TemplateLoader.loadFooterHTML();

            // Hent brukerdata
            String fullName = userService.getNameByEmail(loggedInEmail);
            String phoneNumber = userService.getPhoneNumberByEmail(loggedInEmail);
            String userType = userService.getUserTypeByEmail(loggedInEmail);

            String html = template
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML)
                .replace("{{USER_NAME}}", fullName != null ? fullName : "Ikke tilgjengelig")
                .replace("{{USER_EMAIL}}", loggedInEmail)
                .replace("{{USER_PHONE}}", phoneNumber != null ? phoneNumber : "Ikke tilgjengelig")
                .replace("{{USER_TYPE}}", userType != null ? userType : "Ikke tilgjengelig");
                
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        // =============================================== //
        //      Rutesøk
        // =============================================== //
        app.get("/rutesok", ctx -> {
            String template = TemplateLoader.loadTemplate("rutesok.html");
            String customHeader = getHeaderHTML(ctx, "Ruter - Rutesøk", userService);
            String footerHTML = TemplateLoader.loadFooterHTML();

            String html = template
                .replace("{{HEADER}}", customHeader)
                .replace("{{FOOTER}}", footerHTML)
                .replace("{{ROUTE_RESULTS}}", ""); // Tomt ved første besøk
                
            ctx.contentType("text/html; charset=utf-8").result(html);
        });

        app.post("/rutesok", ctx -> {
            try {
                // Hent form-data
                String startLocation = ctx.formParam("startLocation");
                String endLocation = ctx.formParam("endLocation");
                String departureTime = ctx.formParam("departureTime");
                
                // Valider input
                if (startLocation == null || startLocation.trim().isEmpty() ||
                    endLocation == null || endLocation.trim().isEmpty() ||
                    departureTime == null || departureTime.trim().isEmpty()) {
                    
                    ctx.status(400).result("<p style='color:red;'>Alle felt må fylles ut</p>");
                    return;
                }
                
                // Bruk ekte rutesøk-logikk
                ResultDTO result = routeAppService.searchRoute(
                    startLocation.trim(),
                    endLocation.trim(), 
                    departureTime.trim()
                );
                
                String resultHTML = createResultHTML(result);
                
                // Render index.html (ikke rutesok.html) med resultat
                String template = TemplateLoader.loadTemplate("index.html");
                String customHeader = getHeaderHTML(ctx, "Ruter - Hjem", userService);
                String footerHTML = TemplateLoader.loadFooterHTML();

                String html = template
                    .replace("{{HEADER}}", customHeader)
                    .replace("{{FOOTER}}", footerHTML)
                    .replace("{{ROUTE_RESULTS}}", resultHTML);
                    
                ctx.contentType("text/html; charset=utf-8").result(html);
                
            } catch (Exception e) {
                ctx.status(500).result("<p style='color:red;'>Feil ved rutesøk: " + e.getMessage() + "</p>");
            }
        });


    }
    
    /**
     * Lager HTML for rutesøk-resultat
     */
    private static String createResultHTML(ResultDTO result) {
        if (!result.isSuccess()) {
            return String.format(
                "<div class='error-result'>" +
                "<h3>Søket mislyktes</h3>" +
                "<p>%s</p>" +
                "</div>", 
                result.getMessage()
            );
        }
        
        return String.format(
            "<div class='success-result'>" +
            "<h3>Rute funnet!</h3>" +
            "<div class='route-info'>" +
            "<p><strong>Transport:</strong> %s</p>" +
            "<p><strong>Rute:</strong> %s</p>" +
            "<p><strong>Fra:</strong> %s (Avgang: %s)</p>" +
            "<p><strong>Til:</strong> %s (Ankomst: %s)</p>" +
            "<p><strong>Reisetid:</strong> %d minutter</p>" +
            "<p><strong>Avgangstid fra terminal:</strong> %s</p>" +
            "</div>" +
            "<button class='buy-ticket-button'>Kjøp billett</button>" +
            "</div>",
            result.getTransportType() != null ? result.getTransportType() : "Ukjent",
            result.getRouteName() != null ? result.getRouteName() : "Ukjent rute",
            result.getStartLocation() != null ? result.getStartLocation() : "Ukjent",
            result.getArrivalAtStartStop() != null ? result.getArrivalAtStartStop() : "Ukjent",
            result.getEndLocation() != null ? result.getEndLocation() : "Ukjent", 
            result.getArrivalAtEndStop() != null ? result.getArrivalAtEndStop() : "Ukjent",
            result.getTravelTime(),
            result.getDepartureFromTerminal() != null ? result.getDepartureFromTerminal() : "Ukjent"
        );
    }
    
}