package org.lukdt.bank_card_management.repository;

import org.lukdt.bank_card_management.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>,
                                            JpaSpecificationExecutor<Card> {
    Page<Card> findByOwnerId(Long ownerId, Pageable pageable);

    Optional<Card> findByOwnerId(Long ownerId);

    Optional<Card> findByIdAndOwnerId(Long id, Long ownerId);
}
