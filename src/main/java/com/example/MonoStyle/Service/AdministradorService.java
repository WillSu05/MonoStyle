package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.AdministradorDTO;

import java.util.List;

public interface AdministradorService {
    List<AdministradorDTO> listarAdministradores();
    AdministradorDTO obtenerAdministradorPorId(Long id);
    AdministradorDTO crearAdministrador(AdministradorDTO dto);
    AdministradorDTO actualizarAdministrador(Long id, AdministradorDTO dto);
    void eliminarAdministrador(Long id);
}
