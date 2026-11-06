package com.example.MonoStyle.Service;

import com.example.MonoStyle.DTOS.CategoriaDTO;

import java.util.List;

public interface CategoriaService {
    List<CategoriaDTO> listarCategorias();
    CategoriaDTO obtenerCategoriaPorId(Long id);
    CategoriaDTO crearCategoria(CategoriaDTO dto);
    CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto);
    void eliminarCategoria(Long id);
}
