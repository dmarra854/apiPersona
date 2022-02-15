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
    void obtenerPersonas() {

        MockitoAnnotations.initMocks(this);

		List<PersonaModel> list = new ArrayList<PersonaModel>();

		PersonaModel personaUno = new PersonaModel(Long.valueOf(1), "John", "John", "9999999999", true);
		PersonaModel personaDos = new PersonaModel(Long.valueOf(2), "Alex", "kolenchiski", "9999999999", true);
		PersonaModel personaTres = new PersonaModel(Long.valueOf(3), "Steve", "Waugh", "9999999999", true);

		list.add(personaUno);
		list.add(personaUno);
		list.add(personaUno);

		when(personaRepository.findAll()).thenReturn(list);

        List<PersonaModel> empList = personaService.obtenerPersonas();

		assertEquals(3, empList.size());

    }

    @Test
    void guardarPersona() {
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
    void obtenerPersonaPorId() {
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
/*
    @Test
    void eliminarPersona() {
    }

    @Test
    void modificarPersona() {
    }

    @Test
    void modificarPersonaParcial() {
    }

    @Test
    void existsPersonaById() {
    }

    @Test
    void existsByNombre() {
    }

    @Test
    void existsByApellido() {
    }

    @Test
    void existsByDni() {
    }

    @Test
    void findByDni() {
    }
    */
}