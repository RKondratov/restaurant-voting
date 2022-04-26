package ru.graduation.restaurantvoting.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.graduation.restaurantvoting.model.Role;
import ru.graduation.restaurantvoting.model.User;
import ru.graduation.restaurantvoting.repository.UserRepository;
import ru.graduation.restaurantvoting.web.AuthUser;

import java.util.Optional;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final PasswordEncoder DELEGATING_PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final UserRepository userRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email -> {
            Optional<User> optionalUser = userRepository.getByEmail(email);
            return new AuthUser(optionalUser.orElseThrow(
                    () -> new UsernameNotFoundException("User '" + email + "' was not found")));
        }).passwordEncoder(DELEGATING_PASSWORD_ENCODER);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                .antMatchers("/api/user/**").hasRole(Role.USER.name())
                .antMatchers("/api/common/**").authenticated()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }
}