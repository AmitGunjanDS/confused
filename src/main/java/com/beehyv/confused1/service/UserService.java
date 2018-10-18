package com.beehyv.confused1.service;

import com.beehyv.confused1.DAO.UserDAO;
import com.beehyv.confused1.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getUserList() {
        return userDAO.findAll();
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    public User getUserByUsername(String username) {
        return userDAO.findById(username).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user" + username + "does not exist");
        }

        String role = user.getRole();

        List<GrantedAuthority> grantList = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        grantList.add(authority);

        boolean enabled = user.isActive();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, grantList);
    }

    public String CurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    public boolean userExists(String username) {
        return userDAO.existsAllByUsername(username);
    }
}
