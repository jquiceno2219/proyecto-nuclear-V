package co.edu.unihumboldt.parking.config;

import co.edu.unihumboldt.parking.services.impl.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Clase {@code SecurityConfig} que configura la seguridad de la aplicación usando
 * Spring Security. Define un {@code SecurityFilterChain} que establece reglas de
 * autorización para rutas de la API, permitiendo acceso público a ciertas rutas y
 * restringiendo otras según roles como {@code ADMIN} y {@code PARKING_MANAGER}.
 * Incluye un {@code DaoAuthenticationProvider} con un servicio de detalles de
 * usuario personalizado y un codificador de contraseñas sin encriptación.
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/users/list").permitAll()
                        // Rutas para ADMIN y PARKING_MANAGER
                        .requestMatchers(
                                "api/users/new",
                                "api/users/update/{id}",
                                "api/car-parks/list",
                                "api/car-parks/update/{id}"
                        ).hasAnyRole("ADMIN", "PARKING_MANAGER")

                        // Rutas exclusivas para ADMIN
                        .requestMatchers(
                                "api/users/status/{id}",
                                "api/car-parks/new",
                                "api/car-parks/status/{id}",
                                "api/books-status/list",
                                "api/books-status/new",
                                "api/books-status/update/{id}",
                                "api/books-status/status/{id}",
                                "api/pay-methods/list",
                                "api/pay-methods/new",
                                "api/pay-methods/update/{id}",
                                "api/pay-methods/status/{id}",
                                "api/vehicles-types/list"
                        ).hasRole("ADMIN")

                        // Rutas exclusivas para PARKING_MANAGER
                        .requestMatchers(
                                "api/days-schedules/list",
                                "api/days-schedules/new",
                                "api/spots/list",
                                "api/spots/new",
                                "api/spots/update/{id}",
                                "api/spots/status/{id}",
                                "api/fees/list",
                                "api/fees/new",
                                "api/fees/update/{id}",
                                "api/fees/status/{id}",
                                "api/add-services/list",
                                "api/add-services/new",
                                "api/add-services/update/{id}",
                                "api/add-services/status/{id}"

                        ).hasRole("PARKING_MANAGER")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}