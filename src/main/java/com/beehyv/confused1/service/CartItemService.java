package com.beehyv.confused1.service;

import com.beehyv.confused1.DAO.CartItemDAO;
import com.beehyv.confused1.Model.CartItem;
import com.beehyv.confused1.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemDAO cartItemDAO;

    public void saveCartItem(CartItem cartItem) {
        cartItemDAO.save(cartItem);
    }

    public CartItem getById(Integer cartInfoId) {
        return cartItemDAO.findById(cartInfoId).get();
    }

    public void delete(Product product){
        cartItemDAO.deleteByProduct(product);
    }

    public List<CartItem> getByProduct(Product product){
        return cartItemDAO.findByProduct(product);
    }


}
