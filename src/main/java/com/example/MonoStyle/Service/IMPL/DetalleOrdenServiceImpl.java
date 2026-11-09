package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.DetalleOrdenDTO;
import com.example.MonoStyle.Entity.DetalleOrden;
import com.example.MonoStyle.Entity.Orden;
import com.example.MonoStyle.Entity.Referencia;
import com.example.MonoStyle.Repository.DetalleOrdenRepository;
import com.example.MonoStyle.Repository.OrdenRepository;
import com.example.MonoStyle.Repository.ReferenciaRepository;
import com.example.MonoStyle.Service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private ReferenciaRepository referenciaRepository;

    @Override
    public List<DetalleOrdenDTO> listarDetallesPorOrden(Long idOrden) {
        return detalleOrdenRepository.findByOrdenIdOrden(idOrden).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public DetalleOrdenDTO obtenerDetallePorId(Long idDetalleOrden) {
        DetalleOrden detalle = detalleOrdenRepository.findById(idDetalleOrden)
                .orElseThrow(() -> new RuntimeException("Detalle de orden no encontrado"));
        return convertirADTO(detalle);
    }

    @Override
    @Transactional // Garantiza que si falla la actualización del stock, no se guarde el detalle
    public DetalleOrdenDTO agregarDetalle(DetalleOrdenDTO dto) {
        Orden orden = ordenRepository.findById(dto.getIdOrden())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        Referencia referencia = referenciaRepository.findById(dto.getIdReferencia())
                .orElseThrow(() -> new RuntimeException("Referencia/Repuesto no encontrado"));

        // 1. Validar que exista suficiente stock
        if (referencia.getStock() < dto.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para el repuesto: " + referencia.getDescripcion() +
                    ". Stock actual: " + referencia.getStock());
        }

        // 2. Descontar el stock del inventario
        referencia.setStock(referencia.getStock() - dto.getCantidad());
        referenciaRepository.save(referencia);

        // 3. Crear el detalle con el precio actual congelado
        DetalleOrden detalle = DetalleOrden.builder()
                .orden(orden)
                .referencia(referencia)
                .cantidad(dto.getCantidad())// Se guarda el precio histórico vigente
                .build();

        return convertirADTO(detalleOrdenRepository.save(detalle));
    }

    @Override
    @Transactional
    public DetalleOrdenDTO actualizarCantidad(Long idDetalleOrden, Integer nuevaCantidad) {
        DetalleOrden detalle = detalleOrdenRepository.findById(idDetalleOrden)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        Referencia referencia = detalle.getReferencia();

        // Calcular la diferencia (Ej: si había 2 y ahora son 5, la diferencia es +3, hay que restar 3 al stock)
        double diferencia = nuevaCantidad - detalle.getCantidad();

        if (diferencia > 0 && referencia.getStock() < diferencia) {
            throw new RuntimeException("Stock insuficiente para agregar " + diferencia + " unidades más.");
        }

        // Ajustar el stock (si la diferencia es negativa, el stock aumenta automáticamente al restar un negativo)
        referencia.setStock(referencia.getStock() - diferencia);
        referenciaRepository.save(referencia);

        // Actualizar el detalle
        detalle.setCantidad(Double.valueOf(nuevaCantidad));
        return convertirADTO(detalleOrdenRepository.save(detalle));
    }

    @Override
    @Transactional
    public void eliminarDetalle(Long idDetalleOrden) {
        DetalleOrden detalle = detalleOrdenRepository.findById(idDetalleOrden)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        Referencia referencia = detalle.getReferencia();

        // Al eliminar el repuesto de la orden, se devuelve la cantidad al stock del inventario
        referencia.setStock(referencia.getStock() + detalle.getCantidad());
        referenciaRepository.save(referencia);

        detalleOrdenRepository.deleteById(idDetalleOrden);
    }

    // Método auxiliar de mapeo Entity -> DTO
    private DetalleOrdenDTO convertirADTO(DetalleOrden d) {
        return DetalleOrdenDTO.builder()
                .idDetalleOrden(d.getIdDetalleOrden())
                .idOrden(d.getOrden().getIdOrden())
                .idReferencia(d.getReferencia().getId())
                .cantidad(d.getCantidad())
                .precioUnitario(d.getPrecio())
                .build();
    }
}
