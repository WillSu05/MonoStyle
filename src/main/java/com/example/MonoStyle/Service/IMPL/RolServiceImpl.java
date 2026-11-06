package com.example.MonoStyle.Service.IMPL;

import com.example.MonoStyle.DTOS.RolDTO;
import com.example.MonoStyle.Entity.Rol;
import com.example.MonoStyle.Repository.RolRepository;
import com.example.MonoStyle.Service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {
    private final RolRepository repository;

    @Override public List<RolDTO> listarRoles() {
        return repository.findAll().stream().map(r -> new RolDTO(r.getId(), r.getDescripcion())).collect(Collectors.toList());
    }
    @Override public RolDTO obtenerRolPorId(Long id) {
        Rol r = repository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return new RolDTO(r.getId(), r.getDescripcion());
    }
    @Override public RolDTO crearRol(RolDTO dto) {
        Rol r = repository.save(Rol.builder().descripcion(dto.getDescripcion()).build());
        return new RolDTO(r.getId(), r.getDescripcion());
    }
    @Override public RolDTO actualizarRol(Long id, RolDTO dto) {
        Rol r = repository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        r.setDescripcion(dto.getDescripcion());
        r = repository.save(r);
        return new RolDTO(r.getId(), r.getDescripcion());
    }
    @Override public void eliminarRol(Long id) { repository.deleteById(id); }
}