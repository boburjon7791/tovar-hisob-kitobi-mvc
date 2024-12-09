package com.example.tovar_hisob_kitobi_mvc.implementation.tovar.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TovarRepository extends BaseRepository<Tovar, Long> {
    Optional<Tovar> findByShtrixKod(String shtrixKod);
}
