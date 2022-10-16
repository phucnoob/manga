package uet.ppvan.mangareader.mangas;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import uet.ppvan.mangareader.chapters.Chapter;
import uet.ppvan.mangareader.mangas.enums.Genre;
import uet.ppvan.mangareader.mangas.enums.Status;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mangas")
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 127)
    private String name;

    @Column(nullable = false)
    @URL
    private String cover;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String author = "";

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name = "other_name")
    private String otherName;

    private Status status;

    @OneToMany(mappedBy = "manga", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Chapter> chapters;
}
