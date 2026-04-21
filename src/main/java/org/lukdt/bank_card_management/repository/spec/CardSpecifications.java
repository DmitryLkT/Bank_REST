package org.lukdt.bank_card_management.repository.spec;

import org.lukdt.bank_card_management.entity.Card;
import org.lukdt.bank_card_management.entity.Status;
import org.springframework.data.jpa.domain.Specification;

public class CardSpecifications {
    public static Specification<Card> ownerIdEquals(Long ownerId) {
        return (root, query, cb) -> cb.equal(root.get("owner").get("id"), ownerId);
    }

    public static Specification<Card> statusEquals(Status status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }
}
