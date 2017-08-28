# Robot ![biuld](https://travis-ci.org/agawda/HAL9000.svg?branch=master)
Robot collects book information for gratis and promo books.

### Authors

 - Michał
 - Ania
 - French Ninja Architect: Arek 
 
## Running

- goto target directory 
- run from console: 

```sh
$ mvn clean install
$ java -jar robot-0.0.1-SNAPSHOT.jar
```

Site (TODO)

``mvn site``

Jacoco (TODO)

``mvn jacoco:report``

Sonar (TODO)

``mvn sonar:sonar``

Checkstyle (TODO)

``mvn checkstyle:checkstyle``

## What project does:
- Robot collects book information for gratis and promo books
- Book information collected at minimum is: title, genre, author(s), promo details, price
- If book has subtitle, it's collected as well
- Web-UI
- Book information is collected from 3 DIFFERENT libraries (screen scraping or REST API)
- Robot runs once per day, results are appended
- Run results are persisted in DB
- Runs are logged along with their statistics 
- Project mantra

## Demo.md:
https://github.com/agawda/HAL9000/wiki

## Kanban tables:
https://waffle.io/agawda/HAL9000