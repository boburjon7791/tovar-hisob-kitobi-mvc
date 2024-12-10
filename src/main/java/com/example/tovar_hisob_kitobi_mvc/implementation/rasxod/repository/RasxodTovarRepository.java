package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RasxodTovarRepository extends BaseRepository<RasxodTovar, UUID> {
    @Query(nativeQuery = true, value = """
            select * 
            from rasxod_tovar 
            where tasdiqlandi=false and updated_at<?1
            """)
    List<RasxodTovar> findAllByUpdatedAtLessThanAndTasdiqlandiFalse(LocalDateTime updatedAt);
}
