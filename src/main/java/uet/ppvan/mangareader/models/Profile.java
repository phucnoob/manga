package uet.ppvan.mangareader.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profiles")
@Getter
@Setter
public class Profile extends BaseEntity {

    private String avatar;

    private String cover;

    private String bio;

    public Profile() {
        avatar = "";
        cover = "";
        bio = "";
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;
}
