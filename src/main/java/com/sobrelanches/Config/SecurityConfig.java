package com.sobrelanches.Config;

import com.sobrelanches.service.ClienteUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Admins em memória
    @Bean
    public InMemoryUserDetailsManager inMemoryUsers(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("1234"))
                .roles("ADMIN")
                .build();
        UserDetails admin2 = User.withUsername("admin2")
                .password(encoder.encode("4567"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin, admin2);
    }

    // AuthenticationManager com dois providers
    @Bean
    public AuthenticationManager authenticationManager(ClienteUserDetailsService clienteUserDetailsService,
                                                       PasswordEncoder passwordEncoder,
                                                       InMemoryUserDetailsManager inMemoryUsers) {
        DaoAuthenticationProvider clienteProvider = new DaoAuthenticationProvider();
        clienteProvider.setUserDetailsService(clienteUserDetailsService);
        clienteProvider.setPasswordEncoder(passwordEncoder);

        DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider();
        adminProvider.setUserDetailsService(inMemoryUsers);
        adminProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(Arrays.asList(clienteProvider, adminProvider));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/cliente/cadastro", "/cliente/esqueci-senha", "/h2-console/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll());

        return http.build();
    }
}
