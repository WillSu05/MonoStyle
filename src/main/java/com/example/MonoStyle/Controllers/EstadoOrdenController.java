package com.example.MonoStyle.Controllers;

import com.example.MonoStyle.DTOS.EstadoOrdenDTO;
import com.example.MonoStyle.Service.EstadoOrdenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados-orden")
class EstadoOrdenController {
    @Autowired
    private EstadoOrdenService service;

    @GetMapping
    public ResponseEntity<List<EstadoOrdenDTO>> listar() {
        return ResponseEntity.ok(service.listarEstados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoOrdenDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerEstadoPorId(id));
    }

    @PostMapping
    public ResponseEntity<EstadoOrdenDTO> crear(@Valid @RequestBody EstadoOrdenDTO dto) {
        return new ResponseEntity<>(service.crearEstado(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoOrdenDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EstadoOrdenDTO dto) {
        return ResponseEntity.ok(service.actualizarEstado(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarEstado(id);
        return ResponseEntity.noContent().build();
    }
}