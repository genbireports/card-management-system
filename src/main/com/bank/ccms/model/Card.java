package com.bank.ccms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cardId;

    @Column(nullable = false)
    private String maskedCardNumber; // Storing masked for demo

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status;

    @Column(nullable = false)
    private String productTier; // "BLUE", "GOLD", "PLATINUM"

    private LocalDate expirationDate;
}