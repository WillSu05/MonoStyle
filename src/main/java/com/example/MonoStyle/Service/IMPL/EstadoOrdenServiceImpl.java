package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.EstadoOrdenDTO;
import com.example.MonoStyle.Entity.EstadoOrden;
import com.example.MonoStyle.Repository.EstadoOrdenRepository;
import com.example.MonoStyle.Service.EstadoOrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadoOrdenServiceImpl implements EstadoOrdenService {

    private final EstadoOrdenRepository repository;

    @Override
    public List<EstadoOrdenDTO> listarEstados() {
        return repository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public EstadoOrdenDTO obtenerEstadoPorId(Long id) {
        EstadoOrden estado = repository.findById(id).orElseThrow(() -> new RuntimeException("Estado de Orden no encontrado"));
        return convertirADTO(estado);
    }

    @Override
    public EstadoOrdenDTO crearEstado(EstadoOrdenDTO dto) {
        EstadoOrden estado = EstadoOrden.builder().descripcion(dto.getDescripcion()).build();
        return convertirADTO(repository.save(estado));
    }

    @Override
    public EstadoOrdenDTO actualizarEstado(Long id, EstadoOrdenDTO dto) {
        EstadoOrden estado = repository.findById(id).orElseThrow(() -> new RuntimeException("Estado de Orden no encontrado"));
        estado.setDescripcion(dto.getDescripcion());
        return convertirADTO(repository.save(estado));
    }

    @Override
    public void eliminarEstado(Long id) {
        repository.deleteById(id);
    }

    private EstadoOrdenDTO convertirADTO(EstadoOrden e) {
        return EstadoOrdenDTO.builder().idEstadoOrden(e.getIdEstadoOrden()).descripcion(e.getDescripcion()).build();
    }
}
