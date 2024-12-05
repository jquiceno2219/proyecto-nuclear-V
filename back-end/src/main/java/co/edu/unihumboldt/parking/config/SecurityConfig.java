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
                        .requestMatchers(
                                "api/users/list",
                                "api/pay-methods/list",
                                "api/parking-facilities/list",
                                "api/parking-spots/list",
                                "api/reservations-status/list",
                                "api/reservations/list",
                                "api/days-schedules/list",
                                "api/days-week/list",
                                "api/schedules/list",
                                "api/vehicles-types/list",
                                "api/spots/list",
                                "api/service-reservations/list",
                                "api/fees/list",
                                "api/user_roles/list"
                        ).permitAll()
                        // Rutas para ADMIN y PARKING_MANAGER
                        .requestMatchers(
                                "api/users/new",
                                "api/users/update/{id}",
                                "api/parking-facilities/update/{id}",
                                "api/daily-schedules/new"

                        ).hasAnyRole("ADMIN", "PARKING_MANAGER")

                        // Rutas exclusivas para ADMIN
                        .requestMatchers(
                                "api/users/status/{id}",
                                "api/parking-facilities/new",
                                "api/parking-facilities/status/{id}",
                                "api/reservations-status/new",
                                "api/reservations-status/update/{id}",
                                "api/reservations-status/status/{id}",
                                "api/pay-methods/new",
                                "api/pay-methods/update/{id}",
                                "api/pay-methods/status/{id}"

                        ).hasRole("ADMIN")

                        // Rutas exclusivas para PARKING_MANAGER
                        .requestMatchers(
                                "api/spots/new",
                                "api/spots/update/{id}",
                                "api/spots/status/{id}",
                                "api/fees/new",
                                "api/fees/update/{id}",
                                "api/fees/status/{id}",
                                "api/service-reservations/new",
                                "api/service-reservations/update/{id}",
                                "api/service-reservations/status/{id}"

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
