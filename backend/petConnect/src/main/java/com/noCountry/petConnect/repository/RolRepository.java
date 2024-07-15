package com.noCountry.petConnect.repository;

import com.noCountry.petConnect.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository <Rol, Long> {

    Rol findByNombre(String nombre);

}
