package co.edu.univalle.authmicroservice.domain.models;

public enum Role {
    CLIENT,
    DELIVERER,
    ADMIN;

    public String toAuthority() {
        return "ROLE_" + this.name();
    }
}
