package com.sobrelanches.service;

import com.sobrelanches.model.Cliente;
import com.sobrelanches.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String normalizedEmail = email == null ? "" : email.toLowerCase();

        Cliente cliente = clienteRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UsernameNotFoundException("EMAIL não encontrado: " + email));

        return User.builder()
                .username(cliente.getEmail())
                .password(cliente.getSenha()) // senha deve estar codificada com BCrypt
                .roles("CLIENTE")
                .build();
    }
}
