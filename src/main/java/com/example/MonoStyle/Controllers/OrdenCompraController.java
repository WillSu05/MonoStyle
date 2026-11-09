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
@RequestMapping("/api/ordenes-compra")
class OrdenCompraController {
    @Autowired private OrdenCompraService service;
    @GetMapping public ResponseEntity<List<OrdenCompraDTO>> listar() { return ResponseEntity.ok(service.listarOrdenesCompra()); }
    @GetMapping("/{id}") public ResponseEntity<OrdenCompraDTO> obtener(@PathVariable Long id) { return ResponseEntity.ok(service.obtenerOrdenCompraPorId(id)); }
    @PostMapping public ResponseEntity<OrdenCompraDTO> crear(@Valid @RequestBody OrdenCompraDTO dto) { return new ResponseEntity<>(service.crearOrdenCompra(dto), HttpStatus.CREATED); }
    @PutMapping("/{id}") public ResponseEntity<OrdenCompraDTO> actualizar(@PathVariable Long id, @Valid @RequestBody OrdenCompraDTO dto) { return ResponseEntity.ok(service.actualizarOrdenCompra(id, dto)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminarOrdenCompra(id); return ResponseEntity.noContent().build(); }
}
