package co.edu.univalle.integration_tests.domain.models;

public enum Role {
    CLIENT,
    DELIVERER,
    ADMIN;

    public String toAuthority() {
        return "ROLE_" + this.name();
    }
}
