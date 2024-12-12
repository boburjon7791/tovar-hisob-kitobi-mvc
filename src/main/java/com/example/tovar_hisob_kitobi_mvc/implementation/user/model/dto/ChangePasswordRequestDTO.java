package com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordRequestDTO(@NotNull Long id, String oldPswd, @NotBlank String newPswd, String confirmPswd) {
}
