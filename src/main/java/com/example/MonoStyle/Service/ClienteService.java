package com.example.MonoStyle.Service;
import com.example.MonoStyle.DTOS.*;

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
    ClienteDTO registrarCliente(RegistroClienteDTO dto);
    void cambiarClave(String nombre, CambiarClaveDTO dto);
}
