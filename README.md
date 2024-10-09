# Blackjack API

## Descripció

En aquest exercici pràctic, es crearà una API en Java amb Spring Boot per a un joc de Blackjack. L'API estarà dissenyada per connectar-se i gestionar informació en dues bases de dades diferents: MongoDB i MySQL. El joc de Blackjack s'implementarà amb totes les funcionalitats necessàries per jugar, com la gestió de jugadors, mans de cartes i regles del joc.


### Implementació Bàsica

- **Desenvolupament d'una aplicació reactiva amb Spring WebFlux:** L'aplicació és purament reactiva, incloent l'elecció d'un enfocament reactiu, la configuració de MongoDB reactiva, i la implementació de controladors i serveis reactius.

### Gestió d'Excepcions Global

- **GlobalExceptionHandler:** Implementa un manejador d'excepcions per gestionar les excepcions a l'aplicació de manera global.

### Configuració de Bases de Dades

- **Dues bases de dades:** Configura l'aplicació per utilitzar dues bases de dades: MySQL i MongoDB.

### Proves de Controlador i Servei

- **Proves unitàries:** Implementa proves unitàries per almenys un controlador i un servei utilitzant JUnit i Mockito.

### Documentació amb Swagger

- **Swagger:** Utilitza Swagger per generar documentació automàtica de l'API de l'aplicació.
- http://localhost:8080/swagger-ui.html

## Passos a seguir

1. **Disseny de l'API:** Defineix els diferents endpoints necessaris per gestionar un joc de Blackjack, incloent la creació de partides i la realització de jugades.
2. **Connexió a les Bases de Dades:** Configura la connexió a MongoDB i MySQL. Crea les entitats necessàries en Java per representar les dades del joc.
3. **Proves Unitàries:** Escriu proves unitàries per a cadascun dels endpoints i funcions principals de l'API utilitzant JUnit i Mockito. Verifica que l'API funcioni correctament i que les operacions a les bases de dades es realitzin de manera esperada. Testa com a mínim un servei i un controlador.

## Endpoints per al joc

### 1. Crear partida

- **Mètode:** `POST`
- **Descripció:** Crea una nova partida de Blackjack.
- **Endpoint:** `/game/new`
- **Cos de la sol·licitud (body):** Nou nom del jugador.
- **Paràmetres d'entrada:** Cap
- **Resposta exitosa:** Codi `201 Created` amb informació sobre la partida creada.

### 2. Obtenir detalls de partida

- **Mètode:** `GET`
- **Descripció:** Obté els detalls d'una partida específica de Blackjack.
- **Endpoint:** `/game/{id}`
- **Paràmetres d'entrada:** Identificador únic de la partida (`id`)
- **Resposta exitosa:** Codi `200 OK` amb informació detallada sobre la partida.

### 3. Realitzar jugada

- **Mètode:** `POST`
- **Descripció:** Realitza una jugada en una partida de Blackjack existent.
- **Endpoint:** `/game/{id}/play`
- **Paràmetres d'entrada:** Identificador únic de la partida (`id`), dades de la jugada (per exemple, tipus de jugada i quantitat apostada).
- **Resposta exitosa:** Codi `200 OK` amb el resultat de la jugada i l'estat actual de la partida.

### 4. Eliminar partida

- **Mètode:** `DELETE`
- **Descripció:** Elimina una partida de Blackjack existent.
- **Endpoint:** `/game/{id}/delete`
- **Paràmetres d'entrada:** Identificador únic de la partida (`id`).
- **Resposta exitosa:** Codi `204 No Content` si la partida s'elimina correctament.

### 5. Obtenir rànquing de jugadors

- **Mètode:** `GET`
- **Descripció:** Obtén el rànquing dels jugadors basat en el seu rendiment a les partides de Blackjack.
- **Endpoint:** `/ranking`
- **Paràmetres d'entrada:** Cap
- **Resposta exitosa:** Codi `200 OK` amb la llista de jugadors ordenada per la seva posició en el rànquing i la seva puntuació.

### 6. Canviar nom del jugador

- **Mètode:** `PUT`
- **Descripció:** Canvia el nom d'un jugador en una partida de Blackjack existent.
- **Endpoint:** `/player/{playerId}`
- **Cos de la sol·licitud (body):** Nou nom del jugador.
- **Paràmetres d'entrada:** Identificador únic del jugador (`playerId`).
- **Resposta exitosa:** Codi `200 OK` amb informació actualitzada del jugador.

## Requisits

- Java 11 o superior
- Spring Boot 2.x
- MongoDB
- MySQL
- Swagger per a documentació de l'API

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.4/maven-plugin/build-image.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#web.reactive)
* [Spring Data Reactive MongoDB](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#data.nosql.mongodb)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#data.sql.r2dbc)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Accessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Additional Links
These additional references should also help you:

* [R2DBC Homepage](https://r2dbc.io)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.




