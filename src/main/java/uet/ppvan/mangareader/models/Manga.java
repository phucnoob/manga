package uet.ppvan.mangareader.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.validator.constraints.URL;
import uet.ppvan.mangareader.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Indexed
@Entity
@Table(name = "mangas")
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @FullTextField(analyzer = "custom")
    @Column(nullable = false, length = 127)
    private String name;

    @Column(nullable = false)
    @URL
    private String cover;

    @Column(nullable = false, length = 2048)
    private String description;

    @Column(nullable = false)
    private String author = "";

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mangas_genres",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "manga_id")
    )
    private Set<GenreEntity> genres;

    @FullTextField(analyzer = "custom")
    @Column(name = "other_name")
    private String otherName;

    private Status status;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "manga", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Chapter> chapters;
}
