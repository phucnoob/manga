# Manga Reader

Manga Reader is a backend-service for a reader website.

## Todo List

- Add comments to system
- Add user password forget and mail verify. ok
- Document API. ok
- Write test to look cools. ok
- Write front-end
- Spring security formLogin?
- Optimize JPA query, index...
- Write controller test (too lazy)

## Functionalities

- User register, login, profile
- CRUD operation on manga, chapter
- File uploading
- Fuzzy Search Manga

## Technologies

- Srping boot
- JPA/Hibernate
- Hibernate Search
- Spring Security with JWT
- Google Drive API

## Build Guide

### H2 database
This is a maven project, just clone this repo and run the app

```bash
git clone -b dev https://github.com/phucnoob/manga-be.git

cd manga-be/
./mvnw spring-boot:run # In-memory database, no drive api in  default profile
```
### Mysql database, debug mode
### __Important__:

 The database and Drive API related is private, if you need them contact <a href="mailto:phuclaplace@gmail.com">my
email</a>

After that, you may take a look in local-application.properties and config your database, and the credential for google API.

__Example__
```yaml

spring.datasource.password=${DB_PASS:ubuntu}
spring.datasource.username=${DB_USER:root}
spring.datasource.url=jdbc:mysql://${DB_NAME:localhost}/test?createDatabaseIfNotExist=true&useSSL=TRUE&serverTimezone=UTC

# Google drive env vars
config.drive-folder=${env.DRIVE_FOLDER:dummyfolder}
config.host=${env.DOMAIN_NAME:localhost}
config.credentials=${env.CREDENTIALS:json}
```

Now run the app with local profile
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```
