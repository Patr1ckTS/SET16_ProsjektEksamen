#   TODO for adapter Modulen og litt for **core** modulen
Her er en liste over oppgaver som må fullføres for å ferdigstille denne modulen:

## Oppgaver for å ferdigstille adapter modulen
Formålet her er først og fremst å utfylle en heksagonal arkitektur med adaptere som kan kommunisere med eksterne systemer. Gitt portene som er definert i **core**, skal adapterne implementere logikken for å koble til og kommunisere med disse eksterne systemene.

- [X] Definere et standardisert grensesnitt for de ulike adapterne.
  - [ ] Kalenderadapter (krever først at kalenderintegrasjonen er på plass i **core**)
  - [X] Enturadapter
  - [ ] Webrouteradapter 
- [ ] Implementere adapterne basert på på den definerte logikken.
  
- [ ] Dokumentere bruken av hver adapter, inkludert konfigurasjonsdetaljer og eksempler på bruk.

- [ ] Oppdatere dokumentasjonen for hele modulen for å reflektere de nye adapterne og deres funksjonalitet. (Nytt klasse-diagram kan være lurt her med kun de portene og adapterne).

- [ ] Gjennomgå og refaktorere koden for å sikre at den følger *"Løst koblet kodeprinsippet"* 

- [X] **Om vi får tiden**: Utføre integrasjonstester for å verifisere at adapterne fungerer korrekt (`end-to-end` tester). ***Utført for Enturadapteren***

### Enturintergrasjon
- [X] Enturadapter: Fullføre integrasjonen med Entur for å hente reiseinformasjon og teste bruken i kjerne. 
  - [X] Fullføre Enturadapteren for å hente reiseinformasjon.
  - [X] DTOer for reiseinformasjon vi henter fra Entur sitt API.
  - [X] Mapper for å konvertere fra DTO til domeneobjekter. 
  - [X] Lage tester for Enturadapteren.

- [ ] Dokumentere Enturadapteren, inkludert hvordan den fungerer og hvordan den skal implementeres i fremtiden.
- [ ] Refaktorisere koden for å sikre at den følger beste praksis og er lett å vedlikeholde. (Løst koblet fra resten av systemet så mye som mulig)

### Kalenderintegrasjon
- [ ] Kalenderintegrasjon: Fullføre integrasjonen hvor vi leser fra kalender og oppretter hendelser basert på data. (Egen modul i core for kalenderintegrasjon må være på plass først)
  - [ ] Lesing av kalenderdata fra JSON (simulerer et eksternt API kall).
  - [ ] DTOer for kalenderhendelser vi henter fra deres API.
  - [ ] Mapper for å konvertere fra DTO til domeneobjekter. (reader for bruk i kjernen)
  - [ ] Bruke porten (og adapteret) for kalenderintegrasjon i kjernen for å hente og prosessere kalenderdata.
  - [ ] Lage tester for kalenderintegrasjonen. (Mocking av kalenderdata i en enhetstest er en god start)

- [ ] Dokumentere kalenderintegrasjonen, inkludert hvordan den fungerer og hvordan den skal implementeres i fremtiden.
- [ ] Refaktorisere koden for å sikre at den følger beste praksis og er lett å vedlikeholde. (Løst koblet fra resten av systemet så mye som mulig)

