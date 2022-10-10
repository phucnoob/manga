package uet.ppvan.mangareader.entities;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import uet.ppvan.mangareader.entities.Chapter;

@Entity
@Table(name = "images")
public class Image {
    @Id
    private String url;
    private String alt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "chapter_id",
        nullable = false
    )
    private Chapter chapter;
}
