package co.edu.univalle.authmicroservice.domain.ports;

import co.edu.univalle.authmicroservice.domain.models.AuthUser;
import java.util.Optional;

public interface UserRepositoryPort  {
    Optional<AuthUser> findByEmail(String email);
}
