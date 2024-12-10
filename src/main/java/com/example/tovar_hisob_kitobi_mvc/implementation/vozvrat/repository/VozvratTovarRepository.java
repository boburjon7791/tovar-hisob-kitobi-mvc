package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface VozvratTovarRepository extends BaseRepository<VozvratTovar, UUID> {
    @Query(nativeQuery = true, value = """
            select * 
            from vozvrat_tovar 
            where tasdiqlandi=false and updated_at<?1
            """)
    List<VozvratTovar> findAllByUpdatedAtLessThanAndTasdiqlandiFalse(LocalDateTime updatedAt);
}
