package uet.ppvan.mangareader.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uet.ppvan.mangareader.entities.Chapter;

@Entity
@Getter
@Setter
@Table(name = "images")
public class Image {
    @Id
    private String uri;
    private String alt;

    public Image(String uri, String alt) {
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

    public Image() {

    }
}
