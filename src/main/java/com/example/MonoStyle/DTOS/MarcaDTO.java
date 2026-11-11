package com.example.MonoStyle.DTOS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarcaDTO {
    @JsonIgnore
    private Long idMarca;

    @NotBlank(message = "El nombre de la marca es obligatorio")
    private String nombre;
}