package com.example.tovar_hisob_kitobi_mvc.base.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.common.internationalization.Localization;
import com.example.tovar_hisob_kitobi_mvc.base.common.PaginationResponse;
import com.example.tovar_hisob_kitobi_mvc.base.controller.BaseControllerMVC;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntity;
import com.example.tovar_hisob_kitobi_mvc.base.model.mapper.BaseMapper;
import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.base.specification.BaseSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public abstract class BaseService<ENTITY,ID, REQUEST_DTO, RESPONSE_DTO, FILTERING> {
    private BaseRepository<ENTITY, ID> baseRepository;
    private BaseMapper<ENTITY, REQUEST_DTO, RESPONSE_DTO> baseMapper;
    private BaseSpecification<ENTITY, FILTERING> baseSpecification;
    private Localization localization;
    private BaseControllerMVC<ENTITY,ID,REQUEST_DTO,RESPONSE_DTO,FILTERING> baseControllerMVC;
    private SimpMessagingTemplate simpMessagingTemplate;
    private final Locale uzLocale = Locale.of("uz");
    private final Locale ruLocale = Locale.of("ru");

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setSimpMessagingTemplate(@Lazy SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Autowired
    public void setBaseControllerMVC(@Lazy BaseControllerMVC<ENTITY, ID, REQUEST_DTO, RESPONSE_DTO, FILTERING> baseControllerMVC) {
        this.baseControllerMVC = baseControllerMVC;
    }

    @Autowired
    public void setEntityManager(@Lazy EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public void setBaseRepository(@Lazy BaseRepository<ENTITY, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Autowired
    public void setBaseMapper(@Lazy BaseMapper<ENTITY, REQUEST_DTO, RESPONSE_DTO> baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Autowired
    public void setBaseSpecification(@Lazy BaseSpecification<ENTITY, FILTERING> baseSpecification) {
        this.baseSpecification = baseSpecification;
    }

    @Autowired
    public void setLocalization(@Lazy Localization localization) {
        this.localization = localization;
    }

    public abstract void checkCreating(REQUEST_DTO dto);
    public abstract void checkUpdating(REQUEST_DTO dto, ID id);

    public ApiResponse<RESPONSE_DTO> create(REQUEST_DTO dto){
        checkCreating(dto);
        return ApiResponse.ok(baseMapper.toDto(baseRepository.save(baseMapper.toEntity(dto))));
    }

    public ApiResponse<RESPONSE_DTO> findById(ID id){
        return ApiResponse.ok(baseMapper.toDto(entity(id)));
    }

    public ApiResponse<RESPONSE_DTO> update(REQUEST_DTO dto, ID id){
        checkUpdating(dto, id);
        ENTITY entity = entity(id);
        baseMapper.update(entity, dto);
        return ApiResponse.ok(baseMapper.toDto(baseRepository.save(entity)));
    }

    public ApiResponse<List<RESPONSE_DTO>> findAll(FILTERING request){
        Specification<ENTITY> specification = baseSpecification.specification(request);
        Pageable pageable = baseSpecification.pageable(request);
        Page<RESPONSE_DTO> page = baseRepository.findAll(specification, pageable)
                .map(baseMapper::toDto);
        return ApiResponse.ok(page.getContent(), PaginationResponse.of(page));
    }

    public ApiResponse<Void> deleteById(ID id){
        baseRepository.deleteById(id);
        return ApiResponse.ok();
    }

    public ENTITY entity(ID id){
//        String name = localization.getMessage(BaseEntity.class.getDeclaredAnnotation(Table.class).name());
        String notFound = localization.getMessage("not_found");
        return baseRepository.findById(id).orElseThrow(() -> new ApiException(entityLocalizationName()+" "+notFound));
    }

    public ENTITY saveEntity(ENTITY entity){
        return getBaseRepository().save(entity);
    }

    public List<RESPONSE_DTO> findAll(){
        return baseRepository.findAll(Sort.by(Sort.Direction.DESC, BaseEntity._createdAt)).stream().map(baseMapper::toDto).collect(Collectors.toList());
    }

    public String entityLocalizationName(){
        String entityName = baseControllerMVC.apiPrefix().replaceAll("-", "_");
        return localization.getMessage(entityName);
    }
}
