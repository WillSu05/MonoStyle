package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.ProveedorDTO;

import java.util.List;

public interface ProveedorService {
    List<ProveedorDTO> listarProveedores();
    ProveedorDTO obtenerProveedorPorId(Integer id); // Ojo: el ID de Proveedor es Integer en la Entidad
    ProveedorDTO crearProveedor(ProveedorDTO dto);
    ProveedorDTO actualizarProveedor(Integer id, ProveedorDTO dto);
    void eliminarProveedor(Integer id);
}
