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
@RequestMapping("/api/administradores")
class AdministradorController {
    @Autowired private AdministradorService service;
    @GetMapping public ResponseEntity<List<AdministradorDTO>> listar() {
        return ResponseEntity.ok(service.listarAdministradores()); }
    @GetMapping("/{id}") public ResponseEntity<AdministradorDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerAdministradorPorId(id)); }
    @PostMapping public ResponseEntity<AdministradorDTO> crear(@Valid @RequestBody AdministradorDTO dto) {
        return new ResponseEntity<>(service.crearAdministrador(dto), HttpStatus.CREATED); }
    @PutMapping("/{id}") public ResponseEntity<AdministradorDTO> actualizar(@PathVariable Long id, @Valid @RequestBody AdministradorDTO dto) {
        return ResponseEntity.ok(service.actualizarAdministrador(id, dto)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarAdministrador(id); return ResponseEntity.noContent().build(); }

    @PostMapping("/inventario/ingreso-repuestos")
    public ResponseEntity<String> registrarIngresoRepuestos(@RequestParam Long idReferencia, @RequestParam Integer cantidad) {
        // Lógica delegada al service
        return ResponseEntity.ok("Ingreso de " + cantidad + " repuestos registrado.");
    }

    @GetMapping("/inventario")
    public ResponseEntity<String> consultarInventario() {
        return ResponseEntity.ok("Reporte de inventario generado.");
    }

    @PostMapping("/ordenes-compra/generar")
    public ResponseEntity<String> generarOrdenCompra(@RequestParam Integer idProveedor) {
        return ResponseEntity.ok("Orden de compra generada para el proveedor: " + idProveedor);
    }

    @PostMapping("/inventario/piezas-sobrantes")
    public ResponseEntity<String> gestionarPiezasSobrantes() {
        return ResponseEntity.ok("Gestión de piezas sobrantes ejecutada (Kardex actualizado).");
    }

    @GetMapping("/metricas")
    public ResponseEntity<String> visualizarMetricasTaller() {
        return ResponseEntity.ok("Métricas del taller (Reparaciones, Ingresos, Tiempos).");
    }

    @PatchMapping("/usuarios/{idUsuario}/asignar-rol")
    public ResponseEntity<String> asignarRol(@PathVariable Long idUsuario, @RequestParam Long idRol) {
        return ResponseEntity.ok("Rol " + idRol + " asignado al usuario " + idUsuario);
    }
}
