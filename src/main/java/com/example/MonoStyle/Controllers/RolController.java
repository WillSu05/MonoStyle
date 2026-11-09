package com.example.MonoStyle.Controllers;

import com.example.MonoStyle.DTOS.RolDTO;
import com.example.MonoStyle.Service.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
class RolController {
    private final RolService service;
    @GetMapping public ResponseEntity<List<RolDTO>> listar() { return ResponseEntity.ok(service.listarRoles()); }
    @GetMapping("/{id}") public ResponseEntity<RolDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerRolPorId(id)); }
    @PostMapping public ResponseEntity<RolDTO> crear(@Valid @RequestBody RolDTO dto) {
        return new ResponseEntity<>(service.crearRol(dto), HttpStatus.CREATED); }
    @PutMapping("/{id}") public ResponseEntity<RolDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RolDTO dto) {
        return ResponseEntity.ok(service.actualizarRol(id, dto)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarRol(id); return ResponseEntity.noContent().build(); }
}
