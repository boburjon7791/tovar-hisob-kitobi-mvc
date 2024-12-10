package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PrixodTovarRepository extends BaseRepository<PrixodTovar, UUID> {
    @Query(nativeQuery = true, value = """
            select * 
            from prixod_tovar 
            where tasdiqlandi=false and updated_at<?1
            """)
    List<PrixodTovar> findAllByUpdatedAtLessThanAndTasdiqlandiFalse(LocalDateTime updatedAt);
}
