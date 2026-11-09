package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.DTOS.AdministradorDTO;
import com.example.MonoStyle.Entity.Administrador;
import com.example.MonoStyle.Entity.Rol;
import com.example.MonoStyle.Repository.AdministradorRepository;
import com.example.MonoStyle.Repository.RolRepository;
import com.example.MonoStyle.Service.AdministradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdministradorServiceImpl implements AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AdministradorDTO> listarAdministradores() {
        return administradorRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public AdministradorDTO obtenerAdministradorPorId(Long id) {
        Administrador admin = administradorRepository.findById(id).orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
        return convertirADTO(admin);
    }

    @Override
    public AdministradorDTO crearAdministrador(AdministradorDTO dto) {
        Rol rol = rolRepository.findById(dto.getIdRol()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Administrador admin = Administrador.builder()
                .nombre(dto.getNombre())
                .credenciales(passwordEncoder.encode(dto.getCredenciales()))
                .rol(rol).build();

        return convertirADTO(administradorRepository.save(admin));
    }

    @Override
    public AdministradorDTO actualizarAdministrador(Long id, AdministradorDTO dto) {
        Administrador admin = administradorRepository.findById(id).orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
        Rol rol = rolRepository.findById(dto.getIdRol()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        admin.setNombre(dto.getNombre());
        admin.setRol(rol);

        // Si el usuario envía una nueva contraseña, se encripta y se actualiza
        if (dto.getCredenciales() != null && !dto.getCredenciales().isEmpty()) {
            admin.setCredenciales(passwordEncoder.encode(dto.getCredenciales()));
        }

        return convertirADTO(administradorRepository.save(admin));
    }

    @Override
    public void eliminarAdministrador(Long id) {
        administradorRepository.deleteById(id);
    }

    private AdministradorDTO convertirADTO(Administrador a) {
        return AdministradorDTO.builder().idAdministrador(a.getIdAdministrador()).nombre(a.getNombre())
                .idRol(a.getRol().getId()).build();
    }
}
