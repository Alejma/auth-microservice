package co.edu.univalle.integration_tests.application.service;

import co.edu.univalle.integration_tests.application.dto.AuthRequest;
import co.edu.univalle.integration_tests.application.dto.AuthResponse;
import co.edu.univalle.integration_tests.application.dto.RefreshTokenRequest;
import co.edu.univalle.integration_tests.domain.exceptions.InvalidTokenException;
import co.edu.univalle.integration_tests.domain.models.AuthUser;
import co.edu.univalle.integration_tests.domain.ports.UserRepositoryPort;
import co.edu.univalle.integration_tests.infraestructure.security.JwtTokenService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import co.edu.univalle.integration_tests.domain.exceptions.InvalidCredentialsException;


import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepositoryPort userRepository;
    private final JwtTokenService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(AuthRequest request) {
        try {
            // 1. Autenticar credenciales
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            // 2. Obtener usuario
            AuthUser user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new InvalidCredentialsException("Usuario no encontrado"));

            // 3. Verificar contraseña (opcional, adicional al authenticationManager)
            if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                throw new InvalidCredentialsException("Contraseña incorrecta");
            }

            // 4. Generar tokens
            String accessToken = jwtService.generateToken(
                    new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getPassword(),
                            Collections.singleton(new SimpleGrantedAuthority(user.getRole().toAuthority()))
                    )
            );

            String refreshToken = jwtService.generateRefreshToken((UserDetails) user);

            return new AuthResponse(accessToken, refreshToken);

        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }
    }
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            // 1. Extraer el email del refresh token
            String userEmail = jwtService.extractUsername(refreshTokenRequest.refreshToken());

            // 2. Verificar que el usuario existe
            if (userEmail != null) {
                UserDetails userDetails = (UserDetails) userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

                // 3. Validar el refresh token
                if (jwtService.isTokenValid(refreshTokenRequest.refreshToken(), userDetails)) {
                    // 4. Generar nuevo access token
                    String newAccessToken = jwtService.generateToken(userDetails);
                    return new AuthResponse(newAccessToken, refreshTokenRequest.refreshToken());
                }
            }
            throw new InvalidTokenException("Refresh token inválido");
        } catch (JwtException e) {
            throw new InvalidTokenException("Refresh token inválido o expirado");
        }
    }
}
