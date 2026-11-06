package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.EmpleadoDTO;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoDTO> listarEmpleados();
    EmpleadoDTO obtenerEmpleadoPorId(Long id);
    EmpleadoDTO crearEmpleado(EmpleadoDTO dto);
    EmpleadoDTO actualizarEmpleado(Long id, EmpleadoDTO dto);
    void eliminarEmpleado(Long id);
}
