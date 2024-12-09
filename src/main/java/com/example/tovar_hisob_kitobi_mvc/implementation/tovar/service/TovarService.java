package com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.dto.TovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.dto.TovarResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.filtering.TovarFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.repository.TovarRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TovarService extends BaseService<Tovar, Long, TovarRequestDTO, TovarResponseDTO, TovarFiltering> {
    public static final String path="folder";
    private final TovarRepository tovarRepository;

    static {
        Path folder = Path.of(path);
        if (!Files.exists(folder)) {
            try {
                Files.createDirectories(folder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void checkCreating(TovarRequestDTO tovarRequestDTO) {

    }

    @Override
    public void checkUpdating(TovarRequestDTO tovarRequestDTO, Long aLong) {

    }

    @Override
    public ApiResponse<TovarResponseDTO> create(TovarRequestDTO tovarRequestDTO) {
        checkCreating(tovarRequestDTO);
        Tovar tovar = getBaseMapper().toEntity(tovarRequestDTO);
        if (tovarRequestDTO.file()!=null && !tovarRequestDTO.file().isEmpty()) {
            tovar.setRasmi(saveAndGetFilename(tovarRequestDTO.file()));
        }
        Tovar saved = getBaseRepository().save(tovar);
        TovarResponseDTO responseDTO = getBaseMapper().toDto(saved);
        return ApiResponse.ok(responseDTO);
    }

    @Override
    public ApiResponse<TovarResponseDTO> update(TovarRequestDTO tovarRequestDTO, Long id) {
        checkUpdating(tovarRequestDTO, id);
        Tovar tovar = entity(id);
        getBaseMapper().update(tovar, tovarRequestDTO);
        if (tovarRequestDTO.file()!=null && !tovarRequestDTO.file().isEmpty()) {
            deleteImage(tovar.getRasmi());
            tovar.setRasmi(saveAndGetFilename(tovarRequestDTO.file()));
        }
        Tovar saved = getBaseRepository().save(tovar);
        TovarResponseDTO responseDTO = getBaseMapper().toDto(saved);
        return ApiResponse.ok(responseDTO);
    }

    @SneakyThrows
    public String saveAndGetFilename(MultipartFile file){
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf("."));
        String randomFileName= UUID.randomUUID()+extension;
        Files.copy(file.getInputStream(), Path.of(path+"/"+randomFileName), StandardCopyOption.REPLACE_EXISTING);
        return randomFileName;
    }

    @SneakyThrows
    public byte[] getImage(String fileName){
        Path file=Path.of(path+"/"+fileName);
        if (Files.exists(file)) {
            return Files.readAllBytes(file);
        }
        throw new ApiException(getLocalization().getMessage("rasm")+" "+getLocalization().getMessage("not_found"));
    }

    @SneakyThrows
    @Async
    public void deleteImage(String fileName){
        Files.deleteIfExists(Path.of(path+"/"+fileName));
    }

    public void addKol(Tovar tovar, BigDecimal kol){
        if (kol.compareTo(BigDecimal.ZERO)<0) {
            throw new ApiException(tovar.getNomi()+", "+kol+" "+getLocalization().getMessage("kol_less_than_zero"));
        }
        tovar.setOstatkasi(tovar.getOstatkasi().add(kol));
        getBaseRepository().save(tovar);
    }

    public void subtractKol(Tovar tovar, BigDecimal kol){
        BigDecimal subtracted = tovar.getOstatkasi().subtract(kol);
        if (subtracted.compareTo(BigDecimal.ZERO)<0) {
            throw new ApiException(getLocalization().getMessage("ostatka_can_not_be_less_then_zero")+" "+kol+", "+tovar.getNomi()+" "+tovar.getOstatkasi());
        }
        tovar.setOstatkasi(subtracted);
        getBaseRepository().save(tovar);
    }

    public Tovar findByShtrixKod(String shtrixKod){
        return tovarRepository.findByShtrixKod(shtrixKod).orElseThrow(() -> new ApiException(getLocalization().getMessage("not_found")));
    }
}
