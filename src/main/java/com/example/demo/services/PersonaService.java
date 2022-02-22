package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.demo.models.PersonaModel;
import com.example.demo.repositories.PersonaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PersonaService {
    @Autowired
    PersonaRepository personaRepository;

    public List<PersonaModel> obtenerPersonas() {
        return (List<PersonaModel>) personaRepository.findAll();
    }

    public PersonaModel guardarPersona(PersonaModel persona) {
        return personaRepository.save(persona);
    }

    public Optional<PersonaModel> obtenerPersonaPorId(Long id) {

        return personaRepository.findById(id);
    }

    public boolean eliminarPersona(Long id) {
        try {
            personaRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public boolean modificarPersona(Long id, PersonaModel personaModif) {

        try {
            PersonaModel persona = personaRepository.findById(id).get();
            persona.setNombre(personaModif.getNombre());
            persona.setApellido(personaModif.getApellido());
            persona.setDni(personaModif.getDni());
            persona.setEsEmpleado(personaModif.isEsEmpleado());
            personaRepository.save(persona);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public boolean modificarPersonaParcial(Long id, String dni) {

        try {
            PersonaModel persona = personaRepository.findById(id).get();
            persona.setDni(dni);

            personaRepository.save(persona);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public boolean existsPersonaById(Long id){
        return personaRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre ){
        return personaRepository.existsByNombre(nombre);
    }

    public boolean existsByApellido(String apellido ){
        return personaRepository.existsByApellido(apellido);
    }

    public boolean existsByDni(String dni ){
        return personaRepository.existsByDni(dni);
    }

    public Optional<PersonaModel>  findByDni(String dni ){
        return personaRepository.findByDni(dni);
    }
}