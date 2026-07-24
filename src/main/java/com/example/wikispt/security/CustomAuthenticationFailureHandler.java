package com.example.wikispt.security;

import com.example.wikispt.repository.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final int MAX_TENTATIVES = 3;

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        if (exception instanceof LockedException) {
            response.sendRedirect("/login?error=locked");
            return;
        }

        String email = request.getParameter("username");

        if (email != null) {
            utilisateurRepository.findByEmail(email).ifPresent(utilisateur -> {
                utilisateur.incrementerTentatives();
                if (utilisateur.getTentativesEchouees() >= MAX_TENTATIVES) {
                    utilisateur.verrouiller();
                }
                utilisateurRepository.save(utilisateur);
            });
        }

        response.sendRedirect("/login?error=true");
    }
}