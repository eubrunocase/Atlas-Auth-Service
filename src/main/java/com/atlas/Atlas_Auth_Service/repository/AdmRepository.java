package com.atlas.Atlas_Auth_Service.repository;

import com.atlas.Atlas_Auth_Service.model.Administrador;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmRepository extends BaseRepository<Administrador> {
    Administrador findByLogin(String login);
}
