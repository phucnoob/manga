package uet.ppvan.mangareader.chapters.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
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
