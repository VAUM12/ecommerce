package com.qalaa.cart.repository;

import com.qalaa.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);

    boolean existsByUserIdAndProductId(Long userId, Long productId);

}
