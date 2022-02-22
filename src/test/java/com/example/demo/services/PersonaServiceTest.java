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
import static org.mockito.Mockito.*;

class PersonaServiceTest {
    @Mock
    PersonaRepository personaRepository;

    @InjectMocks
    PersonaService personaService;

    private PersonaModel persona;

    @Test
    void obtenerPersonasTest() {

        MockitoAnnotations.initMocks(this);
		List<PersonaModel> peopleList = new ArrayList<PersonaModel>();

		PersonaModel personaUno = new PersonaModel(Long.valueOf(1), "John", "John", "9999999999", true);
		PersonaModel personaDos = new PersonaModel(Long.valueOf(2), "Alex", "kolenchiski", "9999999999", true);
		PersonaModel personaTres = new PersonaModel(Long.valueOf(3), "Steve", "Waugh", "9999999999", true);

        peopleList.add(personaUno);
        peopleList.add(personaDos);
        peopleList.add(personaTres);

		when(personaRepository.findAll()).thenReturn(peopleList);

        List<PersonaModel> persons = personaService.obtenerPersonas();

		assertEquals(peopleList.size(), persons.size());

    }

    @Test
    void guardarPersonaTest() {

        MockitoAnnotations.initMocks(this);
        PersonaModel persona = getPersona();

        when(personaRepository.save(any(PersonaModel.class))).thenReturn(persona);
        assertNotNull(personaService.guardarPersona(new PersonaModel()));
    }

    @Test
    void obtenerPersonaPorIdTest() {

        MockitoAnnotations.initMocks(this);
        Long id = Long.valueOf(99);
        PersonaModel persona = getPersona();

		when(personaRepository.findById(id)).thenReturn(Optional.ofNullable(persona));

		Optional<PersonaModel> personaObtenida = personaService.obtenerPersonaPorId(id);

		assertEquals("Diego", personaObtenida.get().getNombre());
		assertEquals("Simeone", personaObtenida.get().getApellido());
		assertEquals("1414141414", personaObtenida.get().getDni());
        assertEquals(false, personaObtenida.get().isEsEmpleado());

    }

    @Test
    public void eliminarPersonaTest() {
        MockitoAnnotations.initMocks(this);
        PersonaModel persona = getPersona();

        personaRepository.deleteById(persona.getId());
        Optional optional = personaRepository.findById(persona.getId());
        assertEquals(Optional.empty(), optional);
    }

/*
    @Test
    public void modificarPersona() {

        MockitoAnnotations.initMocks(this);
        PersonaModel persona = getPersona();

        when(personaRepository.findById(persona.getId())).thenReturn(Optional.ofNullable(persona));
        boolean b = personaService.modificarPersona(persona.getId(), persona);
        assertTrue(b);
    }
    */

        @Test
        public void modificarPersonaParcialTest() throws Exception {
            MockitoAnnotations.initMocks(this);
            String dni ="0000000014";
            PersonaModel persona = getPersona();

            when(personaRepository.findById(persona.getId())).thenReturn(Optional.ofNullable(persona));
            boolean b = personaService.modificarPersonaParcial(persona.getId(), dni);
            assertTrue(b);
        }


        private PersonaModel getPersona() {
            persona = new PersonaModel();

            persona.setId(Long.valueOf(14));
            persona.setNombre("Diego");
            persona.setApellido("Simeone");
            persona.setDni("1414141414");
            persona.setEsEmpleado(false);
            personaRepository.save(persona);

            return persona;
        }
}
