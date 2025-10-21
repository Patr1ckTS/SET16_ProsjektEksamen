package com.web;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TemplateLoader { 

    // ================================= //
    //      Generic Template Loader
    // ================================= //
    public static String loadTemplate(String templateName) {
        try {
            String path = "/templates/" + templateName;
            InputStream is = TemplateLoader.class.getResourceAsStream(path);
            if (is == null) {
                return "<html><body><h1>Template ikke funnet: " + path + "</h1></body></html>";
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return "<html><body><h1>Feil ved lasting av template: " + e.getMessage() + "</h1></body></html>";
        }
    }
    

    // ================================= //
    //      Header Template Loader
    // ================================= //
    public static String loadHeaderHTML() {
        try {
            InputStream is = TemplateLoader.class.getResourceAsStream("/templates/components/header.html");
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "<header><!-- Header kunne ikke lastes: " + e.getMessage() + " --></header>";
        }
    }

    // ================================= //
    //      Footer Template Loader
    // ================================= //
    public static String loadFooterHTML() {
        try {
            InputStream is = TemplateLoader.class.getResourceAsStream("/templates/components/footer.html");
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "<footer><!-- Footer kunne ikke lastes: " + e.getMessage() + " --></footer>";
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

    // Ser du har en template loader klasse her Patrick,
    // Men det ble krunglete nå jeg skulle laste header og footer hvor vi kan ha login session info.
    // Kanskje det finnes noen bedre løsninger på det senere.

}