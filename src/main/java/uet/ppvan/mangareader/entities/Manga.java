package uet.ppvan.mangareader.entities;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import uet.ppvan.mangareader.entities.enums.Status;

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

    @Column(nullable = false, unique = true)
    private String cover;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String author = "";
    private String genre = "";
    @Column(name = "other_name")
    private String otherName;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "manga", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Chapter> chapters;
}
