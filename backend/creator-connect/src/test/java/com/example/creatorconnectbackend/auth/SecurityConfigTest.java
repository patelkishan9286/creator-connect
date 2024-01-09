package com.example.creatorconnectbackend.auth;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

class SecurityConfigTest {

    @Mock
    private JwtRequestFilter jwtRequestFilter;

    @InjectMocks
    private SecurityConfig securityConfig;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

	/*
	 * @Test public void testSecurityFilterChain() throws Exception { HttpSecurity
	 * httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);
	 * SecurityFilterChain result =
	 * securityConfig.securityFilterChain(httpSecurity); assertNotNull(result); }
	 */
}
