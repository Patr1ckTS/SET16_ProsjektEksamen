# Navn og profiler

| Navn              | Profilikon                                   | Link til profil                 |
| ----------------- | -------------------------------------------- | ------------------------------- |
| Henrik Sørensen   | ![Sørensens Pikachu](/Profiles/Henrik_S.png)            | https://github.com/Spoopy-Bot   |
| Torgrim Aune      | ![Aunes standardikon (grønn)](/Profiles/Torgrim.png)   | https://github.com/Torgria      |
| Yazan Alarid      | ![Alarids standardikon (blått)](/Profiles/Yazan.png) | https://github.com/Yazan-Z      |
| Patrick T Skjelle | ![Patricks standardikon (blått)](/Profiles/Patrick.png)  | https://github.com/Patr1ckTS    |
| Henrik Berg       | ![Bergs standardikon (rosa)](/Profiles/Henrik_B.png)    | https://github.com/HenrikBerg99 |

---

# Oppstart av SET16 Reiseplanlegger
## Forutsetninger
- Java 21
- Maven 3.x
- MySQL
- Git
- Tilgang til HIOF-nettverket

## Oppsett
**Repository URL:**
https://github.com/Patr1ckTS/SET16_ProsjektEksamen.git

### 1. Klone og installer
Bruk terminalen eller Git GUI-klienter som GitHub Desktop eller GitKraken. I GUI-klient avhenger det av hvilken klient man bruker. Vanligvis er det en "Clone Repository"-knapp hvor man limer inn URLen over.

I terminalen:
```bash
git clone https://github.com/Patr1ckTS/SET16_ProsjektEksamen.git
cd SET16_ProsjektEksamen/develop
```

### 2. Konfigurer IDE
**IntelliJ IDEA:** File -> Project Structure -> Project SDK -> JDK 21  
**VS Code:** Installer "Extension Pack for Java" og velg JDK 21

### 3. Database
Informasjon om databasekredentialer finnes også i `develop/app/src/main/java/org/develop/web/Main.java`. Utelatt fra dette dokumentet av sikkerhetsmessige årsaker.

**Test forbindelse:**
```bash
mysql -h itstud.hiof.no -u gruppe16 -p
```

## 4. Start Applikasjonen
Applikasjonen startes ved å kjøre Main-klassen i `develop/app/src/main/java/org/develop/web/Main.java`.

**URL:** http://localhost:7000

---

## Feilsøking
| Problem                    | Løsning                                  |
| -------------------------- | ---------------------------------------- |
| BUILD FAILURE              | Verifiser Java 21: `java -version`       |
| Databaseforbindelse feiler | Sjekk VPN eller kredentialer i Main.java |
| Port 7000 i bruk           | Endre port i Main.java                   |
| CSS laster ikke            | Kjør `mvn clean` og bygg på nytt         |

---

## Prosjektstruktur
```
develop/
├── core/
│   ├── routesearch/    # Rutesøk-logikk
│   └── database/       # Database-adapters
├── adapters/           # API-integrasjoner
└── app/
    └── src/main/
        ├── java/       # Orkestrering av logikk & oppstart
        └── resources/
            ├── templates/  # HTML
            └── static/     # CSS, bilder
```
