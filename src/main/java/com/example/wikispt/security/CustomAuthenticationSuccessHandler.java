package com.example.wikispt.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        for (GrantedAuthority authority : authentication.getAuthorities()) {

            String role = authority.getAuthority();

            switch (role) {

                case "ROLE_ADMINISTRATEUR":
                    response.sendRedirect("/admin/dashboard");
                    return;

                case "ROLE_CONTRIBUTEUR":
                    response.sendRedirect("/app/accueil");
                    return;

                case "ROLE_LECTEUR":
                    response.sendRedirect("/app/accueil");
                    return;
            }
        }

        response.sendRedirect("/login");
    }
}