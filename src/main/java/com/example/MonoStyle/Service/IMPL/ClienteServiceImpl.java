package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.ClienteDTO;
import com.example.MonoStyle.DTOS.OrdenDTO;
import com.example.MonoStyle.DTOS.SolicitudServicioDTO;
import com.example.MonoStyle.Entity.*;
import com.example.MonoStyle.Repository.*;
import com.example.MonoStyle.Service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final RolRepository rolRepository;
    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired private EstadoOrdenRepository estadoOrdenRepository;
    @Autowired private EmpleadoRepository empleadoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

        Cliente cliente = Cliente.builder()
                .nombre(dto.getNombre())
                .telefono(dto.getTelefono())
                // ¡Línea crucial añadida!: Encriptamos la contraseña para cumplir con la base de datos
                .credenciales(passwordEncoder.encode(dto.getCredenciales()))
                .rol(rol)
                .build();

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
                .telefono(c.getTelefono()).credenciales(c.getCredenciales()).idRol(c.getRol().getId()).build();
    }
    @Override
    public OrdenDTO solicitarServicio(SolicitudServicioDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Se asume que el ID 1 corresponde a "Pendiente" o "En Recepción" en la BD
        EstadoOrden estadoInicial = estadoOrdenRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Estado inicial no configurado en la BD"));

        // Se puede asignar a un empleado "Receptor" por defecto (ej. ID 1) temporalmente
        Empleado empleadoReceptor = empleadoRepository.findById(dto.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado de recepción no configurado"));

        Orden nuevaOrden = Orden.builder()
                .descripcion(dto.getDescripcionProblema())
                .cliente(cliente)
                .empleado(empleadoReceptor)
                .estadoOrden(estadoInicial)
                .build();

        Orden ordenGuardada = ordenRepository.save(nuevaOrden);

        return OrdenDTO.builder()
                .idOrden(ordenGuardada.getIdOrden())
                .descripcion(ordenGuardada.getDescripcion())
                .idCliente(cliente.getIdCliente())
                .idEmpleado(empleadoReceptor.getIdEmpleado())
                .idEstadoOrden(estadoInicial.getIdEstadoOrden())
                .build();
    }

    @Override
    public String consultarEstadoReparacion(Long idOrden) {
        Orden orden = ordenRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        return "El estado actual de su motocicleta es: " + orden.getEstadoOrden().getDescripcion();
    }

    @Override
    public String aprobarCotizacion(Long idOrden, boolean aprueba) {
        Orden orden = ordenRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // Asumiendo que ID 2 es "Aprobada" y ID 3 es "Rechazada" en tu tabla estado_orden
        Long idNuevoEstado = aprueba ? 2L : 3L;
        EstadoOrden nuevoEstado = estadoOrdenRepository.findById(idNuevoEstado)
                .orElseThrow(() -> new RuntimeException("Estado destino no encontrado en la BD"));

        orden.setEstadoOrden(nuevoEstado);
        ordenRepository.save(orden);

        return aprueba ? "Cotización aprobada. Iniciando trabajos." : "Cotización rechazada. Puede retirar su motocicleta.";
    }

    @Override
    public String verDetalleFacturacion(Long idOrden) {
        Orden orden = ordenRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // Como no hay tabla de facturación en el diagrama de 13 tablas, se simula el cálculo o se expone el resumen
        return "Resumen de Facturación para la Orden #" + orden.getIdOrden() + "\n" +
                "Cliente: " + orden.getCliente().getNombre() + "\n" +
                "Descripción: " + orden.getDescripcion() + "\n" +
                "Estado: " + orden.getEstadoOrden().getDescripcion() + "\n" +
                "Total Estimado: Pendiente de módulo de costos.";
    }
}
