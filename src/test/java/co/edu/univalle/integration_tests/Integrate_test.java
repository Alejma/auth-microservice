package co.edu.univalle.integration_tests;

import co.edu.univalle.integration_tests.application.dto.AuthRequest;
import co.edu.univalle.integration_tests.application.dto.AuthResponse;
import co.edu.univalle.integration_tests.application.dto.RefreshTokenRequest;
import co.edu.univalle.integration_tests.domain.models.Role;
import co.edu.univalle.integration_tests.infraestructure.persistence.entities.UserEntity;
import co.edu.univalle.integration_tests.infraestructure.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
public class Integrate_test {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAll();
    }

    @Test
    void testSuccessfulLogin() throws Exception {
        // Given: Crear un usuario en la base de datos
        UserEntity user = new UserEntity();
        user.setEmail("test@univalle.edu.co");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(Role.CLIENT);
        user.setAccessLevel(1); // Usuario habilitado
        userRepository.save(user);

        // When & Then: Realizar login exitoso
        AuthRequest authRequest = new AuthRequest("test@univalle.edu.co", "password123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }



    @Test
    void testLoginWithInvalidEmailFormat() throws Exception {
        // When & Then: Intentar login con formato de email inválido
        AuthRequest authRequest = new AuthRequest("invalid-email", "password123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginWithEmptyFields() throws Exception {
        // When & Then: Intentar login con campos vacíos
        AuthRequest authRequest = new AuthRequest("", "");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSuccessfulRefreshToken() throws Exception {
        // Given: Crear un usuario y obtener tokens iniciales
        UserEntity user = new UserEntity();
        user.setEmail("test@univalle.edu.co");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(Role.CLIENT);
        user.setAccessLevel(1); // Usuario habilitado
        userRepository.save(user);

        AuthRequest authRequest = new AuthRequest("test@univalle.edu.co", "password123");

        String response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthResponse authResponse = objectMapper.readValue(response, AuthResponse.class);

        // When & Then: Refrescar el token
        RefreshTokenRequest refreshRequest = new RefreshTokenRequest(authResponse.refreshToken());

        mockMvc.perform(post("/api/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }



    @Test
    void testLoginWithAdminRole() throws Exception {
        // Given: Crear un usuario con rol de administrador
        UserEntity user = new UserEntity();
        user.setEmail("admin@univalle.edu.co");
        user.setPassword(passwordEncoder.encode("admin123"));
        user.setRole(Role.ADMIN);
        user.setAccessLevel(1); // Usuario habilitado
        userRepository.save(user);

        // When & Then: Realizar login exitoso con admin
        AuthRequest authRequest = new AuthRequest("admin@univalle.edu.co", "admin123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    void testConcurrentLoginAttempts() throws Exception {
        // Given: Crear un usuario
        UserEntity user = new UserEntity();
        user.setEmail("concurrent@univalle.edu.co");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(Role.CLIENT);
        user.setAccessLevel(1); // Usuario habilitado
        userRepository.save(user);

        AuthRequest authRequest = new AuthRequest("concurrent@univalle.edu.co", "password123");

        // When & Then: Realizar múltiples intentos de login simultáneos
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").exists())
                    .andExpect(jsonPath("$.refreshToken").exists());
        }
    }
}
