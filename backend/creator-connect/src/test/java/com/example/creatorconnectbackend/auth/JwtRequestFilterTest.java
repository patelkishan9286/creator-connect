package com.example.creatorconnectbackend.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;

class JwtRequestFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }
    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }


    @Test
    void testDoFilterInternal_noAuthorizationHeader() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_invalidAuthorizationHeader() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Invalid");

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_unableToExtractUsername() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer someJwtToken");
        when(jwtUtil.extractEmail("someJwtToken")).thenThrow(new IllegalArgumentException());

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_expiredToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer someJwtToken");
        Header dummyHeader = new DefaultHeader();
        Claims dummyClaims = new DefaultClaims();
        when(jwtUtil.extractEmail(anyString())).thenThrow(new ExpiredJwtException(dummyHeader, dummyClaims, "JWT Token has expired", null));

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_tokenNotValidated() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer someJwtToken");
        when(jwtUtil.extractEmail("someJwtToken")).thenReturn("email@example.com");
        when(jwtUtil.validateToken("someJwtToken", "email@example.com")).thenReturn(false);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_validToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer someJwtToken");
        when(jwtUtil.extractEmail("someJwtToken")).thenReturn("email@example.com");
        when(jwtUtil.validateToken("someJwtToken", "email@example.com")).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("email@example.com", SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
