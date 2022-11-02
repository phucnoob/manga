create database if not exists mangadb;
use mangadb; 

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
    images     varchar(255) null,
    constraint fk_chapter
        foreign key (chapter_id) references chapters (id)
);

create index fk_manga
    on chapters (manga_id);

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





INSERT INTO mangadb.genres (id, genre) VALUES (1, '16+');
INSERT INTO mangadb.genres (id, genre) VALUES (2, '18+');
INSERT INTO mangadb.genres (id, genre) VALUES (3, 'Action');
INSERT INTO mangadb.genres (id, genre) VALUES (4, 'Adult');
INSERT INTO mangadb.genres (id, genre) VALUES (5, 'Adventure');
INSERT INTO mangadb.genres (id, genre) VALUES (6, 'Anime');
INSERT INTO mangadb.genres (id, genre) VALUES (7, 'Bạo lực - Máu me');
INSERT INTO mangadb.genres (id, genre) VALUES (8, 'Comedy');
INSERT INTO mangadb.genres (id, genre) VALUES (9, 'Comic');
INSERT INTO mangadb.genres (id, genre) VALUES (10, 'Doujinshi');
INSERT INTO mangadb.genres (id, genre) VALUES (11, 'Drama');
INSERT INTO mangadb.genres (id, genre) VALUES (12, 'Ecchi');
INSERT INTO mangadb.genres (id, genre) VALUES (13, 'Event BT');
INSERT INTO mangadb.genres (id, genre) VALUES (14, 'Fantasy');
INSERT INTO mangadb.genres (id, genre) VALUES (15, 'Full màu');
INSERT INTO mangadb.genres (id, genre) VALUES (16, 'Game');
INSERT INTO mangadb.genres (id, genre) VALUES (17, 'Gender Bender');
INSERT INTO mangadb.genres (id, genre) VALUES (18, 'Harem');
INSERT INTO mangadb.genres (id, genre) VALUES (19, 'Historical');
INSERT INTO mangadb.genres (id, genre) VALUES (20, 'Horror');
INSERT INTO mangadb.genres (id, genre) VALUES (21, 'Isekai');
INSERT INTO mangadb.genres (id, genre) VALUES (22, 'Josei');
INSERT INTO mangadb.genres (id, genre) VALUES (23, 'Live action');
INSERT INTO mangadb.genres (id, genre) VALUES (24, 'Magic');
INSERT INTO mangadb.genres (id, genre) VALUES (25, 'manga');
INSERT INTO mangadb.genres (id, genre) VALUES (26, 'Manhua');
INSERT INTO mangadb.genres (id, genre) VALUES (27, 'Manhwa');
INSERT INTO mangadb.genres (id, genre) VALUES (28, 'Martial Arts');
INSERT INTO mangadb.genres (id, genre) VALUES (29, 'Mature');
INSERT INTO mangadb.genres (id, genre) VALUES (30, 'Mecha');
INSERT INTO mangadb.genres (id, genre) VALUES (31, 'Mystery');
INSERT INTO mangadb.genres (id, genre) VALUES (32, 'Nấu Ăn');
INSERT INTO mangadb.genres (id, genre) VALUES (33, 'Ngôn Tình');
INSERT INTO mangadb.genres (id, genre) VALUES (34, 'NTR');
INSERT INTO mangadb.genres (id, genre) VALUES (35, 'One shot');
INSERT INTO mangadb.genres (id, genre) VALUES (36, 'Psychological');
INSERT INTO mangadb.genres (id, genre) VALUES (37, 'Romance');
INSERT INTO mangadb.genres (id, genre) VALUES (38, 'School Life');
INSERT INTO mangadb.genres (id, genre) VALUES (39, 'Sci-fi');
INSERT INTO mangadb.genres (id, genre) VALUES (40, 'Seinen');
INSERT INTO mangadb.genres (id, genre) VALUES (41, 'Shoujo');
INSERT INTO mangadb.genres (id, genre) VALUES (42, 'Shoujo Ai');
INSERT INTO mangadb.genres (id, genre) VALUES (43, 'Shounen');
INSERT INTO mangadb.genres (id, genre) VALUES (44, 'Shounen Ai');
INSERT INTO mangadb.genres (id, genre) VALUES (45, 'Slice of life');
INSERT INTO mangadb.genres (id, genre) VALUES (46, 'Smut');
INSERT INTO mangadb.genres (id, genre) VALUES (47, 'Soft Yaoi');
INSERT INTO mangadb.genres (id, genre) VALUES (48, 'Soft Yuri');
INSERT INTO mangadb.genres (id, genre) VALUES (49, 'Sports');
INSERT INTO mangadb.genres (id, genre) VALUES (50, 'Supernatural');
INSERT INTO mangadb.genres (id, genre) VALUES (51, 'Tạp chí truyện tranh');
INSERT INTO mangadb.genres (id, genre) VALUES (52, 'Tragedy');
INSERT INTO mangadb.genres (id, genre) VALUES (53, 'Trap (Crossdressing)');
INSERT INTO mangadb.genres (id, genre) VALUES (54, 'Trinh Thám');
INSERT INTO mangadb.genres (id, genre) VALUES (55, 'Truyện scan');
INSERT INTO mangadb.genres (id, genre) VALUES (56, 'Tu chân - tu tiên');
INSERT INTO mangadb.genres (id, genre) VALUES (57, 'Video Clip');
INSERT INTO mangadb.genres (id, genre) VALUES (58, 'VnComic');
INSERT INTO mangadb.genres (id, genre) VALUES (59, 'Webtoon');
INSERT INTO mangadb.genres (id, genre) VALUES (60, 'Yuri');

