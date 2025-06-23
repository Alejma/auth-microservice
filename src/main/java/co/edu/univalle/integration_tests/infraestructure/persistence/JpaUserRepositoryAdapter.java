package co.edu.univalle.integration_tests.infraestructure.persistence;

import co.edu.univalle.integration_tests.domain.models.AuthUser;
import co.edu.univalle.integration_tests.domain.ports.UserRepositoryPort;
import co.edu.univalle.integration_tests.infraestructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository; // Cambia esto

    @Override
    public Optional<AuthUser> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(user -> new AuthUser(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole(),
                        user.getAccessLevel(
                )));

    }

}