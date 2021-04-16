package com.luv2code.springsecurity.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource securityDataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // add our users for in memory authentication
//        UserBuilder users = User.withDefaultPasswordEncoder();
//
//        auth.inMemoryAuthentication()
//                .withUser(users.username("q").password("q").roles("EMPLOYEE"))
//                .withUser(users.username("john").password("q").roles("EMPLOYEE"))
//                .withUser(users.username("mary").password("q").roles("EMPLOYEE", "MANAGER"))
//                .withUser(users.username("susan").password("q").roles("EMPLOYEE", "ADMIN", "MANAGER"));

        auth.jdbcAuthentication().dataSource(securityDataSource);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").hasRole("EMPLOYEE")  //restrict access based on Roles
                .antMatchers("/leaders/**").hasRole("MANAGER")
                .antMatchers("/systems/**").hasRole("ADMIN")
                .and()
                .formLogin()
                  .loginPage("/showMyLoginPage")         //add custom login page
                  .loginProcessingUrl("/authenticateTheUser")   //spring will do it for us, himself
                  .permitAll()
                .and()
                .logout().permitAll() //add logout option, spring will do it for us, himself (/contextPath/logout)
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied"); //if wrong access, send to this jsp
    }

}
