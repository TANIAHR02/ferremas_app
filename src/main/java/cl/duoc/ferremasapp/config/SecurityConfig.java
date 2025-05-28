package cl.duoc.ferremasapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .antMatchers("/api/productos/**", "/api/auth/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .antMatchers("/api/pedidos/**", "/api/pagos/**", "/api/mensajes/**").authenticated()
                .antMatchers("/api/usuarios/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin()
            .defaultSuccessUrl("/api/auth/success", true)
            .and()
            .logout()
            .logoutSuccessUrl("/api/auth/logout-success")
            .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
