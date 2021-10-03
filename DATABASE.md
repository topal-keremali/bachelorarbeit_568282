# Datenbank zur Arbeitszeiterfassung
## Daten
Die [Daten](/resources/arbeitszeiterfassung_table_user.csv) aus der Testphase darf ich mit Bestätigung der [Nutzer](/resources/arbeitszeiterfassung_table_shift.csv) hier veröffentlichen. Die Daten können in der Datenbank importiert werden und zum Testen genutzt werden.
Folgende Nutzer mit ihren Passwörtern sind verfügbar:

| Nutzername  	| Name        	| Passwort 	| Role         	|
|-------------	|-------------	|----------	|--------------	|
| admin       	| Admin       	| 12345678 	| Admin        	|
| arbeitgeber 	| Arbeitgeber 	| 12345678 	| Arbeitgeber  	|
| testperson1 	| Testperson1 	| 12345678 	| Arbeitnehmer 	|
| testperson2 	| Testperson2 	| 12345678 	| Arbeitnehmer 	|
| testperson3 	| Testperson3 	| 12345678 	| Arbeitnehmer 	|
| testperson4 	| Testperson4 	| 12345678 	| Arbeitnehmer 	|
| testperson5 	| Testperson5 	| 12345678 	| Arbeitnehmer 	|

## Struktur
Die Struktur der Datenbank "arbeitszeiterfassung" sollte folgend aufgebaut sein um keine weiteren Änderungen im Backend vorzunehmen:
### Tabelle user
| id PK | name        | role | username    | password     |
|-----|-------------|------|-------------|--------------|
| INT (auto increment) | varchar(40) | INT  | varchar(40) | varchar(160) |

### Tabelle shift
| begin       | end         | worktime    | uid FK | id  |
|-------------|-------------|-------------|-----|-----|
| varchar(40) | varchar(40) | varchar(40) | INT | INT (auto increment) |
