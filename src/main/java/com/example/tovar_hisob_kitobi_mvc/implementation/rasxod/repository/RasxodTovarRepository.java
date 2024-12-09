package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RasxodTovarRepository extends BaseRepository<RasxodTovar, UUID> {
}
