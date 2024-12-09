package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VozvratTovarRepository extends BaseRepository<VozvratTovar, UUID> {
}
