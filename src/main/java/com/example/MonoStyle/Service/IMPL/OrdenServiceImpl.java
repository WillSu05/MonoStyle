package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.MisServiciosDTO;
import com.example.MonoStyle.DTOS.OrdenDTO;
import com.example.MonoStyle.Entity.*;
import com.example.MonoStyle.Repository.ClienteRepository;
import com.example.MonoStyle.Repository.EmpleadoRepository;
import com.example.MonoStyle.Repository.EstadoOrdenRepository;
import com.example.MonoStyle.Repository.OrdenRepository;
import com.example.MonoStyle.Service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    @Autowired private OrdenRepository ordenRepository;
    @Autowired private EmpleadoRepository empleadoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private EstadoOrdenRepository estadoOrdenRepository;

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

    @Override
    @Transactional(readOnly = true)
    public List<MisServiciosDTO> listarServiciosPorEmailCliente(String nombreUsuario) {
        Cliente cliente = clienteRepository.findByNombre(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + nombreUsuario));

        return ordenRepository.findByCliente_IdClienteOrderByIdOrdenDesc(cliente.getIdCliente())
                .stream()
                .map(this::convertirAMisServiciosDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MisServiciosDTO obtenerDetalleServicioCliente(Long idOrden, String nombreUsuario) {
        Cliente cliente = clienteRepository.findByNombre(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Orden orden = ordenRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        if (!orden.getCliente().getIdCliente().equals(cliente.getIdCliente())) {
            throw new RuntimeException("No tienes permiso para ver esta orden");
        }

        return convertirAMisServiciosDTO(orden);
    }

    private MisServiciosDTO convertirAMisServiciosDTO(Orden o) {
        BigDecimal total = BigDecimal.ZERO;
        if (o.getDetalles() != null) {
            for (DetalleOrden det : o.getDetalles()) {
                BigDecimal cant = BigDecimal.valueOf(det.getCantidad() != null ? det.getCantidad() : 0.0);
                BigDecimal precio = det.getPrecio() != null ? det.getPrecio() : BigDecimal.ZERO;
                total = total.add(precio.multiply(cant));
            }
        }

        String rawDesc = o.getDescripcion() != null ? o.getDescripcion() : "Servicio de taller";
        String titulo = rawDesc;
        String vehiculo = "Motocicleta registrada";
        String fecha = "Reciente";

        if (rawDesc.contains(" - Vehículo:")) {
            String[] partes = rawDesc.split(" - Vehículo:");
            titulo = partes[0].trim();
            vehiculo = partes[1].trim();
        } else if (rawDesc.contains("|")) {
            String[] partes = rawDesc.split("\\|");
            titulo = partes[0].trim();
            for (String p : partes) {
                if (p.toLowerCase().contains("vehículo") || p.toLowerCase().contains("vehiculo")) {
                    vehiculo = p.replaceAll("(?i)veh[ií]culo:", "").trim();
                }
                if (p.toLowerCase().contains("fecha")) {
                    fecha = p.replaceAll("(?i)fecha:", "").trim();
                }
            }
        }

        String estadoTexto = (o.getEstadoOrden() != null && o.getEstadoOrden().getDescripcion() != null)
                ? o.getEstadoOrden().getDescripcion()
                : "En curso";

        return MisServiciosDTO.builder()
                .idOrden(o.getIdOrden())
                .tituloServicio(titulo)
                .fecha(fecha)
                .vehiculo(vehiculo)
                .descripcion(rawDesc)
                .costo(total)
                .estado(estadoTexto)
                .build();
    }

    private OrdenDTO convertirADTO(Orden o) {
        return OrdenDTO.builder().idOrden(o.getIdOrden()).descripcion(o.getDescripcion())
                .idEmpleado(o.getEmpleado() != null ? o.getEmpleado().getIdEmpleado() : null)
                .idCliente(o.getCliente() != null ? o.getCliente().getIdCliente() : null)
                .idEstadoOrden(o.getEstadoOrden() != null ? o.getEstadoOrden().getIdEstadoOrden() : null).build();
    }
}
