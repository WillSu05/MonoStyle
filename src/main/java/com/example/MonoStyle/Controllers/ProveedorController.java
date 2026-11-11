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
@RequestMapping("/api/proveedores")
class ProveedorController {
    @Autowired private ProveedorService service;
    @GetMapping public ResponseEntity<List<ProveedorDTO>> listar() {
        return ResponseEntity.ok(service.listarProveedores()); }

    @GetMapping("/{id}") public ResponseEntity<ProveedorDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerProveedorPorId(id)); } // ID Integer

    @PostMapping public ResponseEntity<ProveedorDTO> crear(@Valid @RequestBody ProveedorDTO dto) {
        return new ResponseEntity<>(service.crearProveedor(dto), HttpStatus.CREATED); }

    @PutMapping("/{id}") public ResponseEntity<ProveedorDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ProveedorDTO dto) {
        return ResponseEntity.ok(service.actualizarProveedor(id, dto)); }

    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminarProveedor(id); return ResponseEntity.noContent().build(); }

    @PutMapping("/{id}/catalogo")
    public ResponseEntity<String> actualizarCatalogoPrecios(@PathVariable Integer id) {
        return ResponseEntity.ok("Catálogo de precios actualizado para el proveedor " + id);
    }

    @PostMapping("/pedidos/{idOrdenCompra}/confirmar")
    public ResponseEntity<String> confirmarRecepcionPedido(@PathVariable Long idOrdenCompra) {
        return ResponseEntity.ok("Recepción del pedido " + idOrdenCompra + " confirmada.");
    }

    @PatchMapping("/pedidos/{idOrdenCompra}/fecha-entrega")
    public ResponseEntity<String> establecerFechaEntrega(@PathVariable Long idOrdenCompra, @RequestParam String fecha) {
        return ResponseEntity.ok("Fecha de entrega para el pedido " + idOrdenCompra + " establecida para: " + fecha);
    }
}
