package com.example.MonoStyle.Service;
import com.example.MonoStyle.DTOS.ClienteDTO;
import com.example.MonoStyle.DTOS.OrdenDTO;
import com.example.MonoStyle.DTOS.SolicitudServicioDTO;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> listarClientes();
    ClienteDTO obtenerClientePorId(Long id);
    ClienteDTO crearCliente(ClienteDTO clienteDTO);
    ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO);
    void eliminarCliente(Long id);
    OrdenDTO solicitarServicio(SolicitudServicioDTO dto);
    String consultarEstadoReparacion(Long idOrden);
    String aprobarCotizacion(Long idOrden, boolean aprueba);
    String verDetalleFacturacion(Long idOrden);
}
