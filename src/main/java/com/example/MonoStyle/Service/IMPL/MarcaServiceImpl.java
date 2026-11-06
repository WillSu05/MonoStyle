package com.example.MonoStyle.Service.IMPL;

import com.example.MonoStyle.DTOS.MarcaDTO;
import com.example.MonoStyle.Entity.Marca;
import com.example.MonoStyle.Repository.MarcaRepository;
import com.example.MonoStyle.Service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements MarcaService {
    private final MarcaRepository repository;

    @Override
    public List<MarcaDTO> listarMarcas() {
        return repository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public MarcaDTO obtenerMarcaPorId(Long id) {
        Marca m = repository.findById(id).orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        return convertirADTO(m);
    }

    @Override
    public MarcaDTO crearMarca(MarcaDTO dto) {
        Marca m = Marca.builder().nombre(dto.getNombre()).build();
        return convertirADTO(repository.save(m));
    }

    @Override
    public MarcaDTO actualizarMarca(Long id, MarcaDTO dto) {
        Marca m = repository.findById(id).orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        m.setNombre(dto.getNombre());
        return convertirADTO(repository.save(m));
    }

    @Override
    public void eliminarMarca(Long id) {
        repository.deleteById(id);
    }

    private MarcaDTO convertirADTO(Marca m) {
        return MarcaDTO.builder().idMarca(m.getIdMarca()).nombre(m.getNombre()).build();
    }
}
