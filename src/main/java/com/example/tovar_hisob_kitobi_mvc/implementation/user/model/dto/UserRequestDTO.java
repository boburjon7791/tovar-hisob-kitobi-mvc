package com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto;

import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.enums.Lavozim;

public record UserRequestDTO(
        Long id,
        String ism,
        String familya,
        String login,
        String parol,
        Lavozim lavozim,
        String telefonRaqam
) {
}
