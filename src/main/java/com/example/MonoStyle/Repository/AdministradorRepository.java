package com.example.MonoStyle.Repository;
import com.example.MonoStyle.Entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    // Esto será útil más adelante para el Login con Spring Security
    // Optional<Administrador> findByNombre(String nombre);
}
