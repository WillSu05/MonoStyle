package com.example.MonoStyle.Repository;
import com.example.MonoStyle.Entity.EstadoDisponibilidadEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoDisponibilidadEmpleadoRepository extends JpaRepository<EstadoDisponibilidadEmpleado, Long> {
}
