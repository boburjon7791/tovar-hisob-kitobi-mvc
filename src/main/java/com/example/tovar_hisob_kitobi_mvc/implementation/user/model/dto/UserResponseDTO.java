package com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto;

import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.enums.Lavozim;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record UserResponseDTO(
        Long id,
        String createdAt,
        String updatedAt,
        Long createdById,
        Long updatedById,
        String creaetdByFullName,
        String updatedByFullName,
        String ism,
        String familya,
        String login,
        String telefonRaqam,
        Lavozim lavozim,
        List<Lavozim> lavozimlar
) {
    public UserResponseDTO{
        List<Lavozim> lavozimlarArray = Arrays.stream(Lavozim.values()).collect(Collectors.toList());
        lavozimlarArray.removeIf(l -> l.equals(lavozim));
        lavozimlarArray.addFirst(lavozim);
        lavozimlar=lavozimlarArray;
    }
}
