package uet.ppvan.mangareader.mangas.genres;

import lombok.Getter;
import lombok.Setter;
import uet.ppvan.mangareader.mangas.Manga;
import uet.ppvan.mangareader.mangas.enums.Genre;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "genres")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Genre genre;

    @ManyToMany(mappedBy = "genres")
    private Set<Manga> mangas;
}
