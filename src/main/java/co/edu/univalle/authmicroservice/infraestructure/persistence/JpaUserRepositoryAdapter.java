package co.edu.univalle.authmicroservice.infraestructure.persistence;

import co.edu.univalle.authmicroservice.domain.models.AuthUser;
import co.edu.univalle.authmicroservice.domain.ports.UserRepositoryPort;
import co.edu.univalle.authmicroservice.infraestructure.UserJpaRepository;
import co.edu.univalle.authmicroservice.infraestructure.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.Optional;
import java.util.UUID;

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