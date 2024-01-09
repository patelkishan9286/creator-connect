package com.example.creatorconnectbackend.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Overrides the `commence` method of the `AuthenticationEntryPoint` interface.
     * This method is called whenever an AuthenticationException is thrown by the protected endpoint,
     * indicating that the user is not authenticated.
     *
     * @param request        The HTTP servlet request.
     * @param response       The HTTP servlet response.
     * @param authException  The AuthenticationException that triggered the entry point.
     * @throws IOException   If there is an input-output exception during processing.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
