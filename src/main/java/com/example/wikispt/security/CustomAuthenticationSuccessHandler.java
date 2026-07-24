package com.example.wikispt.security;

import com.example.wikispt.repository.UtilisateurRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        utilisateurRepository.findByEmail(authentication.getName())
                .ifPresent(u -> {
                    u.reinitialiserTentatives();
                    utilisateurRepository.save(u);
                });

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