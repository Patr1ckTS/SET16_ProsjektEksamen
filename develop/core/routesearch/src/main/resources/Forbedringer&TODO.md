# Forbedringer & TODO for denne komponenten

Dette dokumentet oppsummerer de viktigste problemene og forslag til løsninger kort og konkret. I tillegg har jeg importert TODO fra README.md.

## Hovedproblemer og løsninger
1. Tett kobling og statiske utility-klasser  
   - Problem: Vanskelig å mocke, bytte implementasjon eller teste isolert.  
   - Løsning: Innfør et service-lag (f.eks. RouteService) og injiser avhengigheter via interfaces (constructor injection).  

2. Eksponering av domeneobjekter (ingen DTO)  
   - Problem: Intern modell brukes direkte mot eksterne lag / UI.
   - Løsning: Bruk request/response DTOer og mapper for kommunikasjon mellom lag.  

3. Manglende separasjon av ansvar (Stop/Route gjør for mye)  
   - Problem: Klasser inneholder data, IO og beregningslogikk samtidig.  
   - Løsning: Del opp i rene dataklasser + repository (persistens) + calculator/service (beregninger).  

4. Manglende testdekning og vanskeligheter med mocking  
   - Problem: Vanskelig å teste isolert pga. statiske metoder og tett kobling.  
   - Løsning: Bruk interfaces og dependency injection for enklere mocking og bedre testdekning.  

## Anbefalt første steg (minimal innsats, stor gevinst)
- Lag IStopRepository og IRouteService interfaces. Implementer en enkel RouteService som bruker constructor injection.  
- Opprett TransportSokeRequestDTO og TransportResultatDTO; legg inn enkel mapper-funksjon.  
- Bytt ut en eller to statiske metoder med injiserbare tjenester og dekk med unit tester.

## Gevinster 
- Bedre testbarhet og enklere mocking  
- Klart ansvar per klasse, enklere vedlikehold og videreutvikling
- Mer robust arkitektur med løst koblede komponenter 
- Enklere å forstå og navigere i koden 
- Bedre muligheter for gjenbruk av komponenter


# TODO
## Formål
- [ ] Ferdigstille koden for søk A-B på ruter.
  - [X] Bruk av Dependency Injection
  - [ ] Bruk av DTO
  - [ ] Enhetstesting av logikken
  - [X] Refaktorer om nødvendig
  - [X] Sikre løst koblet kode
  - [X] Dokumentasjon av koden (Diagrammer)
  - [ ] Dokumentasjon av prosessen (Rapporten)

## Sikre Løst koblet kode 
**Dependency Injection:**
- [X] Bruk av interfaces
- [ ] Injiisering via konstruktør og metoder
- [X] Bruk av mockito for å teste løst koblet kode

**DTO (Data Transfer Object):**
- [ ] Bruk av DTO for å overføre data mellom lag
- [ ] Unngå å eksponere interne modeller direkte

## Enhetstesting 
- [ ] Lesing 
- [ ] Skriving

**Rutelogikk:**
- [ ] findBestTransport()
- [X] calculateTravelTime() -> RouteLogicTest
- [ ] Opprettelse av Resultat objektet

**StoppLogikk:**
- [ ] calculateTravelTime()
- [ ] calculateTransportAtStop()