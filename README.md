<p align="center">
  <img src="./Readme-Images/logo.png">
</p>

# Final Project - Alba Mesa

## Table of Contents

1. [**About**](#About)
2. [**Getting started**](#Getting-started)
3. [**API Routes**](#API-routes)
4. [**Images**](#Images)
5. [**Next Steps**](#Next-Steps)

<a name="About"></a>
## About

At My Diving Trip our aim is to provide a simple and clean web where you can store your dives in your personar diving log book, so you don't have to 
worry about finding your diving log book again.

My Diving Trip allows you to:

* Register / Login as a diver

* Add / Modify dives in your diving log book

* Validate dives sending an email to the diving club

* Register diving titulations in your passport

* Modify your user information

* Lookup the contact information of the registered clubs

The project has been created with a microservices structure to aid with future scalability and the addition of new features. 
The back-end microservices have been deployed using Heroku while the front-end has been deployed using firebase.
Our microservices include:

- Edge-Service -> https://my-diving-trip-edge.herokuapp.com
- Diving-Book-Service -> https://diving-book.herokuapp.com
- Diving-Passport-Service -> https://diving-passport.herokuapp.com
- Diving-Clubs-Service -> https://diving-clubs.herokuapp.com

You can find the front-end deployed in -> https://my-diving-trip.web.app/, here you can use the api without runing anything else.

You can find the last version of the code in the following URL -> https://github.com/albamesgar/my-diving-trip.git

<a name="Getting-started"></a>
## Getting started

### Running My Diving Trip
<br>

1. Download ZIP file / Clone the project:
```
https://github.com/albamesgar/my-diving-trip.git
```

2. Create a mySQL database with the following schemas at the standard localhost port 3306

* "users"

* "diving_log"

* "passport"

* "clubs"

Run the following code in the "users" schema to create the principal roles:

```
CREATE TABLE role (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

INSERT INTO role (name) VALUES
("ADMIN"),
("DIVER"),
("CLUB");
```

Run the following code in the "clubs" schema to create some clubs:

```
CREATE TABLE club(
	id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    street VARCHAR(255),
    home_number INT,
    city VARCHAR(255),
    postal_code INT,
    country VARCHAR(255),
    contact_phone BIGINT,
    email VARCHAR(255),
    rating DOUBLE,
    PRIMARY KEY (id)
);

INSERT INTO club (name, street, home_number, city, postal_code, country, contact_phone,
email, rating) VALUES 
("Cabo Ti??oso", "street1", 1, "city1", 3030, "Spain", 606060, "club1@gmail.com", 4.5),
("BlauMar", "street2", 1, "city2", 3030, "Spain", 606060, "club2@gmail.com",3),
("Hesperides", "street3", 1, "city3", 3030, "Spain", 606060, "club3@gmail.com",2.2);
```

To run the back-end tests create the following schemas:

* "users_test"

* "diving_log_test"

* "passport_test"

* "clubs_test"

You can change the username and the password of the database specified in each application.properties file in case yours are different to the current ones.

3. Set Up & Run The Microservices - (Best Running Order - > Eureka, Edge, Diving Book, Diving Passport, Diving Clubs). Enter the following into terminal:
```
mvn spring-boot:run
```

4. Load Project in Visual Studio. Enter the following into terminal:

  ```
  npm install 
  ng serve 
  ```

5. Enjoy at http://localhost:4200/

<a name="API-routes"></a>
## API Routes

### Related with users

| Endpoint | Method | Description | Path Params
| :--- | :--- | :--- | :--- 
| /users/{id} | `GET` | Get user by id | `id=[Long]`
| /users/usernames | `GET` | Get the usernames of all users | None
| /login | `GET` | Get the user | `user=[User]`
| /users | `POST` | Create a new user | `userDTO=[UserDTO]`
| /users/{userId} | `PUT` | Modify the user information | `id=[Long], userDTO=[UserDTO]`
| /users/{id} | `DELETE` | Delete user | `id=[Long]`

### Related with dive book

| Endpoint | Method | Description | Path Params
| :--- | :--- | :--- | :--- 
| /dive-books/{userId} | `GET` | Get user dive book | `userId=[Long]`
| /add-dive/dive-book/{userId} | `POST` | Add dive to dive book | `userId=[Long], diveDTO=[DiveDTO]`
| /modify-dive/{diveId} | `PUT` | Modify dive of dive book | `diveId=[Long], diveDTO=[DiveDTO]`
| /dive/{id}/validate | `GET` | Validate dive | `id=[Long]`
| /dive/{id}/cancel | `GET` | Cancel dive validation | `id=[Long]`

### Related with passport

| Endpoint | Method | Description | Path Params
| :--- | :--- | :--- | :--- 
| /passports/{userId} | `GET` | Get user passport | `userId=[Long]`
| /add-titulation/passport/{userId} | `POST` | Add titulation to passport | `userId=[Long], titulationDTO=[TitulationDTO]`
| /modify-titulation/{titulationId} | `PUT` | Modify titulation of passport | `titulationId=[Long], titulationDTO=[TitulationDTO]`

### Related with clubs

| Endpoint | Method | Description | Path Params
| :--- | :--- | :--- | :--- 
| /clubs | `GET` | Get all clubs | None
| /clubs/{id} | `GET` | Get club by id | `id=[Long]`
| /clubs | `POST` | Create club | `club=[Club]`
| /clubs/{id} | `PUT` | Modify club | `id=[Long], club=[Club]`
| /clubs/{id} | `DELETE` | Delete club | `id=[Long]`

<a name="Images"></a>
## Images

### Class Diagram
<img src="./Readme-Images/Class Diagram.png">

### Use Case Diagram
<img src="./Readme-Images/Use Case Diagram.png">

### Microservices Diagram
<img src="./Readme-Images/Microservices Diagram.png">

<a name="Next-Steps"></a>
## Next Steps
Finally, I would like to point out some of the future steps of this project:

* Create a clubs view in the frontend to register new clubs and modify their contact information

* Implement the clubs rating as a mean of the ratings of the dives done with each club

* Create an admin view in the frontend

* Optimize the frontend

* Optimize the security of the full API

* Contact diving clubs to poblate de Clubs Data Base
