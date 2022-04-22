package com.virtudoc.web.configuration;

import com.virtudoc.web.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configures how authentication, user sessions, and user profiles are stored in the application. Defines the ACL
 * whitelist on a per-route basis.
 *
 * @author ARMmaster17
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // By default, all requests must have authorization.
                .authorizeRequests()
                // The following endpoints do not require authentication.

                .antMatchers("/", "/img/**", "/*.png", "/js/**","/*.ico", "/site.webmanifest", "/forgotMyPassword","/newPassword","/resetEmail",
                        "/login", "/register", "/registerDoctorAdmin", "/HIPAA_consent", "/checkEmail", "/debug/health","/about")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .defaultSuccessUrl("/notifications",true)
                .failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password");
    }
}
