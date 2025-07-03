package cl.duoc.ferremasapp.config;

import cl.duoc.ferremasapp.model.Usuario;
import cl.duoc.ferremasapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas - Swagger y documentación
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/api-docs/**", "/openapi/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                
                // Rutas públicas - Páginas web
                .requestMatchers("/", "/home", "/login", "/registro", "/productos", "/quienes-somos", "/carrito", "/perfil", "/tipos-pago").permitAll()
                .requestMatchers("/static/**", "/css/**", "/js/**", "/img/**").permitAll()
                
                // Rutas públicas - APIs
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/banco-central/**").permitAll()
                .requestMatchers("/api/webpay/**").permitAll()
                .requestMatchers("/api/webpay-simulation/**").permitAll()
                .requestMatchers("/api/carrito/**").permitAll()
                
                // Rutas por roles específicos
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/vendedor/**").hasRole("VENDEDOR")
                .requestMatchers("/api/bodeguero/**").hasRole("BODEGUERO")
                .requestMatchers("/api/contador/**").hasRole("CONTADOR")
                .requestMatchers("/api/cliente/**").hasRole("CLIENTE")
                
                // Rutas de gestión de productos (solo admin y vendedor)
                .requestMatchers("/api/productos/**").hasAnyRole("ADMIN", "VENDEDOR")
                
                // Rutas que requieren autenticación
                .requestMatchers("/api/pedidos/**").authenticated()
                .requestMatchers("/api/pagos/**").authenticated()
                
                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Autowired
            private UsuarioService usuarioService;
            
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Usuario usuario = usuarioService.buscarPorEmail(username);
                if (usuario == null) {
                    throw new UsernameNotFoundException("Usuario no encontrado: " + username);
                }
                
                return User.builder()
                        .username(usuario.getEmail())
                        .password(usuario.getPassword())
                        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre())))
                        .build();
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
