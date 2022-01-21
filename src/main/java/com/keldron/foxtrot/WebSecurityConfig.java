package com.keldron.foxtrot;

import com.keldron.foxtrot.model.AuthorityRole;
import com.keldron.foxtrot.model.User;
import com.keldron.foxtrot.repositories.service.DataAccessService;
import com.keldron.foxtrot.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private DataAccessService userDataAccessService;

    @Autowired
    public WebSecurityConfig(DataAccessService userDataAccessService) {
        this.userDataAccessService = userDataAccessService;
    }
    //configuration of web security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users")
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/add-training", "/venues-list", "/add-venue").hasRole("ADMIN").and()
                .authorizeRequests()
                .antMatchers("/", "/home", "/registration").permitAll()
                .antMatchers("/resources/**", "/img/**", "/css/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
        .and().csrf().disable().headers().frameOptions().disable();
    }

    //configuration of users data source
    @Override
    public UserDetailsService userDetailsService() {
        return userDataAccessService;
    }

}
