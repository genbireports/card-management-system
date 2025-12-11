package com.bank.ccms.repository;

import com.bank.ccms.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
}