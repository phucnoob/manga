package uet.ppvan.mangareader.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "roles")

@Getter
@Setter
public class RoleEntity extends BaseEntity {

    @Column(name = "role")
    private Role role;
}
