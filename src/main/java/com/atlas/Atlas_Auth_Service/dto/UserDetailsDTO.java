package com.atlas.Atlas_Auth_Service.dto;

import com.atlas.Atlas_Auth_Service.model.enums.UserRoles;

public record UserDetailsDTO(String login, String password, UserRoles role) {
}
