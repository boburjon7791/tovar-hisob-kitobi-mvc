package com.example.tovar_hisob_kitobi_mvc.base.common;

import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSummaByCreatedBy;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSummaByCreatedBy;
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
    public static String ofPrixod(List<PrixodSumma> data){
        BigDecimal summa = data.stream().map(PrixodSumma::getSum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return objectMapper.writeValueAsString(new WebSocketResponse<>(data, summa));
    }

    @SneakyThrows
    public static String ofPrixodCreated(List<PrixodSummaByCreatedBy> data){
        BigDecimal summa = data.stream().map(PrixodSummaByCreatedBy::getSum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return objectMapper.writeValueAsString(new WebSocketResponse<>(data, summa));
    }

    @SneakyThrows
    public static String ofRasxod(List<RasxodSumma> data){
        BigDecimal summa = data.stream().map(RasxodSumma::getSum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return objectMapper.writeValueAsString(new WebSocketResponse<>(data, summa));
    }

    @SneakyThrows
    public static String ofRasxodCreated(List<RasxodSummaByCreatedBy> data){
        BigDecimal summa = data.stream().map(RasxodSummaByCreatedBy::getSum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return objectMapper.writeValueAsString(new WebSocketResponse<>(data, summa));
    }

    @SneakyThrows
    public static String ofVozvrat(List<VozvratSumma> data){
        BigDecimal summa = data.stream().map(VozvratSumma::getSum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return objectMapper.writeValueAsString(new WebSocketResponse<>(data, summa));
    }

    public static WebSocketResponse<List<VozvratSummaByCreatedBy>> ofVozvratCreated(List<VozvratSummaByCreatedBy> data){
        BigDecimal summa = data.stream().map(VozvratSummaByCreatedBy::getSum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return new WebSocketResponse<>(data, summa);
    }
}
