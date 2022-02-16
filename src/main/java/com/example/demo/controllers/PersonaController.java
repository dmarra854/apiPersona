package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.PersonaModel;
import com.example.demo.services.PersonaService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/persona")
@Api(tags = {"Personas"})

public class PersonaController {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(PersonaController.class);

    @Autowired
    PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping()
    @ApiOperation(
            value = "Returns a list of Persons",
            notes = "Return a list of all Persons")
    public ResponseEntity<List<PersonaModel>> obtenerPersonas() {
        List<PersonaModel> personas = this.personaService.obtenerPersonas();
        if (personas.isEmpty())
            throw new NotFoundException("No se encontraron personas");
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "Creates a new Person", notes = "Create new Person")
    public ResponseEntity<?> guardarPersona( @Valid @RequestBody PersonaModel persona, Errors errors){

        if(errors.hasErrors()) {
            List<String> errorMsg = new ArrayList<String>();
            errors.getAllErrors().forEach(a -> errorMsg.add(a.getDefaultMessage()));
            return new ResponseEntity<List<String>>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        if(personaService.existsByDni(persona.getDni()))
            throw new BadRequestException("Ya existe una persona con ese DNI");

        this.personaService.guardarPersona(persona);
        return new ResponseEntity("Persona creada", HttpStatus.CREATED); //HttpStatus.OK
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(
            value = "Returns the person corresponding to the id",
            notes = "Returns HTTP 404 if the person is not found")
    public ResponseEntity<?> obtenerPersonaPorId(@PathVariable("id") Long id){

        if (!this.personaService.existsPersonaById(id))
            throw new NotFoundException("No existe la persona con id "+ id);

            PersonaModel persona = personaService.obtenerPersonaPorId(id).get();
            if (persona.equals("[]"))
                return new ResponseEntity(null, HttpStatus.NO_CONTENT);

            return new ResponseEntity(persona, HttpStatus.OK);

    }
/*
    @DeleteMapping(value = "{patientId}")
    public void deletePatientById(@PathVariable(value = "patientId") Long patientId) throws NotFoundException {
        if (patientRecordRepository.findById(patientId).isEmpty()) {
            throw new NotFoundException("Patient with ID " + patientId + " does not exist.");
        }
        patientRecordRepository.deleteById(patientId);
    }
    */
    @DeleteMapping(path = "/{id}")
    @ApiOperation(
            value = "Deletes the person corresponding to the id",
            notes = "Returns HTTP 404 if the person is not found")
    public ResponseEntity<?> eliminarPorId(@PathVariable("id") Long id)  {

        if (!this.personaService.existsPersonaById(id))
            throw new NotFoundException("No existe la persona con id "+ id);

        boolean ok = this.personaService.eliminarPersona(id);

        if (!ok) throw new BadRequestException("No se eliminó persona con id " + id);

        return new ResponseEntity("Persona eliminada", HttpStatus.OK);

    }

    @PutMapping(path = "/{id}")
    @ApiOperation(
            value = "Modifies the person corresponding to the id",
            notes = "Returns HTTP 404 if the person is not found")
    public ResponseEntity<?> modificarPersona( @PathVariable("id") Long id, @Valid @NotNull @RequestBody PersonaModel persona, Errors errors) {

        if(errors.hasErrors()) {
            List<String> errorMsg = new ArrayList<String>();
            errors.getAllErrors().forEach(a -> errorMsg.add(a.getDefaultMessage()));
            return new ResponseEntity<List<String>>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        if (!this.personaService.existsPersonaById(id))
            throw new NotFoundException("No existe la persona con id "+ id);

        if(personaService.existsByDni(persona.getDni()) && !this.personaService.findByDni(persona.getDni()).get().getId().equals(id))
            throw new BadRequestException("Ya existe otra persona con ese DNI ");

        boolean ok =  this.personaService.modificarPersona(id, persona);

        if (ok) {
            return new ResponseEntity("Persona modificada", HttpStatus.OK);
        } else {
            throw new BadRequestException("No se modificó persona con id " + id);
        }

    }

    @PatchMapping("/{id}/{dni}")
    @ApiOperation(
            value = "Modifies the document number  corresponding to the person id",
            notes = "Returns HTTP 404 if the person is not found and HTTP 400 If a person already exists with the document number")
    public ResponseEntity<?>  modificarPersonaParcial(@PathVariable Long id, @PathVariable String dni) {

        if (!this.personaService.existsPersonaById(id))
            throw new NotFoundException("No existe la persona con id " + id);

        if(personaService.existsByDni(dni))
            throw new BadRequestException("Ya existe una persona con ese DNI ");

        boolean ok = personaService.modificarPersonaParcial(id, dni);

        if (ok) {
            return new ResponseEntity("Persona modificada", HttpStatus.OK);
        } else {
            throw new BadRequestException("No se modificó persona con id " + id);
        }
    }
}