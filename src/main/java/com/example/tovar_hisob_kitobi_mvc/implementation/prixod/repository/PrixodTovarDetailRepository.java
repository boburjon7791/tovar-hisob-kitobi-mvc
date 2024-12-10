package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovarDetail;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrixodTovarDetailRepository extends BaseRepository<PrixodTovarDetail, UUID> {
    List<PrixodTovarDetail> findAllByPrixodTovarId(UUID id, Sort sort);
}
