package com.example.tovar_hisob_kitobi_mvc.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<BaseEntity, ID> extends JpaRepository<BaseEntity, ID>, JpaSpecificationExecutor<BaseEntity> {
}