package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.ProveedorDTO;
import com.example.MonoStyle.Entity.Proveedor;
import com.example.MonoStyle.Entity.Referencia;
import com.example.MonoStyle.Repository.ProveedorRepository;
import com.example.MonoStyle.Repository.ReferenciaRepository;
import com.example.MonoStyle.Service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ReferenciaRepository referenciaRepository;

    @Override
    public List<ProveedorDTO> listarProveedores() {
        return proveedorRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public ProveedorDTO obtenerProveedorPorId(Integer id) {
        Proveedor prov = proveedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        return convertirADTO(prov);
    }

    @Override
    public ProveedorDTO crearProveedor(ProveedorDTO dto) {
        Referencia ref = referenciaRepository.findById(dto.getIdReferencia())
                .orElseThrow(() -> new RuntimeException("Referencia no encontrada"));

        Proveedor prov = Proveedor.builder()
                .nombreEmpresa(dto.getNombreEmpresa())
                .categoriaRepuestos(dto.getCategoriaRepuestos())
                .tiempoPromedioEntrega(dto.getTiempoPromedioEntrega())
                .referencia(ref).build();
        return convertirADTO(proveedorRepository.save(prov));
    }

    @Override
    public ProveedorDTO actualizarProveedor(Integer id, ProveedorDTO dto) {
        Proveedor prov = proveedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        Referencia ref = referenciaRepository.findById(dto.getIdReferencia())
                .orElseThrow(() -> new RuntimeException("Referencia no encontrada"));

        prov.setNombreEmpresa(dto.getNombreEmpresa());
        prov.setCategoriaRepuestos(dto.getCategoriaRepuestos());
        prov.setTiempoPromedioEntrega(dto.getTiempoPromedioEntrega());
        prov.setReferencia(ref);

        return convertirADTO(proveedorRepository.save(prov));
    }

    @Override
    public void eliminarProveedor(Integer id) {
        proveedorRepository.deleteById(id);
    }

    private ProveedorDTO convertirADTO(Proveedor p) {
        return ProveedorDTO.builder()
                .idProveedor(p.getIdProveedor())
                .nombreEmpresa(p.getNombreEmpresa())
                .categoriaRepuestos(p.getCategoriaRepuestos())
                .tiempoPromedioEntrega(p.getTiempoPromedioEntrega())
                .idReferencia(p.getReferencia() != null ? p.getReferencia().getId() : null).build();
    }
}
