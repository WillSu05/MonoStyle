package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.ReferenciaDTO;

import java.util.List;

public interface ReferenciaService {
    List<ReferenciaDTO> listarReferencias();
    ReferenciaDTO obtenerReferenciaPorId(Long id);
    ReferenciaDTO crearReferencia(ReferenciaDTO dto);
    ReferenciaDTO actualizarReferencia(Long id, ReferenciaDTO dto);
    void eliminarReferencia(Long id);
}
