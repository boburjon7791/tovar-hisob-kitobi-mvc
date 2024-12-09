package com.example.tovar_hisob_kitobi_mvc.base.common;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntity;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository.PrixodTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository.PrixodTovarRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository.RasxodTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository.RasxodTovarRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository.VozvratTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository.VozvratTovarRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CronJobs {
    private final PrixodTovarRepository prixodTovarRepository;
    private final PrixodTovarDetailRepository prixodTovarDetailRepository;
    private final RasxodTovarRepository rasxodTovarRepository;
    private final RasxodTovarDetailRepository rasxodTovarDetailRepository;
    private final VozvratTovarRepository vozvratTovarRepository;
    private final VozvratTovarDetailRepository vozvratTovarDetailRepository;

    @Scheduled(cron = "0 * * * * *")
    public void deleteUnconfirmedTransactions(){
        prixodTovarRepository.findAll((root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get(PrixodTovar._tasdiqlandi), false), criteriaBuilder.lessThan(root.get(BaseEntity._updatedAt), LocalDateTime.now().minusMinutes(30)))).forEach(prixodTovar -> {
            prixodTovarDetailRepository.findAllByPrixodTovarId(prixodTovar.getId()).forEach(prixodTovarDetailRepository::delete);
            prixodTovarRepository.delete(prixodTovar);
        });
        rasxodTovarRepository.findAll((root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get(RasxodTovar._tasdiqlandi), false), criteriaBuilder.lessThan(root.get(BaseEntity._updatedAt), LocalDateTime.now().minusMinutes(30)))).forEach(prixodTovar -> {
            rasxodTovarDetailRepository.findAllByRasxodTovarId(prixodTovar.getId()).forEach(rasxodTovarDetailRepository::delete);
            rasxodTovarRepository.delete(prixodTovar);
        });
        vozvratTovarRepository.findAll((root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get(VozvratTovar._tasdiqlandi),false), criteriaBuilder.lessThan(root.get(BaseEntity._updatedAt), LocalDateTime.now().minusMinutes(30)))).forEach(prixodTovar -> {
            vozvratTovarDetailRepository.findAllByVozvratTovarId(prixodTovar.getId()).forEach(vozvratTovarDetailRepository::delete);
            vozvratTovarRepository.delete(prixodTovar);
        });
    }
}
