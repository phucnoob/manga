package uet.ppvan.mangareader.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "images")
public class ChapterImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uri;
    private String alt;

    @Column(name = "chapter_order")
    private Integer chapterOrder;

    public ChapterImage(String uri, String alt) {
        this.uri = uri;
        this.alt = alt;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "chapter_id",
        nullable = false
    )
    @JsonIgnore
    private Chapter chapter;

    public ChapterImage() {

    }
}
