package com.example.MonoStyle.Repository;
import com.example.MonoStyle.Entity.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
    // Permite obtener todos los repuestos asignados a una orden específica
    List<DetalleOrden> findByOrdenIdOrden(Long idOrden);
}
