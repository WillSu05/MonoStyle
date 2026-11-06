package com.example.MonoStyle.Service;
import com.example.MonoStyle.DTOS.OrdenDTO;
import java.util.List;

public interface OrdenService {
    List<OrdenDTO> listarOrdenes();
    OrdenDTO obtenerOrdenPorId(Long id);
    OrdenDTO crearOrden(OrdenDTO ordenDTO);
    OrdenDTO actualizarOrden(Long id, OrdenDTO ordenDTO);
    void eliminarOrden(Long id);
}
