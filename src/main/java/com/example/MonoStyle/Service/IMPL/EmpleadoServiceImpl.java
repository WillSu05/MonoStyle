package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.EmpleadoDTO;
import com.example.MonoStyle.Entity.Empleado;
import com.example.MonoStyle.Entity.EstadoDisponibilidadEmpleado;
import com.example.MonoStyle.Entity.Rol;
import com.example.MonoStyle.Repository.EmpleadoRepository;
import com.example.MonoStyle.Repository.EstadoDisponibilidadEmpleadoRepository;
import com.example.MonoStyle.Repository.RolRepository;
import com.example.MonoStyle.Service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private EmpleadoRepository empleadoRepository;
    private RolRepository rolRepository;
    private EstadoDisponibilidadEmpleadoRepository estadoRepository;

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
}
