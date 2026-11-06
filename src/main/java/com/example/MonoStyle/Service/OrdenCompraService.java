package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.OrdenCompraDTO;

import java.util.List;

public interface OrdenCompraService {
    List<OrdenCompraDTO> listarOrdenesCompra();
    OrdenCompraDTO obtenerOrdenCompraPorId(Long id);
    OrdenCompraDTO crearOrdenCompra(OrdenCompraDTO dto);
    OrdenCompraDTO actualizarOrdenCompra(Long id, OrdenCompraDTO dto);
    void eliminarOrdenCompra(Long id);
}
