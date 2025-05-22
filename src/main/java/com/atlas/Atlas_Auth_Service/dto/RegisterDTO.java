package com.atlas.Atlas_Auth_Service.dto;

import com.atlas.Atlas_Auth_Service.model.enums.UserRoles;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterDTO(@JsonProperty("login") String login,@JsonProperty("password") String password,
                          @JsonProperty("escola") String escola,
                          @JsonProperty("role") UserRoles role) {
}
