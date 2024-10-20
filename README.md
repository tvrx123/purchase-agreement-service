# Aplikacija za kupoprodajne ugovore

## 1. API dokumentacija

- API dokuemntacija implementirana je pomoću alata **Swagger**
- Moguće joj je pristupiti na resursu **/swagger-ui/index.html**deploy-anog rješenja

## 2. Pokretanje unutar **Docker**-a
- Upute podrazumijevaju instalirani **Docker** i **Maven**
- 1. Klonirati **Git** repozitorij sa rješenjem
- 2. Pozicionirati se u direktorij rješenja i izršiti naredbu **mvn clean package** koja pakira rješenje u **jar** datoteku
- 3. Izgraditi *docker image* pomoću naredbe **docker build . --tag=purchase-agreement-service:latest**
- 4. Pokrenuti *image* pomoću naredbe  **docker run -e SPRING_DATASOURCE_URL='<datasource_url>' -e SPRING_DATASOURCE_USERNAME='<username>' -e SPRING_DATASOURCE_PASSWORD='<password>!' -p :8104 purchase-agreement-service:latest**
- u naredbu za pokretanje *image*-a potrebno je umetnuti vlastite *environment* varijable za bazu podataka (*datasauce_url*,*username*, i *password*) te specificirati *port* na kojem će rješenje biti *expose*-ano
