package com.example.MonoStyle.Service;
import com.example.MonoStyle.DTOS.ClienteDTO;
import java.util.List;

public interface ClienteService {
    List<ClienteDTO> listarClientes();
    ClienteDTO obtenerClientePorId(Long id);
    ClienteDTO crearCliente(ClienteDTO clienteDTO);
    ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO);
    void eliminarCliente(Long id);
}
