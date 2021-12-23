package com.example.demo.repositories;

import java.util.Optional;

import com.example.demo.models.PersonaModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends CrudRepository<PersonaModel, Long> {

    Optional<PersonaModel> findByNombre(String nombre);

    boolean existsByNombre (String nombre );

    Optional<PersonaModel> findByApellido(String apellido);

    boolean existsByApellido (String apellido );

    Optional<PersonaModel> findByDni(String dni);

    boolean existsByDni (String dni );
}
