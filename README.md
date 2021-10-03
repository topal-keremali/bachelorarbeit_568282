# Arbeitszeiterfassung Prototyp

Prototyp zur Bachelorarbeit „Analyse und Konzeption einer Zeiterfassungssoftware mit
prototypischer Umsetzung".

## Setup

### Frontend
Zur Ausführung der Webseite wird die Installation von [Node.js](https://nodejs.org/en/download/) vorausgesetzt.

1. Global  Angular CLI mittels ```npm install -g @angular/cli``` installieren.
2. Im Hauptverzeichniss der Webanwendung mittels ```npm install``` die relevanten Bibliotheken dazu laden.

### Backend
Zur Ausführung des Backends wird die Installation von [Java Development Kit 11](https://openjdk.java.net/projects/jdk/11/) und [Apache Maven](https://maven.apache.org/install.html) vorausgesetzt.

1.Im Hauptverzeichniss des Backends mittels ```mvn install``` die relevanten Bibliotheken dazu laden.

### Datenbank
Die MySQL Datenbank wurde mittels [XAMPP](https://www.apachefriends.org/de/download.html) installiert und ausgeführt. 

## Pre-Ausführung
Bevor alle Komponenten gestartet werden können müssen einige Konfigurationen vorgenommen werden.

### Backend
Im Backend befindet sich unter ```aze-be/src/main/resources/``` die Datei  ```application.properties```. In ihr müssen verschiedene Werte eingetragen werden bevor alles gestartet werden kann.
#### Testing
Zum Testen habe ich die Datenbankstruktur 1zu1 kopiert aber verschiedene Einträge benutzt, Die Konfiguration der Testdatenbank ist in der Datei ```application-test.properties```. verlegt.

```
##Einstellungen für Verbindung zur Datenbank [Standart-Werte eingetragen]
spring.datasource.url= {Adresse zur SQL-Datenbank} jdbc:mysql://localhost:3306/arbeitszeiterfassung
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name =com.mysql.jdbc.Driver
spring.jpa.hibernate.use-new-id-generator-mappings= false

##Passwort für Adminnutzer für Webanwendung der bei erster Ausführung angelegt wird. 
##Nutzername: admin
setup.admin.password=admin123

##fachliche Einstellungen für E-Mail Service
setup.gmail.email={E-Mail vom Absender}
setup.gmail.to={E-Mail vom Empfänger der Emails}
setup.gmail.password= {Passwort vom Gmail-Konto}

##Standarteinstellungen für Gmail konfiguration [Standart-Werte eingetragen]
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username={E-Mail vom Absender}
spring.mail.password={Passwort vom Gmail-Konto}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

:bangbang: Meistens muss unter den Einstellungen vom G-Mail Konto der Zugriff für nicht-vertrauenswürdige Anwendungen, hier das Backend, freigegeben werden.

### Frontend
Im Frontend muss die Adresse des Backends unter ```aze-ui/src/environments/environment.ts``` angegeben werden. Der Standartwert für eine lokale Ausführung von Spring ist ```http://localhost:8080```.

```json
export const environment = {
    production: false,
    api: 'http://localhost:8080'
};
```

## Ausführung
Zu aller erst muss der Webserver der u.a. die Datenbank enthält mittels XAMPP ausgeführt werden. Anschließend wird das Backend mittels ```mvn spring-boot:run``` ausgeführt und nach erfolgreichem Start wird die Webanwendung mittels ```ng serve``` auch zum Laufen gebracht.

## Info
Mehr Info unter den einzelnen Markdowns:
<br>
[Backend](/BACKEND.md/)
[Webanwendung](/FRONTEND.md/)
[Datenbank](/DATABASE.md/)
