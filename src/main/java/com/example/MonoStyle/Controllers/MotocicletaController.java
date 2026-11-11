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
@RequestMapping("/api/motocicletas")
class MotocicletaController {
    @Autowired
    private MotocicletaService service;

    @GetMapping
    public ResponseEntity<List<MotocicletaDTO>> listar() {
        return ResponseEntity.ok(service.listarMotocicletas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotocicletaDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerMotocicletaPorId(id));
    }

    @PostMapping
    public ResponseEntity<MotocicletaDTO> crear(@Valid @RequestBody MotocicletaDTO dto) {
        return new ResponseEntity<>(service.crearMotocicleta(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotocicletaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody MotocicletaDTO dto) {
        return ResponseEntity.ok(service.actualizarMotocicleta(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarMotocicleta(id);
        return ResponseEntity.noContent().build();
    }
}
