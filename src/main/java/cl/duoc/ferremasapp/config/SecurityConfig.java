package cl.duoc.ferremasapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // DESARROLLO: Permitir todo
                .anyRequest().permitAll()
                
                // PRODUCCIÓN: Configuración segura (descomenta cuando estés listo)
                /*
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/api/test/**").permitAll() // Solo para testing
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
                */
            )
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());
            
            // PRODUCCIÓN: Habilitar autenticación (descomenta cuando estés listo)
            // .formLogin(Customizer.withDefaults())
            // .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
