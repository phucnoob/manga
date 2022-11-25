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

This is a maven project, just clone this repo and run the app

```bash
git clone https://github.com/phucnoob/manga-be.git

cd manga-be/
./mvnw spring-boot:run # In-memory database
```

### __Important__:

the database and Drive API related is private, if you need them contact <a href="mailto:phuclaplace@gmail.com">my
email</a>
