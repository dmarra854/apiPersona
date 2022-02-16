package com.example.demo.services;

import com.example.demo.models.PersonaModel;
import com.example.demo.repositories.PersonaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PersonaServiceTest {
    @Mock
    PersonaRepository personaRepository;

    @InjectMocks
    PersonaService personaService;

    private PersonaModel persona;

    @Test
    void obtenerPersonasTest() {

        MockitoAnnotations.initMocks(this);

		List<PersonaModel> listaPersonas = new ArrayList<PersonaModel>();

		PersonaModel personaUno = new PersonaModel(Long.valueOf(1), "John", "John", "9999999999", true);
		PersonaModel personaDos = new PersonaModel(Long.valueOf(2), "Alex", "kolenchiski", "9999999999", true);
		PersonaModel personaTres = new PersonaModel(Long.valueOf(3), "Steve", "Waugh", "9999999999", true);

        listaPersonas.add(personaUno);
        listaPersonas.add(personaDos);
        listaPersonas.add(personaTres);

		when(personaRepository.findAll()).thenReturn(listaPersonas);

        List<PersonaModel> empList = personaService.obtenerPersonas();

		assertEquals(3, listaPersonas.size());

    }

    @Test
    void guardarPersonaTest() {
        MockitoAnnotations.initMocks(this);

        persona = new PersonaModel();
        persona.setId(Long.valueOf(99));
        persona.setNombre("Edgardo");
        persona.setApellido("Bauza");
        persona.setDni("13082014");
        persona.setEsEmpleado(true);

        when(personaRepository.save(any(PersonaModel.class))).thenReturn(persona);
        assertNotNull(personaService.guardarPersona(new PersonaModel()));
    }

    @Test
    void obtenerPersonaPorIdTest() {
        MockitoAnnotations.initMocks(this);

        Long id = Long.valueOf(99);

        persona = new PersonaModel();
        persona.setId(Long.valueOf(99));
        persona.setNombre("Lokesh");
        persona.setApellido("Gupta");
        persona.setDni("9999999999");
        persona.setEsEmpleado(false);

		when(personaRepository.findById(id)).thenReturn(Optional.ofNullable(persona));

		Optional<PersonaModel> persona = personaService.obtenerPersonaPorId(id);

		assertEquals("Lokesh", persona.get().getNombre());
		assertEquals("Gupta", persona.get().getApellido());
		assertEquals("9999999999", persona.get().getDni());
        assertEquals(false, persona.get().isEsEmpleado());

    }

    @Test
    public void eliminarPersonaTest() {
        MockitoAnnotations.initMocks(this);
        persona = new PersonaModel();

        persona.setId(Long.valueOf(99));
        persona.setNombre("Lokesh");
        persona.setApellido("Gupta");
        persona.setDni("9999999999");
        persona.setEsEmpleado(false);
        personaRepository.save(persona);

        personaRepository.deleteById(persona.getId());
        Optional optional = personaRepository.findById(persona.getId());
        assertEquals(Optional.empty(), optional);
    }

/*
    @Test
    void modificarPersona() {
    }

    @Test
    void modificarPersonaParcial() {
    }
    */
}