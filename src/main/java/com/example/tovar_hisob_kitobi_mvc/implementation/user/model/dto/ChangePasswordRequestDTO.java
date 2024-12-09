package com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto;

public record ChangePasswordRequestDTO(String oldPswd, String newPswd, String confirmPswd) {
}
