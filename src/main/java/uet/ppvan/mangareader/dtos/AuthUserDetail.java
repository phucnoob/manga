package uet.ppvan.mangareader.dtos;

import uet.ppvan.mangareader.models.Role;

public record AuthUserDetail(Integer id, String username, Role role) {

}
