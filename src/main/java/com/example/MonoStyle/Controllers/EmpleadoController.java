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
@RequestMapping("/api/empleados")
class EmpleadoController {
    @Autowired private EmpleadoService service;

    @GetMapping("/listar")
    public ResponseEntity<List<EmpleadoDTO>> listar() { return ResponseEntity.ok(service.listarEmpleados()); }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EmpleadoDTO> obtener(@PathVariable Long id) { return ResponseEntity.ok(service.obtenerEmpleadoPorId(id)); }

    @PostMapping ("/crear")
    public ResponseEntity<EmpleadoDTO> crear(@Valid @RequestBody EmpleadoDTO dto) { return new ResponseEntity<>(service.crearEmpleado(dto), HttpStatus.CREATED); }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EmpleadoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EmpleadoDTO dto) { return ResponseEntity.ok(service.actualizarEmpleado(id, dto)); }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminarEmpleado(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/repuestos/buscar")
    public ResponseEntity<List<ReferenciaDTO>> buscarRepuestos(@RequestParam String criterio) {
        return ResponseEntity.ok(service.buscarRepuestos(criterio));
    }

    // GET /api/empleados/repuestos/{id}/ubicacion
    @GetMapping("/repuestos/{id}/ubicacion")
    public ResponseEntity<String> consultarUbicacion(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarUbicacion(id));
    }

    // POST /api/empleados/ordenes/asignar-repuesto
    @PostMapping("/ordenes/asignar-repuesto")
    public ResponseEntity<String> asignarRepuestoAOrden(@Valid @RequestBody AsignacionRepuestoDTO dto) {
        service.asignarRepuestoAOrden(dto);
        return ResponseEntity.ok("Repuesto asignado correctamente a la orden.");
    }

    // PATCH /api/empleados/ordenes/{idOrden}/estado/{idEstadoOrden}
    @PatchMapping("/ordenes/{idOrden}/estado/{idEstadoOrden}")
    public ResponseEntity<String> actualizarEstadoServicio(@PathVariable Long idOrden, @PathVariable Long idEstadoOrden) {
        service.actualizarEstadoServicio(idOrden, idEstadoOrden);
        return ResponseEntity.ok("Estado de la orden actualizado exitosamente.");
    }

    // POST /api/empleados/ordenes/{idOrden}/devolucion/{idReferencia}
    @PostMapping("/ordenes/{idOrden}/devolucion/{idReferencia}")
    public ResponseEntity<String> registrarDevolucionPieza(@PathVariable Long idOrden, @PathVariable Long idReferencia) {
        service.registrarDevolucionPieza(idOrden, idReferencia);
        return ResponseEntity.ok("Devolución de pieza registrada.");
    }
}
