package com.atlas.Atlas_Auth_Service.model;

import com.atlas.Atlas_Auth_Service.model.enums.UserRoles;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "administrador")
@Table(name = "administrador")
@EqualsAndHashCode(of = "id")
public class Administrador extends Users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Administrador (String login, String password, UserRoles role) {
        super(login, password, role);
    }

    public Administrador() {
        super("", "", UserRoles.ADMINISTRADOR);
    }

    @Override
    public String toString() {
        return "Administrador{}";
    }


    @Override
    public String getUsername() {
        return this.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }
}
