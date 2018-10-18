package com.beehyv.confused1.controller;

import com.beehyv.confused1.Model.Cart;
import com.beehyv.confused1.Model.CartItem;
import com.beehyv.confused1.Model.Product;
import com.beehyv.confused1.service.CartItemService;
import com.beehyv.confused1.service.CartService;
import com.beehyv.confused1.service.ProductService;
import com.beehyv.confused1.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "addProduct", method = RequestMethod.GET,produces = "application/json",consumes = "application/x-www-form-urlencoded")
    public void addNew(@RequestParam String name,
                       @RequestParam Double price,
                       @RequestParam String category,
                       HttpServletResponse response) throws IOException {
        Product product = new Product();
        product.setProductName(name);
        product.setPrice(price);

        product.setCategory(category);

        productService.addProduct(product);
        response.sendRedirect("/main.html");
    }

    @RequestMapping(value = "getProductById", method = RequestMethod.GET)
    public Product getById(@RequestParam Integer productId) {
        return productService.getProductById(productId);
    }

    @RequestMapping(value = "getProductByName", method = RequestMethod.GET)
    public Product getName(@RequestParam String productName) {
        return productService.getProductByName(productName);
    }

    @RequestMapping(value = "findProductByCategory", method = RequestMethod.GET)
    public List<Product> findByCategory(@RequestParam String category) {

        return productService.getProductByCategory(category);

    }

    @RequestMapping(value = "modifyProduct", method = RequestMethod.POST, consumes ="application/json",produces = "application/json")
    public Product modify(@RequestBody ObjectNode jsonProduct) {

        Product product = productService.getProductById(jsonProduct.get("productId").asInt());

        product.setProductName(jsonProduct.get("name").asText());
        product.setPrice(jsonProduct.get("price").asDouble());
        product.setCategory(jsonProduct.get("category").asText());

        productService.addProduct(product);

        return productService.getProductById(jsonProduct.get("productId").asInt());
    }

    @RequestMapping(value = "deleteProduct", method = RequestMethod.GET)
    public void deleteProduct(@RequestParam Integer productId) {
        Product product = productService.getProductById(productId);
        Cart cart =cartService.getByUsername(userService.CurrentUser());

        List<CartItem> cartProducts = cartItemService.getByProduct(product);
        while (cartProducts.size()>0){
            cart.getCartItem().remove(cartProducts.get(0));
        }

//        if (cart.getCartItem().contains(cartItemService.getByProduct(product))){
//            cart.getCartItem().remove(cartItemService.getByProduct(product));
//            cartItemService.delete(product);
//
//        }
        cartService.save(cart);

        productService.deleteProduct(productId);

    }



    @RequestMapping(value = "search", method = RequestMethod.GET)
    public List<Product> search(@RequestParam String search) {
        return productService.getBySearchString(search);
    }

    @RequestMapping(value = "filter", method = RequestMethod.GET)
    public List<Product> priceFilter(@RequestParam Double lowest,
                                     @RequestParam Double highest) {
        return productService.priceFilter(lowest, highest);
    }

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public List<Product> getAll() {
        return productService.getProductList();
    }
}
