package com.example.creatorconnectbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Annotate the class as a Configuration class to provide bean definitions to the Spring IoC container.
 */
@Configuration
/**
 * Enable Spring's web security features.
 */
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Defines the security filter chain for the application using the provided HttpSecurity object.
     *
     * @param http The HttpSecurity object to configure the security filter chain.
     * @return The configured SecurityFilterChain object.
     * @throws Exception If an exception occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()

            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    // Allow anyone to access registration and login endpoints.
                    .requestMatchers(
                        "/api/users/register",
                        "/api/users/login",
                        "/api/users/forgot-password"
                    ).permitAll()
                    // Any other request should be authenticated.
                    .anyRequest().authenticated()
            )

            .exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
            )

            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
