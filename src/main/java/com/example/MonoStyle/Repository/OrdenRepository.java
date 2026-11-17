package com.example.MonoStyle.Repository;
import com.example.MonoStyle.Entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByCliente_IdClienteOrderByIdOrdenDesc(Long idCliente);
}