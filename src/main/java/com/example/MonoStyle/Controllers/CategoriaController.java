package com.example.MonoStyle.Controllers;

import com.example.MonoStyle.DTOS.CategoriaDTO;
import com.example.MonoStyle.Service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
class CategoriaController {
    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        return ResponseEntity.ok(service.listarCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerCategoriaPorId(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> crear(@Valid @RequestBody CategoriaDTO dto) {
        return new ResponseEntity<>(service.crearCategoria(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(service.actualizarCategoria(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
