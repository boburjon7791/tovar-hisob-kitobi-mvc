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
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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
    private static final String loggingFolder="logging_folder";

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void deleteUnconfirmedTransactions(){
        LocalDateTime lastUpdatedAt=LocalDateTime.now().minusMinutes(30);
        prixodTovarRepository.findAllByUpdatedAtLessThanAndTasdiqlandiFalse(lastUpdatedAt).forEach(prixodTovar -> {
            prixodTovarDetailRepository.findAllByPrixodTovarId(prixodTovar.getId(), Sort.unsorted()).forEach(prixodTovarDetailRepository::delete);
            prixodTovarRepository.delete(prixodTovar);
        });
        rasxodTovarRepository.findAllByUpdatedAtLessThanAndTasdiqlandiFalse(lastUpdatedAt).forEach(rasxodTovar -> {
            rasxodTovarDetailRepository.findAllByRasxodTovarId(rasxodTovar.getId(), Sort.unsorted()).forEach(rasxodTovarDetailRepository::delete);
            rasxodTovarRepository.delete(rasxodTovar);
        });
        vozvratTovarRepository.findAllByUpdatedAtLessThanAndTasdiqlandiFalse(lastUpdatedAt).forEach(vozvratTovar -> {
            vozvratTovarDetailRepository.findAllByVozvratTovarId(vozvratTovar.getId(), Sort.unsorted()).forEach(vozvratTovarDetailRepository::delete);
            vozvratTovarRepository.delete(vozvratTovar);
        });
    }

    @Transactional
    @Scheduled(cron = "40 * * * * *")
    public void deleteOldLogs(){
        String localDate = LocalDate.now().minusMonths(1).toString();
        for (File file : Objects.requireNonNullElse(new File(loggingFolder).listFiles(),new File[]{})) {
            if (file.getName().contains(localDate)) {
                System.out.println("file.delete() = " + file.delete());
            }
        }
    }
}
