package mmd.headless.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/public").permitAll() // Allow access to /public endpoint without authentication
                .anyRequest().authenticated() // Require authentication for any other endpoint
                .and()
                .formLogin() // Use form-based authentication
                .permitAll() // Allow access to the login page
                .and()
                .logout() // Enable logout support
                .permitAll(); // Allow access to the logout URL
    }
}
