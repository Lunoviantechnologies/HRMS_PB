package com.example.AdmineEntity;

public class AdmineMapper {

    public static AdmineEntity toEntity(AdmineDTO dto) {
        AdmineEntity entity = new AdmineEntity();
        entity.setFirst_name(dto.getFirstName());
        entity.setLast_name(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());

        // If confirmPassword is null or blank, default to password
        String confirmPassword = dto.getConfirmPassword();
        entity.setConfirm_password((confirmPassword != null && !confirmPassword.isBlank())
                ? confirmPassword
                : dto.getPassword());

        // Hardcoding role
        entity.setRole("admin");

        return entity;
    }
}