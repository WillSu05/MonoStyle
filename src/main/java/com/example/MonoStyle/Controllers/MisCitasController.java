package com.example.MonoStyle.Controllers;

import com.example.MonoStyle.DTOS.CitaDTO;
import com.example.MonoStyle.Entity.Cita;
import com.example.MonoStyle.Entity.Cliente;
import com.example.MonoStyle.Repository.CitaRepository;
import com.example.MonoStyle.Repository.ClienteRepository;
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
@RequestMapping("/api/mis-citas")
@PreAuthorize("hasRole('CLIENTE')")
public class MisCitasController {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private CitaRepository citaRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public List<CitaDTO> listar(Authentication auth) {
        Cliente cliente = clienteActual(auth);
        return citaRepository.findByCliente_IdClienteOrderByIdCitaDesc(cliente.getIdCliente())
                .stream().map(this::aDTO).collect(Collectors.toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CitaDTO> crear(@Valid @RequestBody CitaDTO dto, Authentication auth) {
        Cliente cliente = clienteActual(auth);

        Cita cita = Cita.builder()
                .cliente(cliente)
                .vehiculo(dto.getVehiculo().trim())
                .servicio(dto.getServicio().trim())
                .fecha(dto.getFecha().trim())
                .hora(dto.getHora().trim())
                .observaciones(dto.getObservaciones() == null ? null : dto.getObservaciones().trim())
                .estado("Pendiente")
                .build();

        return new ResponseEntity<>(aDTO(citaRepository.save(cita)), HttpStatus.CREATED);
    }

    private Cliente clienteActual(Authentication auth) {
        return clienteRepository.findByNombre(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    private CitaDTO aDTO(Cita c) {
        return CitaDTO.builder()
                .vehiculo(c.getVehiculo())
                .servicio(c.getServicio())
                .fecha(c.getFecha())
                .hora(c.getHora())
                .observaciones(c.getObservaciones())
                .estado(c.getEstado())
                .build();
    }
}