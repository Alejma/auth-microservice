package co.edu.univalle.integration_tests.domain.ports;

import co.edu.univalle.integration_tests.domain.models.AuthUser;
import java.util.Optional;

public interface UserRepositoryPort  {
    Optional<AuthUser> findByEmail(String email);
}
