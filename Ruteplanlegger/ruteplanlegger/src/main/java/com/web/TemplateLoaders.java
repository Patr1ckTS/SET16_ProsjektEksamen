package com.web;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TemplateLoaders {
    // =============================================== //
    //      Template Loaders
    // =============================================== //
    
    // ===== Hjelpefunksjon for å laste HTML templates ===== //
    public static String loadTemplate(String templateName) {
        try {
            InputStream is = TemplateLoaders.class.getResourceAsStream("/templates/" + templateName);
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "<html><body><h1>Feil ved lasting av template: " + e.getMessage() + "</h1></body></html>";
        }
    }

    // ===== Header Template Loader ===== //
    public static String loadHeaderHTML() {
        try {
            InputStream is = TemplateLoaders.class.getResourceAsStream("/templates/components/header.html");
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "<header><!-- Header kunne ikke lastes: " + e.getMessage() + " --></header>";
        }
    }

    // ===== Footer Template Loader ===== //
    public static String loadFooterHTML() {
        try {
            InputStream is = TemplateLoaders.class.getResourceAsStream("/templates/components/footer.html");
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "<footer><!-- Footer kunne ikke lastes --></footer>";
        }
    }

//  Grupperte templates for forenklet visning:    
    public static String groupedLoader(String templateName, String title){
        String template = loadTemplate(templateName);
        String headerHTML = loadHeaderHTML();
        String footerHTML = loadFooterHTML();
        String customHeader = headerHTML.replace("{{PAGE_TITLE}}", title);
        
        return template.replace("{{HEADER}}", customHeader).replace("{{FOOTER}}", footerHTML);
    }
}