package com.beehyv.confused1.DAO;

import com.beehyv.confused1.Model.CartItem;
import com.beehyv.confused1.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemDAO extends JpaRepository<CartItem, Integer> {
    void deleteByProduct(Product product);
    List<CartItem> findByProduct(Product product);
}
