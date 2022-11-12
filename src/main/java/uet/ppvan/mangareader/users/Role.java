package uet.ppvan.mangareader.users;

import lombok.Getter;
import lombok.Setter;
import uet.ppvan.mangareader.comons.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "roles")

@Getter
@Setter
public class Role extends BaseEntity {

    @Column(name = "role")
    private String role;
}
