#   TODO for adapter Modulen og litt for **core** modulen
Her er en liste over oppgaver som må fullføres for å ferdigstille denne modulen:

## Oppgaver for å ferdigstille adapter modulen
Formålet her er først og fremst å utfylle en heksagonal arkitektur med adaptere som kan kommunisere med eksterne systemer. Gitt portene som er definert i **core**, skal adapterne implementere logikken for å koble til og kommunisere med disse eksterne systemene.

- [ ] Definere et standardisert grensesnitt for de ulike adapterne.
  - [ ] Kalenderadapter (krever først at kalenderintegrasjonen er på plass i **core**)
  - [ ] Enturadapter
  - [ ] Webrouteradapter (sendingen av logikkken til webrouter)
- [ ] Implementere adapterne basert på på den definerte logikken.
  
- [ ] Dokumentere bruken av hver adapter, inkludert konfigurasjonsdetaljer og eksempler på bruk.

- [ ] Oppdatere dokumentasjonen for hele modulen for å reflektere de nye adapterne og deres funksjonalitet. (Nytt klasse-diagram kan være lurt her med kun de portene og adapterne).

- [ ] Gjennomgå og refaktorere koden for å sikre at den følger *"Løst koblet kodeprinsippet"* 

- [ ] **Om vi får tiden**: Utføre integrasjonstester for å verifisere at adapterne fungerer korrekt (`end-to-end` tester). (Holder nok med mocking og enhetstester i første omgang).

##  Oppgaver for systemet som helhet
Disse oppgavene er mer generelle og gjelder hele systemet, inkludert alle modulene da tanken er å sikre at alt fungerer som forventet når det hele settes sammen.

- [ ] Klare å kjøre systemet på tvers av moduler (Riktig bruk av porter og DTOer mellom modulene).
  - [ ] Adaptere og porter kan ventes med her for å sikre at kjernen fungerer først.


### Kalenderintegrasjon
- [ ] Kalenderintegrasjon: Fullføre integrasjonen hvor vi leser fra kalender og oppretter hendelser basert på data. (Egen modul i core for kalenderintegrasjon må være på plass først)
  - [ ] Hendelsene kan først lagres som objekter lokalt / eller direkte brukes i logikken uten lagring. 
  - [ ] DTOer for kalenderhendelser vi henter fra deres API.
  - [ ] Mapper for å konvertere fra DTO til domeneobjekter. (reader for bruk i kjernen)
  - [ ] Bruke porten (og adapteret) for kalenderintegrasjon i kjernen for å hente og prosessere kalenderdata.
  - [ ] Lage tester for kalenderintegrasjonen. (Mocking av kalenderdata i en enhetstest er en god start)

- [ ] Om vi lagrer hendelser lokalt først:
  - [ ] Lagringslogikk for kalenderhendelser i databasen.
  - [ ] Repository for å håndtere lagring og henting av kalenderhendelser.

- [ ] Dokumentere kalenderintegrasjonen, inkludert hvordan den fungerer og hvordan den skal implementeres i fremtiden.
- [ ] Refaktorisere koden for å sikre at den følger beste praksis og er lett å vedlikeholde. (Løst koblet fra resten av systemet så mye som mulig)

## Arbeidet utført så langt
### Adapter modulen
Utarbeidet grunnleggende struktur for adapter modulen med noen foreløpige klasser og grensesnitt. Følgende er gjort så langt:

- **CalenderAdapter** er kun en skisse og ikke implementert.

- **EnturRepositoryAdapter** er mest utfylt, lesing fra filer metodene er lagt inn her for `getStops` og `getRoute`.
  
- **WebRouteAdapter** er kun en skisse, men har som formål å sende `RouteDTO` og `ResultatDTO` til webrouter tjenesten.

- **DatabaseUserRepository** var tidligere lagt i core (i egen adaptermappe inennfor databasemodulen). Dette er flyttet hit for å følge arkitekturen bedre.
- **SQLDatabaseConnection** er også flyttet hit fra core for samme grunn. (Tidligere het den bare DatabaseConnection).


### Databasemodulen
- Som nevnt er noen av databaselogikken flyttet til adapter modulen for å følge arkitekturen bedre.
- Noen småendringer måtte også gjøres i logikken for å tilpasse seg flyttingen.
- Modulen var ikke korrekt satt opp og ble ikke registret som en modul i prosjektet tidligere. Dette er nå fikset.


### Webmodulen
- Ingen endringer er gjort i logikken
- Måtte "klone" eksisterende templates og components for å få satt opp en fungerende modulstruktur i prosjektet.

### Routesearchmodulen
- Uarbeidet porter for de nevnte adapterne i adapter modulen:
  - CalendarRepository
  - EnturRepository
  - WebRouteRepository

Formålet er at kjernen skal bruke disse portene for å kommunisere med de eksterne systemene via adapterne i adapter modulen og DTOer for datautveksling.