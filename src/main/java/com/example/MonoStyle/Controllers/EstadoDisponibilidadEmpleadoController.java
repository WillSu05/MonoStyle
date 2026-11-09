package com.example.MonoStyle.Controllers;

import com.example.MonoStyle.DTOS.EstadoDisponibilidadEmpleadoDTO;
import com.example.MonoStyle.Service.EstadoDisponibilidadEmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados-disponibilidad")
class EstadoDisponibilidadEmpleadoController {
    @Autowired
    private EstadoDisponibilidadEmpleadoService service;

    @GetMapping
    public ResponseEntity<List<EstadoDisponibilidadEmpleadoDTO>> listar() {
        return ResponseEntity.ok(service.listarEstados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDisponibilidadEmpleadoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerEstadoPorId(id));
    }

    @PostMapping
    public ResponseEntity<EstadoDisponibilidadEmpleadoDTO> crear(@Valid @RequestBody EstadoDisponibilidadEmpleadoDTO dto) {
        return new ResponseEntity<>(service.crearEstado(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDisponibilidadEmpleadoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EstadoDisponibilidadEmpleadoDTO dto) {
        return ResponseEntity.ok(service.actualizarEstado(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarEstado(id);
        return ResponseEntity.noContent().build();
    }
}
