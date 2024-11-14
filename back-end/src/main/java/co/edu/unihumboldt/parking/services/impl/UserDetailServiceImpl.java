package co.edu.unihumboldt.parking.services.impl;

import co.edu.unihumboldt.parking.domain.entities.User;
import co.edu.unihumboldt.parking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
/**
 * La clase UserDetailServiceImpl implementa la interfaz UserDetailsService de Spring Security
 * y proporciona la funcionalidad para cargar detalles de usuario desde el sistema de usuarios.
 * Utiliza UserRepository para buscar un usuario por su nombre de usuario y su estado activo.
 * Si el usuario no se encuentra, lanza una excepción UsernameNotFoundException.
 * Al recuperar un usuario, devuelve una instancia de User de Spring Security, que incluye la
 * contraseña, el estado de la cuenta, y los roles asociados al usuario, permitiendo así la
 * autenticación y autorización en la aplicación.
 */

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameAndStatus(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(), // La contraseña se usa directamente sin encriptación
                user.isStatus(), // Activo/Inactivo
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().getName()))
        );
    }
}
