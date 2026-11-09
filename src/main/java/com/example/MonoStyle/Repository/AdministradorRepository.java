package com.example.MonoStyle.Repository;
import com.example.MonoStyle.Entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByNombre(String nombre); // Método clave para el login
}
