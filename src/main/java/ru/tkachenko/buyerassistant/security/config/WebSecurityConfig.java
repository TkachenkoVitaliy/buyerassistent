package ru.tkachenko.buyerassistant.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.tkachenko.buyerassistant.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/uploadAccept").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/uploadMultipleFiles").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/downloadAllFiles").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/sendAllFiles").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/settings/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/settings/save_month_settings/to_main_page").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/settings/save_month_settings").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/settings/mail").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/main").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();

        //TODO-for front-end App
//        http
//                .csrf().disable()
//                .authorizeRequests().anyRequest().permitAll();
        //TODO-for front-end App
    }
}
