package com.example.tovar_hisob_kitobi_mvc.base.common;

import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodSummaByCreatedByDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodSummaDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSummaByCreatedBy;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodSummaByCreatedByDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodSummaDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSummaByCreatedBy;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratSummaByCreatedByDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratSummaDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.projection.VozvratSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.projection.VozvratSummaByCreatedBy;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WebSocketResponse<T> {
    private T data;
    private BigDecimal summa;
    private static final ObjectMapper objectMapper=new ObjectMapper();

    @SneakyThrows
    public static String ofPrixod(List<PrixodSummaDTO> data){
        BigDecimal summa = data.stream().map(PrixodSummaDTO::sum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return objectMapper.writeValueAsString(new WebSocketResponse<>(data, summa));
    }

    @SneakyThrows
    public static String ofPrixodCreated(List<PrixodSummaByCreatedByDTO> data){
        BigDecimal summa = data.stream().map(PrixodSummaByCreatedByDTO::sum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return objectMapper.writeValueAsString(new WebSocketResponse<>(data, summa));
    }

    @SneakyThrows
    public static String ofRasxod(List<RasxodSummaDTO> data){
        BigDecimal summa = data.stream().map(RasxodSummaDTO::sum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return objectMapper.writeValueAsString(new WebSocketResponse<>(data, summa));
    }

    @SneakyThrows
    public static String ofRasxodCreated(List<RasxodSummaByCreatedByDTO> data){
        BigDecimal summa = data.stream().map(RasxodSummaByCreatedByDTO::sum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return objectMapper.writeValueAsString(new WebSocketResponse<>(data, summa));
    }

    @SneakyThrows
    public static String ofVozvrat(List<VozvratSummaDTO> data){
        BigDecimal summa = data.stream().map(VozvratSummaDTO::sum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return objectMapper.writeValueAsString(new WebSocketResponse<>(data, summa));
    }

    @SneakyThrows
    public static String ofVozvratCreated(List<VozvratSummaByCreatedByDTO> data){
        BigDecimal summa = data.stream().map(VozvratSummaByCreatedByDTO::sum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return objectMapper.writeValueAsString(new WebSocketResponse<>(data, summa));
    }
}
