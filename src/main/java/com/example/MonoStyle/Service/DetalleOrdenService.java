package com.example.MonoStyle.Service;
import com.example.MonoStyle.DTOS.DetalleOrdenDTO;
import java.util.List;

public interface DetalleOrdenService {
    List<DetalleOrdenDTO> listarDetallesPorOrden(Long idOrden);
    DetalleOrdenDTO obtenerDetallePorId(Long idDetalleOrden);
    DetalleOrdenDTO agregarDetalle(DetalleOrdenDTO dto);
    DetalleOrdenDTO actualizarCantidad(Long idDetalleOrden, Integer nuevaCantidad);
    void eliminarDetalle(Long idDetalleOrden);
}
