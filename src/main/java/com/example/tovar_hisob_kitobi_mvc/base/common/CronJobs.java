package com.example.tovar_hisob_kitobi_mvc.base.common;

import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository.PrixodTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository.PrixodTovarRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository.RasxodTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository.RasxodTovarRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository.VozvratTovarDetailRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository.VozvratTovarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Slf4j
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

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    private static final String BACKUP_FOLDER="db-backup-files";

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void deleteUnconfirmedTransactions(){
        LocalDateTime lastUpdatedAt=LocalDateTime.now().minusMinutes(1);
        prixodTovarRepository.findAllByUpdatedAtLessThanAndTasdiqlandiFalse(lastUpdatedAt).forEach(prixodTovar -> {
            prixodTovarDetailRepository.findAllByPrixodTovarId(prixodTovar.getId()).forEach(prixodTovarDetailRepository::delete);
            prixodTovarRepository.delete(prixodTovar);
        });
        rasxodTovarRepository.findAllByUpdatedAtLessThanAndTasdiqlandiFalse(lastUpdatedAt).forEach(rasxodTovar -> {
            rasxodTovarDetailRepository.findAllByRasxodTovarId(rasxodTovar.getId()).forEach(rasxodTovarDetailRepository::delete);
            rasxodTovarRepository.delete(rasxodTovar);
        });
        vozvratTovarRepository.findAllByUpdatedAtLessThanAndTasdiqlandiFalse(lastUpdatedAt).forEach(vozvratTovar -> {
            vozvratTovarDetailRepository.findAllByVozvratTovarId(vozvratTovar.getId()).forEach(vozvratTovarDetailRepository::delete);
            vozvratTovarRepository.delete(vozvratTovar);
        });
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteOldLogs(){
        String localDate = LocalDate.now().minusMonths(1).toString();
        for (File file : Objects.requireNonNullElse(new File(loggingFolder).listFiles(),new File[]{})) {
            if (file.getName().contains(localDate)) {
                System.out.println("file.delete() = " + file.delete());
            }
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void backupDatabase(){
        try {
            File backupFilesFolder = new File(BACKUP_FOLDER);
            if (!backupFilesFolder.exists()) {
                System.out.println("backupFilesFolder.mkdirs() = " + backupFilesFolder.mkdirs());
            }
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String databaseName = databaseUrl.substring(databaseUrl.lastIndexOf("/") + 1);
            String backupFile = BACKUP_FOLDER + "/" + databaseName + "-" + date + ".dump";

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "pg_dump", "-U", databaseUsername, "-F", "c", "-d", databaseName, "-f", backupFile);
            processBuilder.environment().put("PGPASSWORD",databasePassword);
            System.out.println("databaseName = " + databaseName);
            System.out.println("databaseUsername = " + databaseUsername);
            System.out.println("databasePassword = " + databasePassword);
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();

            if (process.exitValue()==0) {
                log.info("db backup was written : {}",backupFile);
                cleanOldBackups(backupFilesFolder);
            }else {
                log.error("error while writing db backup : {}",backupFile);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void cleanOldBackups(File backupFolder){
        long currentTime = System.currentTimeMillis();
        int day=1;
        for (File file : backupFolder.listFiles()) {
            if (file.getName().endsWith(".dump")) {
                long fileAge = currentTime-file.lastModified();
                if (fileAge>day*24*60*60*1000L && file.delete()) {
                    log.info("deleted old db backup : {}",file.getName());
                }
            }
        }
    }
}
