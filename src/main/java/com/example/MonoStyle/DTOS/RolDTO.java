package com.example.MonoStyle.DTOS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolDTO {
    @JsonIgnore
    private Long id;

    @NotBlank(message = "La descripción del rol es obligatoria")
    private String descripcion;
}