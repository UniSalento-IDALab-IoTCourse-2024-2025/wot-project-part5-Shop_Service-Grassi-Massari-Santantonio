# FastGo Shop Microservice

Questo repository contiene il microservizio backend "Shop", sviluppato in Java Spring Boot. Il servizio gestisce l'intero ciclo di vita lato ristoratore: gestione del profilo, creazione dei menu, ricezione degli ordini e aggiornamento degli stati. Si integra con l'ecosistema FastGo tramite RabbitMQ e MQTT.

## Struttura del Progetto

.
├── src/main/java/com/fastgo/shop/fastgo_shop/
│   ├── config/             # Configurazione Cloudinary, MQTT, RabbitMQ
│   ├── component/          # Listener asincroni (RabbitMQ/MQTT) e Scheduler
│   ├── restControllers/    # Endpoint API REST
│   ├── service/            # Logica di business (Menu, Ordini, Ristoranti)
│   ├── domain/             # Entità del database (JPA/Hibernate)
│   ├── repositories/       # Interfacce di accesso ai dati
│   ├── security/           # Gestione JWT e costanti di sicurezza
│   └── dto/                # Data Transfer Objects per API e messaggistica
├── src/main/resources/
│   └── application.properties # Configurazione applicativa
├── docker-compose.yml      # Orchestrazione container locale
└── build.gradle            # Gestione dipendenze Gradle

## Prerequisiti

* Java JDK 17 o superiore
* Gradle (o wrapper incluso)
* Docker (per database e broker messaggi)
* Account Cloudinary (per upload immagini)

## Configurazione

Il microservizio richiede la configurazione delle variabili d'ambiente o la modifica del file `src/main/resources/application.properties`.

Le principali configurazioni necessarie sono:

1. Database (MongoDb):

2. RabbitMQ (Messaging interno):
   spring.rabbitmq.host=localhost
   spring.rabbitmq.port=5672
   spring.rabbitmq.username=guest
   spring.rabbitmq.password=guest

3. MQTT (Comunicazione Real-time):
   mqtt.broker.url=tcp://localhost:1883
   mqtt.client.id=fastgo-shop-service

4. Cloudinary (Gestione Immagini):
   cloudinary.cloud_name=TUO_CLOUD_NAME
   cloudinary.api_key=TUA_API_KEY
   cloudinary.api_secret=TUA_API_SECRET

## Compilazione e Avvio

1. Clean e Build del progetto:
   ./gradlew clean build

2. Avvio dell'applicazione:
   ./gradlew bootRun

L'applicazione sarà accessibile tipicamente su: http://localhost:8080 (o altra porta definita nel docker compose).

## Funzionalità Principali

* Gestione Ristorante:
  * CRUD Ristoranti e Shopkeeper.

* Gestione Menu:
  * Aggiunta, modifica e rimozione piatti (MenuItem).

* Gestione Ordini:
  * Ricezione ordini tramite MQTT.
  * Pubblicazione aggiornamenti stato.

* Sincronizzazione:
  * Listener per eventi di sincronizzazione dati tra microservizi (SyncListener).

## Note su Docker

Nella root è presente un file `docker-compose.yml`. Per avviare le dipendenze infrastrutturali (DB, Broker) necessarie allo sviluppo locale:

docker-compose up -d