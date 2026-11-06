package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.MarcaDTO;

import java.util.List;

public interface MarcaService {
    List<MarcaDTO> listarMarcas();
    MarcaDTO obtenerMarcaPorId(Long id);
    MarcaDTO crearMarca(MarcaDTO dto);
    MarcaDTO actualizarMarca(Long id, MarcaDTO dto);
    void eliminarMarca(Long id);
}
