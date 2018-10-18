package com.beehyv.confused1.Config;


import com.beehyv.confused1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity


public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired

    UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());


    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/getprofile", "/addToCart", "/getCartItem"
                , "/getCart", "/updateProfile", "/removeFromCart", "/changeQuantity", "/createOrder", "/orderHistory",
                "/getProductById", "/getProductByName", "/findProductByCategory", "/search",
                "/filter", "/getAll")
                .access("hasAnyRole('ADMIN','USER')");

        http.authorizeRequests().antMatchers("/listUsers", "/makeAdmin", "/addProduct", "/modifyProduct").access("hasRole('ADMIN')");

        http.authorizeRequests().antMatchers("/signup", "/forgotPassword", "/error").permitAll();

        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403.html");


        http.authorizeRequests().and().formLogin()//


                .loginProcessingUrl("/login")
                .loginPage("/login.html")
                .defaultSuccessUrl("/main.html")
                .failureUrl("/wrongCredential.html")
                .usernameParameter("username")
                .passwordParameter("password")


                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");

    }
}
