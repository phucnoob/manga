package uet.ppvan.mangareader.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
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
    private String name;
    private String cover;
    private String description;
    private String author;
    @Column(name = "other_name")
    private String otherName;

    @Enumerated(EnumType.STRING)
    private Status status;
}
