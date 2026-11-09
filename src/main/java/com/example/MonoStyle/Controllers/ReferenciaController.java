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
@RequestMapping("/api/referencias")
class ReferenciaController {
    @Autowired
    private ReferenciaService service;

    @GetMapping
    public ResponseEntity<List<ReferenciaDTO>> listar() {
        return ResponseEntity.ok(service.listarReferencias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReferenciaDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerReferenciaPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReferenciaDTO> crear(@Valid @RequestBody ReferenciaDTO dto) {
        return new ResponseEntity<>(service.crearReferencia(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReferenciaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ReferenciaDTO dto) {
        return ResponseEntity.ok(service.actualizarReferencia(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarReferencia(id);
        return ResponseEntity.noContent().build();
    }
}