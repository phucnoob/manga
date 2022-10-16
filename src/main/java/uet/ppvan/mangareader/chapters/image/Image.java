package uet.ppvan.mangareader.chapters.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import uet.ppvan.mangareader.chapters.Chapter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @URL(message = "Chapter image must be an url")
    private String uri;

    @Column(name = "chapter_order")
    private Integer chapterOrder;

    public Image(String uri) {
        this.uri = uri;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "chapter_id",
        nullable = false
    )
    @JsonIgnore
    private Chapter chapter;

    public Image() {

    }
}
