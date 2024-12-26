package org.example.enums;

public enum Role {
    ADMIN("admin"),
    PROFESSEUR("professeur"),
    ETUDIANT("etudiant");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
