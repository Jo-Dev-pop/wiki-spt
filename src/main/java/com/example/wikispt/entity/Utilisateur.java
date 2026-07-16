package com.example.wikispt.entity;

import com.example.wikispt.enums.Role;
import com.example.wikispt.enums.StatutCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Utilisateur extends AbstractAuditingEntity implements UserDetails {

    //attributs
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false)
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutCompte statut = StatutCompte.ACTIF;

    @Column(nullable = false)
    private int tentativesEchouees = 0;

    @Column(name = "date_verrouillage")
    private Instant dateVerrouillage;

    // methodes
    public boolean estVerrouille() {
        return statut == StatutCompte.VERROUILLE;
    }

    public boolean estBloque(){
        return statut == StatutCompte.BLOQUE;
    }

    public boolean estActif() {
        return statut == StatutCompte.ACTIF;
    }

    public void verrouiller() {
        statut = StatutCompte.VERROUILLE;
        dateVerrouillage = Instant.now();
    }

    public void deverrouiller() {
        this.statut = StatutCompte.ACTIF;
        this.tentativesEchouees = 0;
        this.dateVerrouillage = null;
    }

    public void incrementerTentatives() {
        this.tentativesEchouees++;
    }

    public void reinitialiserTentatives() {
        this.tentativesEchouees = 0;
    }

    //les methodes de UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !estVerrouille() && !estBloque();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return estActif();
    }




}
