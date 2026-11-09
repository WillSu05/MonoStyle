package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.AsignacionRepuestoDTO;
import com.example.MonoStyle.DTOS.EmpleadoDTO;
import com.example.MonoStyle.DTOS.ReferenciaDTO;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoDTO> listarEmpleados();
    EmpleadoDTO obtenerEmpleadoPorId(Long id);
    EmpleadoDTO crearEmpleado(EmpleadoDTO dto);
    EmpleadoDTO actualizarEmpleado(Long id, EmpleadoDTO dto);
    void eliminarEmpleado(Long id);

    List<ReferenciaDTO> buscarRepuestos(String criterioBusqueda);
    String consultarUbicacion(Long idReferencia);
    void asignarRepuestoAOrden(AsignacionRepuestoDTO asignacionDTO);
    void actualizarEstadoServicio(Long idOrden, Long idEstadoOrden);
    void registrarDevolucionPieza(Long idOrden, Long idReferencia);
}
