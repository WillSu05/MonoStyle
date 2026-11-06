package com.example.MonoStyle.Service.IMPL;

import com.example.MonoStyle.DTOS.CategoriaDTO;
import com.example.MonoStyle.Entity.Categoria;
import com.example.MonoStyle.Repository.CategoriaRepository;
import com.example.MonoStyle.Service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository repository;

    @Override
    public List<CategoriaDTO> listarCategorias() {
        return repository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        Categoria cat = repository.findById(id).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return convertirADTO(cat);
    }

    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO dto) {
        Categoria cat = Categoria.builder().descripcion(dto.getDescripcion()).build();
        return convertirADTO(repository.save(cat));
    }

    @Override
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto) {
        Categoria cat = repository.findById(id).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        cat.setDescripcion(dto.getDescripcion());
        return convertirADTO(repository.save(cat));
    }

    @Override
    public void eliminarCategoria(Long id) {
        repository.deleteById(id);
    }

    private CategoriaDTO convertirADTO(Categoria c) {
        return CategoriaDTO.builder().idCategoria(c.getIdCategoria()).descripcion(c.getDescripcion()).build();
    }
}
