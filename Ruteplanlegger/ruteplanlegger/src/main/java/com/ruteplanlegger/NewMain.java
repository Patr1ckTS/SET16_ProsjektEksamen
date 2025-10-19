package com.ruteplanlegger;

import com.ruteplanlegger.domain.DatabaseSetup;
import com.ruteplanlegger.service.DatabaseConnection;

public class NewMain {
    
    DatabaseSetup dbSetup = new DatabaseSetup(
        "se25_G16",
        "itstud.hiof.no",  
        "3306",
        "gruppe16",
        "Summer35"
    );

    DatabaseConnection connect = new DatabaseConnection(dbSetup.getDbUrl(), dbSetup.getDbUsername(), dbSetup.getDbPassword());
}
