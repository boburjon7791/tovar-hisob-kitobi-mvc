package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrixodTovarRepository extends BaseRepository<PrixodTovar, UUID> {
}
