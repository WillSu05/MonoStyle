package com.example.MonoStyle.Repository;


import com.example.MonoStyle.Entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByCliente_IdClienteOrderByIdCitaDesc(Long idCliente);
}
