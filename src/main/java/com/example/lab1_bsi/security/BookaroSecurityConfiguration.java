package com.example.lab1_bsi.security;

import com.example.lab1_bsi.serwisy.UserService;
import com.example.lab1_bsi.serwisy.EncoderHMAC;
import com.example.lab1_bsi.serwisy.EncodeSHA512;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class BookaroSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // 1
        http
                .authorizeRequests()
                .antMatchers("/create/user").permitAll() // 2
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll().and().httpBasic(); // 3

    }

    private UserService userService;

    @Autowired
    public BookaroSecurityConfiguration(UserService userService){
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("sha512", new EncodeSHA512());
        encoders.put("hmac", new EncoderHMAC());
        return new DelegatingPasswordEncoder("sha512", encoders);
    }


}
