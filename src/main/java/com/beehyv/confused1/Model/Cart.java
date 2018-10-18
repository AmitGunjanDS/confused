package com.beehyv.confused1.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartId;

    @OneToMany
    private List<CartItem> cartItems;

    @OneToOne
    private User user;

    private Double total;

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }

    public Integer getCartId() {
        return cartId;
    }


    public List<CartItem> getCartItem() {
        return this.cartItems;
    }

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotal() {
        List<CartItem> items = cartItems;
        for (CartItem item : items) {
            this.total += item.getAmount();
        }
        return this.total;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", cartItems=" + cartItems +
                ", user=" + user +
                '}';
    }
}
