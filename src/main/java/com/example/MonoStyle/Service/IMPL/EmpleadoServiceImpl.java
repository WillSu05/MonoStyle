package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.AsignacionRepuestoDTO;
import com.example.MonoStyle.DTOS.EmpleadoDTO;
import com.example.MonoStyle.DTOS.ReferenciaDTO;
import com.example.MonoStyle.Entity.*;
import com.example.MonoStyle.Repository.*;
import com.example.MonoStyle.Service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final RolRepository rolRepository;
    private final EstadoDisponibilidadEmpleadoRepository estadoRepository;

    @Autowired
    private ReferenciaRepository referenciaRepository;
    @Autowired private OrdenRepository ordenRepository;
    @Autowired private EstadoOrdenRepository estadoOrdenRepository;

    @Override
    public List<EmpleadoDTO> listarEmpleados() {
        return empleadoRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public EmpleadoDTO obtenerEmpleadoPorId(Long id) {
        Empleado emp = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return convertirADTO(emp);
    }

    @Override
    public EmpleadoDTO crearEmpleado(EmpleadoDTO dto) {
        Rol rol = rolRepository.findById(dto.getIdRol()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        EstadoDisponibilidadEmpleado estado = null;
        if (dto.getIdDisponibilidadEmpleado() != null) {
            estado = estadoRepository.findById(dto.getIdDisponibilidadEmpleado()).orElse(null);
        }

        Empleado emp = Empleado.builder()
                .nombre(dto.getNombre()).especialidad(dto.getEspecialidad())
                .rol(rol).disponibilidadEmpleado(estado).build();
        return convertirADTO(empleadoRepository.save(emp));
    }

    @Override
    public EmpleadoDTO actualizarEmpleado(Long id, EmpleadoDTO dto) {
        Empleado emp = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Rol rol = rolRepository.findById(dto.getIdRol()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        EstadoDisponibilidadEmpleado estado = null;
        if (dto.getIdDisponibilidadEmpleado() != null) {
            estado = estadoRepository.findById(dto.getIdDisponibilidadEmpleado()).orElse(null);
        }

        emp.setNombre(dto.getNombre());
        emp.setEspecialidad(dto.getEspecialidad());
        emp.setRol(rol);
        emp.setDisponibilidadEmpleado(estado);

        return convertirADTO(empleadoRepository.save(emp));
    }

    @Override
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    private EmpleadoDTO convertirADTO(Empleado e) {
        return EmpleadoDTO.builder().idEmpleado(e.getIdEmpleado()).nombre(e.getNombre())
                .especialidad(e.getEspecialidad()).idRol(e.getRol().getId())
                .idDisponibilidadEmpleado(e.getDisponibilidadEmpleado() != null ? e.getDisponibilidadEmpleado().getIdDisponibilidadEmpleado() : null).build();
    }

    @Override
    public List<ReferenciaDTO> buscarRepuestos(String criterioBusqueda) {
        // Busca repuestos que contengan la palabra clave en su descripción
        return referenciaRepository.findAll().stream()
                .filter(ref -> ref.getDescripcion().toLowerCase().contains(criterioBusqueda.toLowerCase()))
                .map(r -> ReferenciaDTO.builder().id(r.getId()).descripcion(r.getDescripcion()).idCategoria(r.getCategoria().getIdCategoria()).build())
                .collect(Collectors.toList());
    }

    @Override
    public String consultarUbicacion(Long idReferencia) {
        // En tu diagrama actual Referencia no tiene el atributo "Ubicacion",
        // por lo que devolvemos un mensaje estándar o estático por el momento.
        Referencia ref = referenciaRepository.findById(idReferencia)
                .orElseThrow(() -> new RuntimeException("Referencia no encontrada"));
        return "Ubicación en Bodega Principal (Atributo físico pendiente en BD)";
    }

    @Override
    public void asignarRepuestoAOrden(AsignacionRepuestoDTO asignacionDTO) {
        Orden orden = ordenRepository.findById(asignacionDTO.getIdOrden())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        Referencia ref = referenciaRepository.findById(asignacionDTO.getIdReferencia())
                .orElseThrow(() -> new RuntimeException("Referencia no encontrada"));

        // Lógica de negocio: Aquí se debería guardar en una tabla DetalleOrden / Kardex
        System.out.println("LÓGICA EJECUTADA: Asignando " + asignacionDTO.getCantidad() +
                " unidades del repuesto " + ref.getDescripcion() +
                " a la orden ID " + orden.getIdOrden());
    }

    @Override
    public void actualizarEstadoServicio(Long idOrden, Long idEstadoOrden) {
        Orden orden = ordenRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        EstadoOrden nuevoEstado = estadoOrdenRepository.findById(idEstadoOrden)
                .orElseThrow(() -> new RuntimeException("Estado de Orden no encontrado"));

        orden.setEstadoOrden(nuevoEstado);
        ordenRepository.save(orden);
    }

    @Override
    public void registrarDevolucionPieza(Long idOrden, Long idReferencia) {
        // Lógica para reversar un repuesto sobrante
        System.out.println("LÓGICA EJECUTADA: Registrando devolución de la pieza ID " +
                idReferencia + " de la orden ID " + idOrden);
    }
}
