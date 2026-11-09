package com.example.MonoStyle.Service.IMPL;
import com.example.MonoStyle.Entity.Administrador;
import com.example.MonoStyle.Repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Administrador admin = administradorRepository.findByNombre(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));


        return new User(admin.getNombre(), admin.getCredenciales(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + admin.getRol().getDescripcion())));
    }
}
