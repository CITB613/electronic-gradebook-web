package bg.nbu.gradebook.commons.constants;

public enum RoleConstants {
    ROLE_ADMIN("ROLE_ADMIN"), ROLE_PRINCIPAL("ROLE_PRINCIPAL"), ROLE_TEACHER("ROLE_TEACHER"),
    ROLE_PARENT("ROLE_PARENT"), ROLE_STUDENT("ROLE_STUDENT"), ADMIN("ADMIN"), PRINCIPAL("PRINCIPAL"),
    TEACHER("TEACHER"), PARENT("PARENT"), STUDENT("STUDENT");

    private final String role;

    private RoleConstants(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}