# Navn og profiler

| Navn              | Profilikon                                              | Link til profil                 |
| ----------------- | ------------------------------------------------------- | ------------------------------- |
| Henrik Sørensen   | ![Sørensens Pikachu](/Profiles/Henrik_S.png)            | https://github.com/Spoopy-Bot   |
| Torgrim Aune      | ![Aunes standardikon (grønn)](/Profiles/Torgrim.png)    | https://github.com/Torgria      |
| Yazan Alarid      | ![Alarids standardikon (blått)](/Profiles/Yazan.png)    | https://github.com/Yazan-Z      |
| Patrick T Skjelle | ![Skjelles standardikon (blått)](/Profiles/Patrick.png) | https://github.com/Patr1ckTS    |
| Henrik Berg       | ![Bergs standardikon (rosa)](/Profiles/Henrik_B.png)    | https://github.com/HenrikBerg99 |

---

# Oppstart av SET16 Reiseplanlegger
## Forutsetninger
- Java JDK-21 eller nyere
- Apache Maven 3.x
- MySQL klient
- Git installeret
- Tilgang til HIOF-nettverket (enten via campus eller VPN)

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
Informasjon om databasekredentialer finnes også i `develop/app/src/main/java/org/develop/web/Main.java`. 

**Test forbindelse:**
```bash
mysql -h itstud.hiof.no -u gruppe16 -p
```

####    Testbrukere 
Vi har laget 3 testbrukere for å visualisere databaserollene:
| Brukernavn/Mail   | Passord  | Rolle     |
| ----------------- | -------- | --------- |
| test@test.no      | passord  | user      |
| testadmin@test.no | passord1 | admin     |
| testdev@test.no   | passord2 | developer |

## 4. Start Applikasjonen
Applikasjonen startes ved å kjøre Main-klassen i `develop/app/src/main/java/org/develop/web/Main.java`.

**URL:** http://localhost:7000

### Mobilvisning 

Grensesnittet er utviklet etter **"Mobile First"-prinsippet** og er optimalisert for mobilskjerm. For best brukeropplevelse anbefales det å bruke nettleserens mobilvisning.

#### Aktivere mobilvisning i nettleseren:

1. **Åpne utviklerverktøy:** Trykk `F12` (eller høyreklikk → "Inspiser")
2. **Aktiver enhetsemulering:** Trykk `Ctrl + Shift + M` (Windows/Linux) eller `Cmd + Shift + M` (Mac)
3. **Velg enhet:** I verktøylinjen øverst kan du velge mellom forhåndsdefinerte enheter som:
   - iPhone SE / iPhone 12 Pro / iPhone 14 Pro Max
   - Samsung Galaxy S20 
   - iPad Mini / iPad Pro
   - Eller sett egendefinert oppløsning 

**Alternativ metode:**
- **Chrome/Edge:** F12 → Klikk på "Toggle device toolbar"-ikonet øverst til høyre
- **Firefox:** F12 → Klikk på "Responsive Design Mode"-ikonet 

> **Merk:** Applikasjonen kan vises i desktop-modus, men layout og funksjonalitet er ikke optimalisert for dette og kan gi en dårligere brukeropplevelse.

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
│   ├── routesearch/    # Rutesøklogikk
│   └── database/       # Databaselogikk (Bruker og Ticket)
├── adapters/           # API-integrasjoner
└── app/
    └── src/main/
        ├── java/       # Orkestrering av systemet & grensesnittet
        └── resources/
            ├── templates/  # HTML
            └── static/     # CSS, bilder
```
