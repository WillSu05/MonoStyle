package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.MotocicletaDTO;

import java.util.List;

public interface MotocicletaService {
    List<MotocicletaDTO> listarMotocicletas();
    MotocicletaDTO obtenerMotocicletaPorId(Long id);
    MotocicletaDTO crearMotocicleta(MotocicletaDTO dto);
    MotocicletaDTO actualizarMotocicleta(Long id, MotocicletaDTO dto);
    void eliminarMotocicleta(Long id);
}
