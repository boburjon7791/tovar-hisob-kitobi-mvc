package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovarDetail;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VozvratTovarDetailRepository extends BaseRepository<VozvratTovarDetail, UUID> {
    List<VozvratTovarDetail> findAllByVozvratTovarId(UUID id, Sort sort);

    @Query(nativeQuery = true, value = "select * from vozvrat_tovar_detail rtd where rtd.vozvrat_tovar_id=:id")
    List<VozvratTovarDetail> findAllByVozvratTovarId(UUID id);
}
