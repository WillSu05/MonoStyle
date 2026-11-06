package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.EstadoDisponibilidadEmpleadoDTO;

import java.util.List;

public interface EstadoDisponibilidadEmpleadoService {
    List<EstadoDisponibilidadEmpleadoDTO> listarEstados();
    EstadoDisponibilidadEmpleadoDTO obtenerEstadoPorId(Long id);
    EstadoDisponibilidadEmpleadoDTO crearEstado(EstadoDisponibilidadEmpleadoDTO dto);
    EstadoDisponibilidadEmpleadoDTO actualizarEstado(Long id, EstadoDisponibilidadEmpleadoDTO dto);
    void eliminarEstado(Long id);
}
