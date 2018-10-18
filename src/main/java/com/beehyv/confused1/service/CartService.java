package com.beehyv.confused1.service;

import com.beehyv.confused1.DAO.CartDAO;
import com.beehyv.confused1.Model.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;

    public void save(Cart cart) {
        cartDAO.save(cart);
    }

    public Cart getById(Integer cartId) {
        return cartDAO.findById(cartId).get();

    }


    public Cart getByUsername(String username) {
        return cartDAO.findByUserUsername(username);
    }


}
