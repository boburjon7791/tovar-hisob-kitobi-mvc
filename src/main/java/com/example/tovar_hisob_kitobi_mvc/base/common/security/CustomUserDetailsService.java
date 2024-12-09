package com.example.tovar_hisob_kitobi_mvc.base.common.security;

import com.example.tovar_hisob_kitobi_mvc.base.common.internationalization.Localization;
import com.example.tovar_hisob_kitobi_mvc.base.exception.ApiException;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.User;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.repository.UserRepository;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Localization localization;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String name=localization.getMessage(User.class.getAnnotation(Table.class).name());
        String message = localization.getMessage("not_found");
        User user = userRepository.findByLogin(username).orElseThrow(() -> new ApiException(localization.getMessage(name + " " + message)));
        return new CustomUserDetails(user);
    }
}
