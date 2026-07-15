package com.example.wikispt.entity;

import com.example.wikispt.enums.TypeNotification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification extends AbstractAuditingEntity{

    //attribut

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;   // celui qui reçoit la notification

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TypeNotification type;     // SOUMISSION, PUBLICATION, REJET

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean lu = false;

    //methodes

    public void marquerCommeLue() {
        this.lu = true;
    }

    public void marquerCommeNonLue() {
        this.lu = false;
    }
}
