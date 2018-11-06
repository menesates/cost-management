package com.menesates.costmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,
        prePostEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**/favicon.ico",
                "/css/**",
                "js/**",
                "/images/**",
                "/webjars/**",
                "/login.html").permitAll()
                .antMatchers("/rest/**").access("hasRole('EDITOR')")
                .antMatchers("/actuator/**").access("hasRole('ADMIN')")
                .anyRequest().authenticated();

        http.formLogin().loginPage("/login.html")
                .loginProcessingUrl("/login")
                .failureUrl("/login.html?loginFailed=true");
        
        http.rememberMe()
                .rememberMeCookieName("CostManagement-remember-me")
                .tokenValiditySeconds(24*60*60) // 1 day
                .tokenRepository(persistentTokenRepository());

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        System.out.println("geldi");
        System.out.println(tokenRepository);
        return tokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }
}
