package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovarDetail;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RasxodTovarDetailRepository extends BaseRepository<RasxodTovarDetail, UUID> {
    List<RasxodTovarDetail> findAllByRasxodTovarId(UUID id, Sort sort);

    @Query(nativeQuery = true, value = "select * from rasxod_tovar_detail rtd where rtd.rasxod_tovar_id=:id")
    List<RasxodTovarDetail> findAllByRasxodTovarId(UUID id);
}
