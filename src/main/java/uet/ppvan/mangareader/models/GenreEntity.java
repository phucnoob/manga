package uet.ppvan.mangareader.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uet.ppvan.mangareader.enums.Genre;

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
