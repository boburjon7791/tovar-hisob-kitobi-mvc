package com.example.tovar_hisob_kitobi_mvc.implementation.user.service;

import com.example.tovar_hisob_kitobi_mvc.base.common.ApiResponse;
import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntity;
import com.example.tovar_hisob_kitobi_mvc.base.service.BaseService;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.ChangePasswordRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserResponseDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.enums.Lavozim;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.filtering.UserFiltering;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, Long, UserRequestDTO, UserResponseDTO, UserFiltering> {
    private final PasswordEncoder passwordEncoder;
    @Override
    public void checkCreating(UserRequestDTO userRequestDTO) {
        User user=new User();
        user.setLogin(userRequestDTO.login());
        if (getBaseRepository().exists(Example.of(user))) {
            throw new ApiException(getLocalization().getMessage("already_exists_by_login"));
        }
    }

    @Override
    public void checkUpdating(UserRequestDTO userRequestDTO, Long id) {
        boolean exists = getBaseRepository().exists((root, query, criteriaBuilder) -> {
            Predicate loginEqual = criteriaBuilder.equal(root.get(User._login), userRequestDTO.login());
            Predicate idNotEqual = criteriaBuilder.notEqual(root.get(BaseEntity._id), id);
            return criteriaBuilder.and(loginEqual, idNotEqual);
        });
        if (exists) {
            throw new ApiException(getLocalization().getMessage("already_exists_by_login"));
        }
    }

    public void changePassword(ChangePasswordRequestDTO requestDTO){
        User currentUser = Utils.customUserDetails().user();
        if (!currentUser.getLavozim().equals(Lavozim.DIRECTOR) && !currentUser.getId().equals(requestDTO.id())) {
            throw new AccessDeniedException("");
        }
        User user = entity(requestDTO.id());
        if (!requestDTO.newPswd().equals(requestDTO.confirmPswd())) {
            throw new ApiException(getLocalization().getMessage("not_confirmed_pswd"));
        }
        if (!passwordEncoder.matches(requestDTO.oldPswd(), user.getParol())) {
            throw new ApiException(getLocalization().getMessage("incorrect_pswd"));
        }
        user.setParol(passwordEncoder.encode(requestDTO.newPswd()));
        getBaseRepository().save(user);
    }

    public void changeRole(Long id, Lavozim lavozim){
        User user = entity(id);
        user.setLavozim(lavozim);
        getBaseRepository().save(user);
    }

    @Override
    public ApiResponse<UserResponseDTO> update(UserRequestDTO requestDTO, Long id) {
        User currentLoginUser = Utils.customUserDetails().user();
        if (!currentLoginUser.getId().equals(id) && !currentLoginUser.getLavozim().equals(Lavozim.DIRECTOR)) {
            throw new AccessDeniedException("");
        }
        checkUpdating(requestDTO, id);
        return super.update(requestDTO, id);
    }

    @Override
    public ApiResponse<UserResponseDTO> create(UserRequestDTO requestDTO) {
        checkCreating(requestDTO);
        User user = getBaseMapper().toEntity(requestDTO);
        user.setParol(passwordEncoder.encode(requestDTO.parol()));
        User saved = getBaseRepository().save(user);
        UserResponseDTO responseDTO = getBaseMapper().toDto(saved);
        return ApiResponse.ok(responseDTO);
    }

    @Override
    public ApiResponse<List<UserResponseDTO>> findAll(UserFiltering request) {
        ApiResponse<List<UserResponseDTO>> apiResponse = super.findAll(request);
        /*String userId = Utils.request().getParameter("userId");
        if (userId!=null && !userId.isBlank()) {
            apiResponse.getData().stream()
                    .filter(userResponseDTO -> userResponseDTO.id().equals(Long.valueOf(userId)))
                    .findFirst()
                    .ifPresent(userResponseDTO -> {
                        apiResponse.getData().removeIf(dto -> dto.id().equals(Long.valueOf(userId)));
                        apiResponse.getData().addFirst(userResponseDTO);
                    });
        }*/
        return apiResponse;
    }
}
