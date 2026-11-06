package com.example.MonoStyle.Repository;
import com.example.MonoStyle.Entity.Motocicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotocicletaRepository extends JpaRepository<Motocicleta, Long> {
    // Ejemplo de método de búsqueda personalizado que Spring Data JPA resolverá solo:
    // Optional<Motocicleta> findByPlaca(String placa);

}
