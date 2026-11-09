package com.example.MonoStyle.Controllers;

import com.example.MonoStyle.DTOS.MarcaDTO;
import com.example.MonoStyle.Service.MarcaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
class MarcaController {
    @Autowired
    private MarcaService service;

    @GetMapping
    public ResponseEntity<List<MarcaDTO>> listar() {
        return ResponseEntity.ok(service.listarMarcas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerMarcaPorId(id));
    }

    @PostMapping
    public ResponseEntity<MarcaDTO> crear(@Valid @RequestBody MarcaDTO dto) {
        return new ResponseEntity<>(service.crearMarca(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody MarcaDTO dto) {
        return ResponseEntity.ok(service.actualizarMarca(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarMarca(id);
        return ResponseEntity.noContent().build();
    }
}
