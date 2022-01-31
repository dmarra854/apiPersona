package com.example.demo.controllers;

import java.util.List;

import com.example.demo.models.PersonaModel;
import com.example.demo.services.PersonaService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/persona")
@Api(tags = {"Personas"})
public class PersonaController {

    @Autowired
    PersonaService personaService;

    @GetMapping()
    public ResponseEntity<List<PersonaModel>>obtenerPersonas() {

        List<PersonaModel> personas = this.personaService.obtenerPersonas();
        return new ResponseEntity<List<PersonaModel>>(personas, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> guardarPersona(@RequestBody PersonaModel persona){

        if(StringUtils.isBlank(persona.getNombre()))
            return new ResponseEntity("El nombre es obligatorio", HttpStatus.BAD_REQUEST);

        if(StringUtils.isBlank(persona.getApellido()))
            return new ResponseEntity("El Apellido es obligatorio", HttpStatus.BAD_REQUEST);

        if(StringUtils.isBlank(persona.getDni()))
            return new ResponseEntity("El DNI es obligatorio", HttpStatus.BAD_REQUEST);

        //Se comenta validación por asumir que puede haber una persona con el mismo nombre con distinto dni
/*
        if(personaService.existsByNombre (persona.getNombre()))
            return new ResponseEntity("Ya existe una persona con ese nombre", HttpStatus.BAD_REQUEST);

        if(personaService.existsByApellido(persona.getApellido()))
            return new ResponseEntity("Ya existe una persona con ese Apellido", HttpStatus.BAD_REQUEST);
*/

        if(personaService.existsByDni(persona.getDni()))
            return new ResponseEntity("Ya existe una persona con ese DNI ", HttpStatus.BAD_REQUEST);

        this.personaService.guardarPersona(persona);
        return new ResponseEntity("Persona creada", HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PersonaModel> obtenerPersonaPorId(@PathVariable("id") Long id){

        if (!this.personaService.existsPersonaById(id))
            return new ResponseEntity("No existe la persona con id "+ id, HttpStatus.NOT_FOUND);

        PersonaModel persona = personaService.obtenerPersonaPorId(id).get();
        return new ResponseEntity(persona, HttpStatus.OK);
    }

   @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable("id") Long id){

        if (!this.personaService.existsPersonaById(id))
            return new ResponseEntity("No existe la persona con id "+ id, HttpStatus.NOT_FOUND);

        boolean ok = this.personaService.eliminarPersona(id);

        if (ok) {
            return new ResponseEntity("Persona eliminada", HttpStatus.OK);
        } else {
            return new ResponseEntity("No se eliminó persona con id " + id, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> modificarPersona( @PathVariable("id") Long id, @Valid @NotNull @RequestBody PersonaModel persona) {

        if (!this.personaService.existsPersonaById(id))
            return new ResponseEntity("No existe la persona con id "+ id, HttpStatus.NOT_FOUND);

        if(StringUtils.isBlank(persona.getNombre()))
            return new ResponseEntity("El nombre es obligatorio", HttpStatus.BAD_REQUEST);

        if(StringUtils.isBlank(persona.getApellido()))
            return new ResponseEntity("El Apellido es obligatorio", HttpStatus.BAD_REQUEST);

        if(StringUtils.isBlank(persona.getDni()))
            return new ResponseEntity("El DNI es obligatorio", HttpStatus.BAD_REQUEST);

        if(personaService.existsByDni(persona.getDni()) && !this.personaService.findByDni(persona.getDni()).get().getId().equals(id))
            return new ResponseEntity("Ya existe otra persona con ese DNI ", HttpStatus.BAD_REQUEST);

        boolean ok =  this.personaService.modificarPersona(id, persona);

        if (ok) {
            return new ResponseEntity("Persona modificada", HttpStatus.OK);
        } else {
            return new ResponseEntity("No se modificó persona con id " + id, HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/{id}/{dni}")
    public ResponseEntity<?>  modificarPersonaParcial(@PathVariable Long id, @PathVariable String dni) {

        if (!this.personaService.existsPersonaById(id))
            return new ResponseEntity("No existe la persona con id " + id, HttpStatus.NOT_FOUND);

        if(personaService.existsByDni(dni))
            return new ResponseEntity("Ya existe una persona con ese DNI ", HttpStatus.BAD_REQUEST);

        boolean ok = personaService.modificarPersonaParcial(id, dni);

        if (ok) {
            return new ResponseEntity("Persona modificada", HttpStatus.OK);
        } else {
            return new ResponseEntity("No se modificó persona con id " + id, HttpStatus.BAD_REQUEST);
        }
    }


}