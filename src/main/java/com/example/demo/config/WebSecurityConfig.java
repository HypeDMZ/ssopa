package com.example.demo.config;

import com.example.demo.jwt.JwtAccessDeniedHandler;
import com.example.demo.jwt.JwtAuthenticationEntryPoint;
import com.example.demo.jwt.JwtFilter;
import com.example.demo.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = true)
@Component
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig{

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer (){
        return (web)-> web.ignoring().antMatchers("/v2/api-docs",  "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html", "/webjars/**","/swagger/**");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/auth/**","/api/**","/v3/api-docs","/swagger-ui/**").permitAll()
                        .antMatchers("/v2/api-docs/**").permitAll()
                        .antMatchers("/v2/api-docs").permitAll()
                        //.antMatchers("/member/**").permitAll()
                        .antMatchers("/index").permitAll()
                        .antMatchers("/swagger-resources/**").permitAll()
                        .antMatchers("/swagger-resources/configuration/ui").permitAll()
                        .antMatchers("/swagger-ui/index.html").permitAll()
                        .antMatchers("/actuator/**").permitAll()
                        //.antMatchers("/chat/**").permitAll()
                        .antMatchers("/chat").permitAll()
                        .antMatchers("/chat/room").permitAll()
                        .antMatchers("/ws/chat/**").permitAll()
                        .antMatchers("/profile").permitAll()
                        .antMatchers("/greeting").permitAll()
                        .antMatchers("/webjars/**").permitAll()
                        .antMatchers("/neis/**").permitAll()
                        //.antMatchers("/post/**").permitAll()
                        .anyRequest().authenticated()

                )
                .httpBasic().disable();

        return http.build();


    }






}
