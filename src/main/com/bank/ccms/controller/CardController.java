package com.bank.ccms.controller;

import com.bank.ccms.model.Card;
import com.bank.ccms.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    // 1. Get Card Details
    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getCard(@PathVariable UUID cardId) {
        return ResponseEntity.ok(cardService.getCard(cardId));
    }

    // 2. Toggle Status (Freeze/Unblock)
    @PatchMapping("/{cardId}/status")
    public ResponseEntity<Card> updateStatus(
            @PathVariable UUID cardId,
            @RequestBody Map<String, String> payload) {
        // Payload example: { "action": "FREEZE" }
        String action = payload.get("action");
        return ResponseEntity.ok(cardService.updateStatus(cardId, action));
    }

    // 3. Permanent Block & Replace
    @PostMapping("/{cardId}/block")
    public ResponseEntity<CardService.BlockResponse> blockCard(
            @PathVariable UUID cardId,
            @RequestBody Map<String, String> payload) {
        // Payload example: { "reason": "FRAUD" }
        String reason = payload.get("reason");
        return ResponseEntity.ok(cardService.permanentBlock(cardId, reason));
    }
}