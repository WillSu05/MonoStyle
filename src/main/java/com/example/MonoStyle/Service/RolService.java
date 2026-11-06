package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.RolDTO;

import java.util.List;

public interface RolService {
    List<RolDTO> listarRoles();
    RolDTO obtenerRolPorId(Long id);
    RolDTO crearRol(RolDTO dto);
    RolDTO actualizarRol(Long id, RolDTO dto);
    void eliminarRol(Long id);
}
