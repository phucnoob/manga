package uet.ppvan.mangareader.dtos;

import uet.ppvan.mangareader.models.Role;

/**
 * DTO object holds user authentication info.
 * Use to generate login JWT.
 * @see uet.ppvan.mangareader.security.JwtAuthentication JwtAuthentication
 * @param id
 * @param username
 * @param role
 */
public record AuthUserDetail(Integer id, String username, Role role) {

}
