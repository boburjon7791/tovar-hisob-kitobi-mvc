package com.example.tovar_hisob_kitobi_mvc.base.common.security;

import com.example.tovar_hisob_kitobi_mvc.base.common.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.Optional;

@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final DataSource dataSource;

    public static final String[] OPEN={
            "/static/favicon.ico",
            "/images/**",
            "/errors/**",
            "/css/**",
            "/js/**",
            "/auth/register",
            "/auth/login",
            "/language/**",
            "/home",
            "/created-by"
    };
    public static final String[] CLOSED= {"/"};

    @Value(value = "${server.servlet.session.cookie.name}")
    private String sessionCookieName;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(registry ->
                        registry.requestMatchers(OPEN).permitAll()
                                .anyRequest().authenticated())
                .rememberMe(rememberMe-> {
                    rememberMe.alwaysRemember(true);
                    rememberMe.userDetailsService(customUserDetailsService);
                    rememberMe.tokenValiditySeconds(60*60*10);
                    rememberMe.tokenRepository(persistentTokenRepository());
                })
                .formLogin(formLogin->{
                    formLogin.permitAll(true);
                    formLogin.loginPage("/auth/login");
                    formLogin.failureUrl("/auth/login");
                    formLogin.successForwardUrl("/");
                    formLogin.defaultSuccessUrl("/",false);
                    formLogin.usernameParameter("username");
                    formLogin.passwordParameter("password");
                })
                .logout(logout -> {
                    logout.permitAll(true);
                    logout.logoutUrl("/auth/logout");
                    logout.logoutSuccessUrl("/auth/login");
                    logout.deleteCookies("JSESSIONID",sessionCookieName,"remember-me");
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", HttpMethod.GET.name()));
                })
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(exceptionHandling->exceptionHandling.authenticationEntryPoint(authenticationEntryPoint()))
                .build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, authException) -> response.sendRedirect("/auth/login");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuditorAware<Long> auditorAware(){
        return () -> Optional.ofNullable(Utils.customUserDetails().user().getId());
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return daoAuthenticationProvider;
    }
}
