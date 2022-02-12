package com.example.demo.models;
import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "personas")
@Data
public class PersonaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @NotNull(message="This field cannot be null")
    private String nombre;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "El apellido es obligatorio")
    @NotNull(message="This field cannot be null")
    private String apellido;

    @Column(unique = true, length = 10, nullable = false )
    @NotBlank(message = "El dni es obligatorio")
    @NotNull(message="This field cannot be null")
    private String dni;

    private boolean esEmpleado;
}