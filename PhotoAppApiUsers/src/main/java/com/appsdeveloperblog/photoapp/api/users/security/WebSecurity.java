package com.appsdeveloperblog.photoapp.api.users.security;

import com.appsdeveloperblog.photoapp.api.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

    private final Environment environment;
    private final UsersService usersService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationConfiguration configuration;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .headers().frameOptions().disable()
                .and()
                .addFilter(getAuthenticationFilter(authenticationManager()));

        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(),usersService,environment);
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
        return authenticationFilter;
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Autowired
    void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
    }

}
