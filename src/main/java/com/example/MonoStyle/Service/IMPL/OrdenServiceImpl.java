package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.OrdenDTO;
import com.example.MonoStyle.Entity.Cliente;
import com.example.MonoStyle.Entity.Empleado;
import com.example.MonoStyle.Entity.EstadoOrden;
import com.example.MonoStyle.Entity.Orden;
import com.example.MonoStyle.Repository.ClienteRepository;
import com.example.MonoStyle.Repository.EmpleadoRepository;
import com.example.MonoStyle.Repository.EstadoOrdenRepository;
import com.example.MonoStyle.Repository.OrdenRepository;
import com.example.MonoStyle.Service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private OrdenRepository ordenRepository;
    private EmpleadoRepository empleadoRepository;
    private ClienteRepository clienteRepository;
    private EstadoOrdenRepository estadoOrdenRepository;

    @Override
    public List<OrdenDTO> listarOrdenes() {
        return ordenRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public OrdenDTO obtenerOrdenPorId(Long id) {
        Orden orden = ordenRepository.findById(id).orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        return convertirADTO(orden);
    }

    @Override
    public OrdenDTO crearOrden(OrdenDTO dto) {
        Empleado emp = empleadoRepository.findById(dto.getIdEmpleado()).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Cliente cli = clienteRepository.findById(dto.getIdCliente()).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        EstadoOrden estado = estadoOrdenRepository.findById(dto.getIdEstadoOrden()).orElseThrow(() -> new RuntimeException("Estado de Orden no encontrado"));

        Orden orden = Orden.builder()
                .descripcion(dto.getDescripcion())
                .empleado(emp).cliente(cli).estadoOrden(estado).build();

        return convertirADTO(ordenRepository.save(orden));
    }

    @Override
    public OrdenDTO actualizarOrden(Long id, OrdenDTO dto) {
        Orden orden = ordenRepository.findById(id).orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        Empleado emp = empleadoRepository.findById(dto.getIdEmpleado()).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Cliente cli = clienteRepository.findById(dto.getIdCliente()).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        EstadoOrden estado = estadoOrdenRepository.findById(dto.getIdEstadoOrden()).orElseThrow(() -> new RuntimeException("Estado de Orden no encontrado"));

        orden.setDescripcion(dto.getDescripcion());
        orden.setEmpleado(emp);
        orden.setCliente(cli);
        orden.setEstadoOrden(estado);

        return convertirADTO(ordenRepository.save(orden));
    }

    @Override
    public void eliminarOrden(Long id) {
        ordenRepository.deleteById(id);
    }

    private OrdenDTO convertirADTO(Orden o) {
        return OrdenDTO.builder().idOrden(o.getIdOrden()).descripcion(o.getDescripcion())
                .idEmpleado(o.getEmpleado() != null ? o.getEmpleado().getIdEmpleado() : null)
                .idCliente(o.getCliente() != null ? o.getCliente().getIdCliente() : null)
                .idEstadoOrden(o.getEstadoOrden() != null ? o.getEstadoOrden().getIdEstadoOrden() : null).build();
    }
}
