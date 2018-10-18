package com.beehyv.confused1.controller;

import com.beehyv.confused1.Model.Cart;
import com.beehyv.confused1.Model.User;
import com.beehyv.confused1.service.CartService;
import com.beehyv.confused1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;


    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json", consumes = "application/x-www-form-urlencoded")

    public void signUp(HttpServletResponse response,
                       @RequestParam String name,
                       @RequestParam String username,
                       @RequestParam String password,
                       @RequestParam String email,
                       @RequestParam String phone) throws IOException {

        if (!userService.userExists(username)) {
            User user = new User(name, username, password, email, phone);
            userService.saveUser(user);

            Cart cart = new Cart(user);
            cart.setUser(user);
            cartService.save(cart);

            response.sendRedirect("/login.html");
        } else {
            response.sendRedirect("/userExists.html");
        }

    }

    @RequestMapping(value = "/getProfile", method = RequestMethod.GET)

    public User getProfileByUsername() {

        String username = userService.CurrentUser();
        return userService.getUserByUsername(username);

    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")

    public void updateUser(@RequestParam String name,
                           @RequestParam String phone,
                           @RequestParam String email,
                           HttpServletResponse response) throws IOException {
        String username = userService.CurrentUser();
        User user = userService.getUserByUsername(username);

        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        userService.saveUser(user);

        response.sendRedirect("/UserDetail.html");

    }

    @RequestMapping(value = "forgotPassword", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public void changePassword(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String newPassword,
                               HttpServletResponse response
    ) throws IOException {

        if (userService.userExists(username)) {
            User user = userService.getUserByUsername(username);

            if (email.equals(user.getEmail())) {
                user.setPassword(newPassword);
                userService.saveUser(user);
                response.sendRedirect("/login.html");
            } else {
                response.sendRedirect("/wrongCredential.html");
            }
        } else {
            response.sendRedirect("/wrongCredential.html");
        }
    }

    @RequestMapping(value = "makeAdmin", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public void makeAdmin(@RequestParam String username) {
        userService.getUserByUsername(username).assignAdmin();
    }

    @RequestMapping(value = "listUsers", method = RequestMethod.POST)
    public List<User> allUsers() {
        return userService.getUserList();
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public void error(HttpServletResponse response) throws IOException {
        response.sendRedirect("/error.html");
    }
}
