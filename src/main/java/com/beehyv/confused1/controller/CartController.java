package com.beehyv.confused1.controller;

import com.beehyv.confused1.Model.Cart;
import com.beehyv.confused1.Model.CartItem;
import com.beehyv.confused1.service.CartItemService;
import com.beehyv.confused1.service.CartService;
import com.beehyv.confused1.service.ProductService;
import com.beehyv.confused1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "addToCart", method = RequestMethod.GET)
    public String addToCart(@RequestParam Integer productId,
                            @RequestParam Integer quantity) {
        String username = userService.CurrentUser();

        CartItem cartItem = new CartItem();
        cartItem.setProduct(productService.getProductById(productId));
        cartItem.setOrdered(false);
        cartItem.setQuantity(quantity);
        cartItemService.saveCartItem(cartItem);

        Cart cart = cartService.getByUsername(username);
        cart.addCartItem(cartItem);
        cartService.save(cart);

        return cart.toString();
    }

    @RequestMapping(value = "getCart", method = RequestMethod.GET)
    public List<CartItem> getCartByUserId() {
        String username = userService.CurrentUser();

        double amount = 0.0;


        Cart cart = cartService.getByUsername(username);
        List<CartItem> cartItems = new ArrayList<>();
        List<CartItem> fullList = cart.getCartItem();
        for (CartItem item : fullList) {
            if (item.isOrdered()) {

            } else {
                cartItems.add(item);
                amount += item.getAmount();
            }
        }

        return cartItems;
    }

    @RequestMapping(value = "getCartItem", method = RequestMethod.GET)
    public CartItem getCartItem(@RequestParam Integer cartItemId) {

        String username = userService.CurrentUser();
        Cart cart = cartService.getByUsername(username);
        List<CartItem> cartItems = cart.getCartItem();
        CartItem cartItem = null;
        for (CartItem items : cartItems) {
            if (items.getCartItemId() == cartItemId) {
                cartItem = items;
                break;
            }
        }
        return cartItem;
    }

    @RequestMapping(value = "removeFromCart", method = RequestMethod.GET)

    public String removeFromCart(@RequestParam Integer cartItemId) {
        String username = userService.CurrentUser();
        Cart cart = cartService.getByUsername(username);
        List<CartItem> cartItems = cart.getCartItem();

        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getCartItemId() == cartItemId && !cartItems.get(i).isOrdered()) {
                cartItems.remove(i);

                break;
            }

        }
        cartService.save(cart);
        return "cartItem deleted";
    }

    @RequestMapping(value = "changeQuantity", method = RequestMethod.GET)
    public CartItem changeQuantity(@RequestParam Integer quantity,
                                   @RequestParam Integer cartItemId) {

        CartItem cartItem = getCartItem(cartItemId);
        cartItem.setQuantity(quantity);
        cartItemService.saveCartItem(cartItem);

        return cartItem;


    }

    @RequestMapping(value = "createOrder", method = RequestMethod.GET)
    public List<CartItem> createOrder() {
        String username = userService.CurrentUser();
        Cart cart = cartService.getByUsername(username);
        List<CartItem> cartItems = cart.getCartItem();

        for (int i = 0; i < cartItems.size(); i++) {
            cartItems.get(i).setOrdered(true);
            cartItemService.saveCartItem(cartItems.get(i));
        }

        cartService.save(cart);

        return cart.getCartItem();

    }

    @RequestMapping(value = "orderHistory", method = RequestMethod.GET)
    public List<CartItem> orderHistory() {

        String username = userService.CurrentUser();

        Cart cart = cartService.getByUsername(username);
        List<CartItem> cartItems = cart.getCartItem();

        List<CartItem> orderedCartItem = new ArrayList<>();

        for (int i = 0; i < cartItems.size(); i++) {

            if (cartItems.get(i).isOrdered()) {
                orderedCartItem.add(cartItems.get(i));
            }

        }


        return orderedCartItem;

    }

}