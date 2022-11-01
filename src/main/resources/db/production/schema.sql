use manga_production;

create table if not exists chapters
(
    id           int auto_increment
        primary key,
    name         varchar(255) null,
    updated_date date         null,
    manga_id     int          null
);

create table if not exists chapter_images
(
    chapter_id int          not null,
    images     varchar(1024) null,
    constraint fk_chapter
        foreign key (chapter_id) references chapters (id)
);

create table if not exists genres
(
    id    int auto_increment
        primary key,
    genre varchar(255) null
);

create table if not exists mangas
(
    id          int auto_increment
        primary key,
    author      varchar(255)  not null,
    cover       varchar(255)  not null,
    description varchar(2048) not null,
    last_update datetime(6)   null,
    name        varchar(127)  not null,
    other_name  varchar(255)  null,
    status      varchar(255)  null
);

create table if not exists mangas_genres
(
    genre_id int not null,
    manga_id int not null,
    primary key (genre_id, manga_id),
    constraint fk_genre
        foreign key (genre_id) references mangas (id),
    constraint fk_manga
        foreign key (manga_id) references genres (id)
);
