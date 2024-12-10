package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovarDetail;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VozvratTovarDetailRepository extends BaseRepository<VozvratTovarDetail, UUID> {
    List<VozvratTovarDetail> findAllByVozvratTovarId(UUID id, Sort sort);
}
