package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.EstadoOrdenDTO;

import java.util.List;

public interface EstadoOrdenService {
    List<EstadoOrdenDTO> listarEstados();
    EstadoOrdenDTO obtenerEstadoPorId(Long id);
    EstadoOrdenDTO crearEstado(EstadoOrdenDTO dto);
    EstadoOrdenDTO actualizarEstado(Long id, EstadoOrdenDTO dto);
    void eliminarEstado(Long id);
}
