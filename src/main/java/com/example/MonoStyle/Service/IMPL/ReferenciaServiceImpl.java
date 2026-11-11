package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.ReferenciaDTO;
import com.example.MonoStyle.Entity.Categoria;
import com.example.MonoStyle.Entity.Referencia;
import com.example.MonoStyle.Repository.CategoriaRepository;
import com.example.MonoStyle.Repository.ReferenciaRepository;
import com.example.MonoStyle.Service.ReferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReferenciaServiceImpl implements ReferenciaService {

    private final ReferenciaRepository referenciaRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public List<ReferenciaDTO> listarReferencias() {
        return referenciaRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public ReferenciaDTO obtenerReferenciaPorId(Long id) {
        Referencia ref = referenciaRepository.findById(id).orElseThrow(() -> new RuntimeException("Referencia no encontrada"));
        return convertirADTO(ref);
    }

    @Override
    public ReferenciaDTO crearReferencia(ReferenciaDTO dto) {
        Categoria cat = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Referencia ref = Referencia.builder().descripcion(dto.getDescripcion()).stock(dto.getStock()).categoria(cat).build();
        return convertirADTO(referenciaRepository.save(ref));
    }

    @Override
    public ReferenciaDTO actualizarReferencia(Long id, ReferenciaDTO dto) {
        Referencia ref = referenciaRepository.findById(id).orElseThrow(() -> new RuntimeException("Referencia no encontrada"));
        Categoria cat = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        ref.setDescripcion(dto.getDescripcion());
        ref.setCategoria(cat);
        return convertirADTO(referenciaRepository.save(ref));
    }

    @Override
    public void eliminarReferencia(Long id) {
        referenciaRepository.deleteById(id);
    }

    private ReferenciaDTO convertirADTO(Referencia r) {
        return ReferenciaDTO.builder().id(r.getId()).descripcion(r.getDescripcion()).idCategoria(r.getCategoria().getIdCategoria()).stock(r.getStock()).build();
    }
}
