package com.example.MonoStyle.Controllers;

import com.example.MonoStyle.DTOS.MotoClienteDTO;
import com.example.MonoStyle.Entity.Cliente;
import com.example.MonoStyle.Entity.Marca;
import com.example.MonoStyle.Entity.Motocicleta;
import com.example.MonoStyle.Repository.ClienteRepository;
import com.example.MonoStyle.Repository.MarcaRepository;
import com.example.MonoStyle.Repository.MotocicletaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mis-motos")
@PreAuthorize("hasRole('CLIENTE')")
public class MisMotosController {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private MotocicletaRepository motocicletaRepository;
    @Autowired private MarcaRepository marcaRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public List<MotoClienteDTO> listar(Authentication auth) {
        Cliente cliente = clienteActual(auth);
        return motocicletaRepository.findByCliente_IdCliente(cliente.getIdCliente())
                .stream().map(this::aDTO).collect(Collectors.toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<MotoClienteDTO> crear(@Valid @RequestBody MotoClienteDTO dto,
                                                Authentication auth) {
        Cliente cliente = clienteActual(auth);

        String placa = dto.getPlaca().trim().toUpperCase();
        if (motocicletaRepository.existsByPlacaIgnoreCase(placa)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esa placa ya está registrada");
        }

        Marca marca = marcaRepository.findByNombreIgnoreCase(dto.getMarca().trim())
                .orElseGet(() -> marcaRepository.save(
                        Marca.builder().nombre(dto.getMarca().trim()).build()));

        Motocicleta moto = Motocicleta.builder()
                .modelo(dto.getModelo().trim())
                .año(dto.getAnio())
                .placa(placa)
                .color(dto.getColor().trim())
                .marca(marca)
                .cliente(cliente)
                .build();

        return new ResponseEntity<>(aDTO(motocicletaRepository.save(moto)), HttpStatus.CREATED);
    }

    private Cliente clienteActual(Authentication auth) {
        return clienteRepository.findByNombre(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    private MotoClienteDTO aDTO(Motocicleta m) {
        return MotoClienteDTO.builder()
                .marca(m.getMarca().getNombre())
                .modelo(m.getModelo())
                .placa(m.getPlaca())
                .anio(m.getAño())
                .color(m.getColor())
                .build();
    }
}
