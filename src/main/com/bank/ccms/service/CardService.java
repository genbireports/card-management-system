package com.bank.ccms.service;

import com.bank.ccms.model.Card;
import com.bank.ccms.model.CardStatus;
import com.bank.ccms.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public Card getCard(UUID cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found: " + cardId));
    }

    /**
     * Handles Temporary Freeze and Unblock.
     * Rule: Cannot modify a card that is already permanently BLOCKED.
     */
    @Transactional
    public Card updateStatus(UUID cardId, String action) {
        Card card = getCard(cardId);

        if (card.getStatus() == CardStatus.BLOCKED) {
            throw new IllegalStateException("Operation failed: Card is permanently BLOCKED.");
        }

        if ("FREEZE".equalsIgnoreCase(action)) {
            card.setStatus(CardStatus.FROZEN);
        } else if ("UNBLOCK".equalsIgnoreCase(action)) {
            card.setStatus(CardStatus.ACTIVE);
        } else {
            throw new IllegalArgumentException("Invalid action: Use FREEZE or UNBLOCK");
        }

        return cardRepository.save(card);
    }

    /**
     * Handles Permanent Block.
     * Rule: Irreversible. Triggers replacement logic.
     */
    @Transactional
    public BlockResponse permanentBlock(UUID cardId, String reason) {
        Card card = getCard(cardId);

        if (card.getStatus() == CardStatus.BLOCKED) {
            throw new IllegalStateException("Card is already blocked.");
        }

        // 1. Apply Block
        card.setStatus(CardStatus.BLOCKED);
        cardRepository.save(card);

        // 2. Determine if new number is needed (Page 3 Logic)
        boolean isFraudOrLoss = reason.equalsIgnoreCase("FRAUD") || reason.equalsIgnoreCase("LOST");
        String replacementType = isFraudOrLoss ? "NEW_NUMBER" : "SAME_NUMBER";

        // 3. Determine Shipping Time (Page 1 Logic)
        int deliveryDays = "PLATINUM".equalsIgnoreCase(card.getProductTier()) ? 2 : 7;

        // 4. Create Mock Response (In real system, this calls Fulfillment Service)
        return BlockResponse.builder()
                .cardId(card.getCardId())
                .newStatus(CardStatus.BLOCKED)
                .replacementOrderId(UUID.randomUUID().toString())
                .replacementType(replacementType)
                .estimatedDeliveryDays(deliveryDays)
                .build();
    }

    // Inner DTO class for response
    @lombok.Data
    @lombok.Builder
    public static class BlockResponse {
        private UUID cardId;
        private CardStatus newStatus;
        private String replacementOrderId;
        private String replacementType;
        private int estimatedDeliveryDays;
    }
}