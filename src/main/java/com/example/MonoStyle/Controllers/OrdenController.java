package com.example.MonoStyle.Controllers;
import com.example.MonoStyle.DTOS.*;
import com.example.MonoStyle.Service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/ordenes")
class OrdenController {
    @Autowired private OrdenService service;
    @GetMapping public ResponseEntity<List<OrdenDTO>> listar() { return ResponseEntity.ok(service.listarOrdenes()); }
    @GetMapping("/{id}") public ResponseEntity<OrdenDTO> obtener(@PathVariable Long id) { return ResponseEntity.ok(service.obtenerOrdenPorId(id)); }
    @PostMapping public ResponseEntity<OrdenDTO> crear(@Valid @RequestBody OrdenDTO dto) { return new ResponseEntity<>(service.crearOrden(dto), HttpStatus.CREATED); }
    @PutMapping("/{id}") public ResponseEntity<OrdenDTO> actualizar(@PathVariable Long id, @Valid @RequestBody OrdenDTO dto) { return ResponseEntity.ok(service.actualizarOrden(id, dto)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminarOrden(id); return ResponseEntity.noContent().build(); }
}