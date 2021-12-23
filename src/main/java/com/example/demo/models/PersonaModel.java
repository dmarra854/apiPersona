package com.example.demo.models;
import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.validation.annotation.Validated;
@Entity
@Table(name = "personas")
public class PersonaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @NotEmpty(message = "El nombre es obligatorio")
    @Getter @Setter
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @NotEmpty(message ="El apellido es obligatorio")
    @Getter @Setter
    private String apellido;

    @Column(unique = true, nullable = false)
    @Getter @Setter
    private String dni;

    @Getter @Setter
    private boolean esEmpleado;
}