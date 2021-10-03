# Backend zur Arbeitszeiterfassung

## Email Service
Bei Nutzung des Arbeitszeiterfassungssystem werden bei Ein- und Austragen Emails an die in der config hinterlegten E-Mails gesendet. Diese sehen wie folgt aus:
#### Eintragung
```
Betreff: Beginn der werktäglichen Zeit


Nachricht:
***********************************
{Name des Arbeitnehmers}
Beginn: {Uhrzeit der Eintragung}
***********************************
```
#### Austragung
```
Betreff: Ende der werktäglichen Zeit


Nachricht:
***********************************
{Name des Arbeitnehmers}
Beginn: {Uhrzeit der Eintragung}
***********************************

***********************************
{Name des Arbeitnehmers}
Ende: {Uhrzeit der Eintragung}
***********************************
```
## Genutzte Bibliotheken

#### [spring-context-support](https://github.com/spring-projects/spring-boot)
Standart-Bibliothek einer Springanwendung für z.B. DependencyInjection
#### [Junit4](https://junit.org/junit4/)
Für Tests wurde dieses Framework benutzt.
#### [Auth0](https://github.com/auth0/java-jwt)
Diese Bibliothek enthält die Java-Implementation der Json Web Tokens.
#### [mysql-connector](https://github.com/mysql/mysql-connector-j)
Diese Bibliothek stellt die JDBC-Bibliotheken zur Verfügung.
#### [mysql-connector](https://github.com/mysql/mysql-connector-j)
Diese Bibliothek stellt die JDBC-Bibliotheken zur Verfügung.
#### [modelmapper](http://modelmapper.org)
Mit Hilfe dieser Bibliothek wurden die Data Transfer-Objekte erstellt und gemappt.
#### [spring-boot-starter-mail](https://github.com/spring-projects/spring-boot)
Diese Bibliothek unterstützt den E-Mail-Versand von Java Mail und dem Spring Framework.
#### [spring-boot-starter-web](https://github.com/spring-projects/spring-boot)
Bibliothek zur Erstellung der REST-Schnittstelle
#### [spring-boot-starter-jdbc](https://github.com/spring-projects/spring-boot)
Diese Bibliothek stellt auch JDBC-Bibliotheken zur Verfügung.
#### [spring-boot-starter-data-jpa](https://github.com/spring-projects/spring-boot)
Hilft bei einer Verbindung zu relationen Datenbanken für z.B. Objekte.
#### [spring-boot-starter-security](https://github.com/spring-projects/spring-boot)
Bibliothek zu Sicherheitsmaßnahmen einer Spring-Anwendung
#### [spring-boot-starter-test](https://github.com/spring-projects/spring-boot)
Bibliothek zum Testen spring-spezifischer Implementierungen.
#### [spring-security-test](https://github.com/spring-projects/spring-boot)
Bibliothek zu Sicherheitsmaßnahmen einer Spring-Anwendung
#### [spring-boot-devtools](https://github.com/spring-projects/spring-boot)
Bibliothek für Entwicklertools zum Testen einer Spring-Anwendung.

