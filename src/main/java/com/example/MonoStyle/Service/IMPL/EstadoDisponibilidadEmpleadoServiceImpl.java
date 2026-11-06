package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.EstadoDisponibilidadEmpleadoDTO;
import com.example.MonoStyle.Entity.EstadoDisponibilidadEmpleado;
import com.example.MonoStyle.Repository.EstadoDisponibilidadEmpleadoRepository;
import com.example.MonoStyle.Service.EstadoDisponibilidadEmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadoDisponibilidadEmpleadoServiceImpl implements EstadoDisponibilidadEmpleadoService {

    private final EstadoDisponibilidadEmpleadoRepository repository;

    @Override
    public List<EstadoDisponibilidadEmpleadoDTO> listarEstados() {
        return repository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public EstadoDisponibilidadEmpleadoDTO obtenerEstadoPorId(Long id) {
        EstadoDisponibilidadEmpleado estado = repository.findById(id).orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        return convertirADTO(estado);
    }

    @Override
    public EstadoDisponibilidadEmpleadoDTO crearEstado(EstadoDisponibilidadEmpleadoDTO dto) {
        EstadoDisponibilidadEmpleado estado = EstadoDisponibilidadEmpleado.builder().descripcion(dto.getDescripcion()).build();
        return convertirADTO(repository.save(estado));
    }

    @Override
    public EstadoDisponibilidadEmpleadoDTO actualizarEstado(Long id, EstadoDisponibilidadEmpleadoDTO dto) {
        EstadoDisponibilidadEmpleado estado = repository.findById(id).orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        estado.setDescripcion(dto.getDescripcion());
        return convertirADTO(repository.save(estado));
    }

    @Override
    public void eliminarEstado(Long id) {
        repository.deleteById(id);
    }

    private EstadoDisponibilidadEmpleadoDTO convertirADTO(EstadoDisponibilidadEmpleado e) {
        return EstadoDisponibilidadEmpleadoDTO.builder().idDisponibilidadEmpleado(e.getIdDisponibilidadEmpleado()).descripcion(e.getDescripcion()).build();
    }
}
