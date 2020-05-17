package com.ominext.cms.repository;

import com.ominext.cms.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findFirstById(Long id);
    List<Cart> findAllByUserId(Long userId);
}
