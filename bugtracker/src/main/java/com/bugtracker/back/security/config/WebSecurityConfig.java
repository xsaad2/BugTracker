package com.bugtracker.back.security.config;

import com.bugtracker.back.Services.UserService;
import com.bugtracker.back.security.PasswordEncoder;
import com.bugtracker.back.session.SessionFilter;
import com.bugtracker.back.utils.AppUserRoles;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SessionFilter sessionFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/registration","/login","/isloggedin").permitAll()
                        .requestMatchers("/emailcheck", "/emailcheck/**").permitAll()
                        .requestMatchers("/userdetails").hasAuthority(AppUserRoles.ADMIN.toString())
                        .anyRequest().authenticated());

        http.cors();
        http
                .addFilterBefore(
                        sessionFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
