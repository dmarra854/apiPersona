package com.example.demo.models;
import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @ApiModelProperty(notes = "Product ID", example = "1", required = true, position = 1)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "This field is required")
    @NotNull(message="This field cannot be null")
    @ApiModelProperty(notes = "Nombre", example = "Diego", required = true, position = 2)
    private String nombre;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "This field is required")
    @NotNull(message="This field cannot be null")
    @ApiModelProperty(notes = "Apellido", example = "Simeone", required = true, position = 3)
    private String apellido;

    @Column(unique = true, length = 10, nullable = false )
    @NotBlank(message = "This field is required")
    @NotNull(message="This field cannot be null")
    @ApiModelProperty(notes = "dni", example = "1414141414", required = true, position = 4)
    private String dni;

    @ApiModelProperty(notes = "isEmployed", required = true,  position = 5)
    private boolean esEmpleado;
}