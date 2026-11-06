package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.OrdenCompraDTO;
import com.example.MonoStyle.Entity.OrdenCompra;
import com.example.MonoStyle.Entity.Proveedor;
import com.example.MonoStyle.Repository.OrdenCompraRepository;
import com.example.MonoStyle.Repository.ProveedorRepository;
import com.example.MonoStyle.Service.OrdenCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenCompraServiceImpl implements OrdenCompraService {

    private  final OrdenCompraRepository ordenCompraRepository;
    private final ProveedorRepository proveedorRepository;

    @Override
    public List<OrdenCompraDTO> listarOrdenesCompra() {
        return ordenCompraRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public OrdenCompraDTO obtenerOrdenCompraPorId(Long id) {
        OrdenCompra oc = ordenCompraRepository.findById(id).orElseThrow(() -> new RuntimeException("Orden de Compra no encontrada"));
        return convertirADTO(oc);
    }

    @Override
    public OrdenCompraDTO crearOrdenCompra(OrdenCompraDTO dto) {
        Proveedor prov = proveedorRepository.findById(dto.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        OrdenCompra oc = OrdenCompra.builder().descripcion(dto.getDescripcion()).proveedor(prov).build();
        return convertirADTO(ordenCompraRepository.save(oc));
    }

    @Override
    public OrdenCompraDTO actualizarOrdenCompra(Long id, OrdenCompraDTO dto) {
        OrdenCompra oc = ordenCompraRepository.findById(id).orElseThrow(() -> new RuntimeException("Orden de Compra no encontrada"));
        Proveedor prov = proveedorRepository.findById(dto.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        oc.setDescripcion(dto.getDescripcion());
        oc.setProveedor(prov);
        return convertirADTO(ordenCompraRepository.save(oc));
    }

    @Override
    public void eliminarOrdenCompra(Long id) {
        ordenCompraRepository.deleteById(id);
    }

    private OrdenCompraDTO convertirADTO(OrdenCompra o) {
        return OrdenCompraDTO.builder()
                .idOrdenCompra(o.getIdOrdenCompra())
                .descripcion(o.getDescripcion())
                .idProveedor(o.getProveedor().getIdProveedor()).build();
    }
}
