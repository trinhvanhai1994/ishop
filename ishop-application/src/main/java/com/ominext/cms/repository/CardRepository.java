package com.ominext.cms.repository;

import com.ominext.cms.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findFirstById(Long id);
    List<Cart> findAllByUserId(Long userId);
    @Modifying
    @Transactional
    @Query(value = "delete from cart where user_id = :userId and product_id = :productId", nativeQuery = true)
    void deleteAllByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}
