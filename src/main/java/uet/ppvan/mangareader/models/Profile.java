package uet.ppvan.mangareader.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@Validated
public class Profile extends BaseEntity {

    @URL
    @NotNull
    private String avatar;

    @URL(message = "cover must be a image url")
    @NotNull
    private String cover;

    private String bio;

    public Profile() {
        avatar = "";
        cover = "";
        bio = "";
    }

    public static Profile defaultProfile() {
        return new Profile();
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    @JsonIgnore
    private User user;
}
