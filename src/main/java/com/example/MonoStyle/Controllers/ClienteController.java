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
@RequestMapping("/api/clientes")
class ClienteController {
    @Autowired private ClienteService service;

    @GetMapping public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(service.listarClientes()); }

    @GetMapping("/{id}") public ResponseEntity<ClienteDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerClientePorId(id)); }

    @PostMapping public ResponseEntity<ClienteDTO> crear(@Valid @RequestBody ClienteDTO dto) {
        return new ResponseEntity<>(service.crearCliente(dto), HttpStatus.CREATED); }

    @PutMapping("/{id}") public ResponseEntity<ClienteDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(service.actualizarCliente(id, dto)); }

    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarCliente(id); return ResponseEntity.noContent().build(); }

    @PostMapping("/solicitar-servicio")
    public ResponseEntity<OrdenDTO> solicitarServicio(@Valid @RequestBody SolicitudServicioDTO dto) {
        return new ResponseEntity<>(service.solicitarServicio(dto), HttpStatus.CREATED);
    }

    @GetMapping("/ordenes/{idOrden}/estado")
    public ResponseEntity<String> consultarEstadoReparacion(@PathVariable Long idOrden) {
        return ResponseEntity.ok(service.consultarEstadoReparacion(idOrden));
    }

    @PatchMapping("/ordenes/{idOrden}/cotizacion")
    public ResponseEntity<String> aprobarCotizacion(@PathVariable Long idOrden, @RequestParam boolean aprueba) {
        return ResponseEntity.ok(service.aprobarCotizacion(idOrden, aprueba));
    }

    @GetMapping("/ordenes/{idOrden}/factura")
    public ResponseEntity<String> verDetalleFacturacion(@PathVariable Long idOrden) {
        return ResponseEntity.ok(service.verDetalleFacturacion(idOrden));
    }

}
