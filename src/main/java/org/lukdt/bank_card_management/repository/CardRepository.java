package org.lukdt.bank_card_management.repository;

import org.lukdt.bank_card_management.entity.Card;
import org.lukdt.bank_card_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<User, Long> {
    Page<Card> findByOwnerId(Long ownerId, Pageable pageable);
}
