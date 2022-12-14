package uet.ppvan.mangareader.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "chapters")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manga_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Manga manga;

    @Column(name = "server")
    private String server;


    //    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ElementCollection(fetch = FetchType.LAZY)
    @JoinTable(name = "chapter_images", joinColumns = @JoinColumn(name = "chapter_id"))
    private Set<String> images;
}
