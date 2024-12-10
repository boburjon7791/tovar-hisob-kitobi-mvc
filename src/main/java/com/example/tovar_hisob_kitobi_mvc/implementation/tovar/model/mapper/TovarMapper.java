package com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.mapper;

import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.model.mapper.BaseMapper;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.dto.TovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.dto.TovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class TovarMapper implements BaseMapper<Tovar, TovarRequestDTO,TovarResponseDTO> {
    @Override
    public void update(Tovar tovar, TovarRequestDTO tovarRequestDTO){
        tovar.setNomi(tovarRequestDTO.nomi());
        tovar.setOlchovBirligi(tovarRequestDTO.olchovBirligi());
        tovar.setIzoh(tovarRequestDTO.izoh());
        tovar.setPrixodSumma(tovarRequestDTO.prixodSumma());
        tovar.setRasxodSumma(tovarRequestDTO.rasxodSumma());
    }

    @Override
    public TovarResponseDTO toDto(Tovar tovar){
        return new TovarResponseDTO(tovar.getId(), Utils.formatDate(tovar.getCreatedAt()), Utils.formatDate(tovar.getUpdatedAt()), tovar.getCreatedBy(), tovar.getUpdatedBy(), null, null, tovar.getOstatkasi(), tovar.getRasmi(), tovar.getPrixodSumma(), tovar.getRasxodSumma(), tovar.getNomi(), tovar.getShtrixKod(), tovar.getOlchovBirligi());
    }

    @Override
    public Tovar toEntity(TovarRequestDTO tovarRequestDTO){
        return new Tovar(tovarRequestDTO.nomi(), BigDecimal.ZERO, null, tovarRequestDTO.shtrixKod(), tovarRequestDTO.prixodSumma(), tovarRequestDTO.rasxodSumma(),tovarRequestDTO.olchovBirligi());
    }
}
