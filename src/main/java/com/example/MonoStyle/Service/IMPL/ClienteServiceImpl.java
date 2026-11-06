package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.ClienteDTO;
import com.example.MonoStyle.Entity.Cliente;
import com.example.MonoStyle.Entity.Rol;
import com.example.MonoStyle.Repository.ClienteRepository;
import com.example.MonoStyle.Repository.RolRepository;
import com.example.MonoStyle.Service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final RolRepository rolRepository;

    @Override
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public ClienteDTO obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return convertirADTO(cliente);
    }

    @Override
    public ClienteDTO crearCliente(ClienteDTO dto) {
        Rol rol = rolRepository.findById(dto.getIdRol()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Cliente cliente = Cliente.builder().nombre(dto.getNombre()).telefono(dto.getTelefono()).rol(rol).build();
        return convertirADTO(clienteRepository.save(cliente));
    }

    @Override
    public ClienteDTO actualizarCliente(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Rol rol = rolRepository.findById(dto.getIdRol()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        cliente.setNombre(dto.getNombre());
        cliente.setTelefono(dto.getTelefono());
        cliente.setRol(rol);
        return convertirADTO(clienteRepository.save(cliente));
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    private ClienteDTO convertirADTO(Cliente c) {
        return ClienteDTO.builder().idCliente(c.getIdCliente()).nombre(c.getNombre())
                .telefono(c.getTelefono()).idRol(c.getRol().getId()).build();
    }
}
