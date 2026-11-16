package com.example.MonoStyle.Repository;
import com.example.MonoStyle.Entity.Motocicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MotocicletaRepository extends JpaRepository<Motocicleta, Long> {
    List<Motocicleta> findByCliente_IdCliente(Long idCliente);
    boolean existsByPlacaIgnoreCase(String placa);
}
