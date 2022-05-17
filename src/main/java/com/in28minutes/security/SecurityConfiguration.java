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

import javax.sql.DataSource;

/**
 * The Spring security configuration class. Configures the authentication mechanism and
 * the access rights for the users.
 *
 * @author Christos Patsouras
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * The DB used.
     */
    DataSource dataSource;

    /**
     * Setter injection for the datasource.
     *
     * @param dataSource
     *          The DB used.
     */
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*
     * Defines the authentication mechanism of the app.
     */
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }


    /*
     * Defines the rules for the access of the users.
     * The more specific rules need to come first, followed by the more general ones.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            // expose a login page
            .antMatchers("/login").permitAll()
            // any url contains todo must be secured
            .antMatchers("/", "/*todo*/**").hasRole("USER")
            .and().formLogin()
            .and().logout().invalidateHttpSession(true).deleteCookies("JSESSIONID");
    }

    /**
     * Initializes and returns the BCryptPasswordEncoder.
     *
     * @return
     *          The password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}