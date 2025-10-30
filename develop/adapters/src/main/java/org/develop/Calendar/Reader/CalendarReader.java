package org.develop.Calendar.Reader;

import org.develop.Calendar.DTO.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class CalendarReader {

    private final ObjectMapper objectMapper;

    public CalendarReader() {
        this.objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//hopper over felter i json filen som ikke er i constuctøren til java classen. Istedenfor å kaster error.
    }

    //leser inn fra json filer
    public ArrayList<PersonCalendarDTO> readPersonCalendar(String fileName) {  
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("Calendar/" + fileName);
            if (stream == null) {
                System.err.println("Feil: Kunne ikke finne ressurs Calendar/" + fileName);
                return null;
            }
            
            //deserialiserer json filen og mapper den.
            ArrayList<PersonCalendarDTO> person = objectMapper.readValue(stream,objectMapper.getTypeFactory().constructCollectionType(List.class, PersonCalendarDTO.class));

            //Hvis json filen er tom eller ikke inneholder person.
            if(person.isEmpty()){
                System.out.println("Fant ingen person i " + fileName);
                return person;
            }
            
            //Printer ut hvilken persons kalender er lastet.
            PersonCalendarDTO owner = person.get(0);
            System.out.println("Leste kalender for: " + owner.getName() + " fra " + fileName);;
            
            return person;

        } catch (IOException e) {
            System.err.println("Feil ved lesing av " + fileName + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //Henter inn kalendere
    public ArrayList<PersonCalendarDTO> getIda(){
        return readPersonCalendar("Ida_Calendar.json");
    }

    public ArrayList<PersonCalendarDTO> getOle(){
        return readPersonCalendar("Ole_Calendar.json");
    }

   
    
}
