# sokos-bigqquery-template

# 1. Beskrivelse
Inneholder minimumsoppsett for db2 -> bigquery apper, samt eksempler på tester med bigquery remote og med DB2 testcontainer  
Oppsettet med PropertiesConfig er slik vi pleier å gjøre det i andre applikasjoner. 
Jeg har utelatt noe av domain oppsett fordi det er helt opp til deg hvordan du ønsker å modellere det, men personlig har jeg funnet følgende oppsett hjelpsomt:  
* En data class som holder på tabellnavn for DB2
* En data class som holder på tabellnavn for BigQuery
* En data class som holder på data fra DB2 (Db2MappingObjects.kt)

Se sokos-bigquery-tilbakekreving for implementasjon


# 2. Kjøring av tester
For å kunne kjøre InsertionTest (test for insertion i BigQuery) må du ha deployet appen din på NAIS (eventuelt bruk navnet til en annen BigQuery app i stedet for ditt app navn), og så kjører du scriptet [setupLocalEnvironment.sh](setupLocalEnvironment.sh)
```
chmod 755 setupLocalEnvironment.sh && ./setupLocalEnvironment.sh
```
Denne vil opprette [default.properties](defaults.properties) med alle environmentvariabler du trenger for å kjøre applikasjonen som er definert i [PropertiesConfig](src/main/kotlin/no.nav.sokos.bigquery.DINAPP/config/PropertiesConfig.kt)  
For å kunne teste lokalt må du gjøre dette slik at du får tak i GCP BigQuery ServiceUser credentials.

Du må ha Docker installert for å kunne kjøre testene som bruker testcontainere. 

# 3. Endringer du må gjøre
* Endre forekomster av "DINAPP" til ditt appnavn
* Endre databaseinformasjon i naiserator-dev.yaml
* Legg til GCP BigQuery service user secret i github (for github actions) og kall den "GOOGLE_APPLICATION_CREDENTIALS"
