package com.in28minutes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity  // we get Spring Security and MVC integration support:
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("cpats")
            .password(passwordEncoder().encode("admin"))
            .roles("USER", "ADMIN");
    }

    // The more specific rules need to come first, followed by the more general ones.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            // expose a login page
            .antMatchers("/login").permitAll()
            // any url contains todo must be secured
            .antMatchers("/", "/*todo*/**").access("hasRole('USER')")
            .and().formLogin()
            .and().logout().invalidateHttpSession(true).deleteCookies("JSESSIONID");
    }

    // Starting with Spring 5, we also have to define a password encoder.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
