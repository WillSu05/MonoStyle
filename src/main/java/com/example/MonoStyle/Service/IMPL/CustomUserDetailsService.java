package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.Entity.Administrador;
import com.example.MonoStyle.Entity.Cliente;
import com.example.MonoStyle.Entity.Empleado;
import com.example.MonoStyle.Repository.AdministradorRepository;
import com.example.MonoStyle.Repository.ClienteRepository;
import com.example.MonoStyle.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private AdministradorRepository adminRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private EmpleadoRepository empleadoRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Buscar en Administrador
        Optional<Administrador> admin = adminRepo.findByNombre(username);
        if (admin.isPresent()) {
            return new User(admin.get().getNombre(), admin.get().getCredenciales(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        // 2. Buscar en Empleado
        Optional<Empleado> empleado = empleadoRepo.findByNombre(username);
        if (empleado.isPresent()) {
            return new User(empleado.get().getNombre(), empleado.get().getCredenciales(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLEADO")));
        }

        // 3. Buscar en Cliente
        Optional<Cliente> cliente = clienteRepo.findByNombre(username);
        if (cliente.isPresent()) {
            return new User(cliente.get().getNombre(), cliente.get().getCredenciales(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE")));
        }

        throw new UsernameNotFoundException("Usuario no encontrado en ninguna tabla: " + username);
    }
}
