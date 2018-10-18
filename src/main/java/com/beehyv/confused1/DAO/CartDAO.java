package com.beehyv.confused1.DAO;

import com.beehyv.confused1.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDAO extends JpaRepository<Cart, Integer> {

    Cart findByUserUsername(String username);
}
