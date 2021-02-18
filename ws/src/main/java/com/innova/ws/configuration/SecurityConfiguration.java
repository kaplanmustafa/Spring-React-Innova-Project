package com.innova.ws.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(new AuthEntryPoint());

        http.headers().frameOptions().disable();

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/1.0/users/{username}/notes").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/1.0/users/{username}").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/1.0/users/password/{username}").authenticated()
                .antMatchers(HttpMethod.GET, "/api/1.0/users").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/1.0/users/{username}").authenticated()
                .antMatchers(HttpMethod.POST, "/api/1.0/notes").authenticated()
                .antMatchers(HttpMethod.GET, "/api/1.0/users/{username}/notes").authenticated()
                .antMatchers(HttpMethod.GET, "/api/1.0/users/{username}/notes/{id:[0-9]+}").authenticated()
                .antMatchers(HttpMethod.GET, "/api/1.0/notes/{username}/{noteId}").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/1.0/notes/{id:[0-9]+}").authenticated()
                .antMatchers(HttpMethod.POST, "/api/1.0/comments/{noteId}").authenticated()
                .antMatchers(HttpMethod.GET, "/api/1.0/users/comments/{noteId}").authenticated()
                .antMatchers(HttpMethod.GET, "/api/1.0/users/{noteId}/comments/{id:[0-9]+}").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/1.0/comments/{id:[0-9]+}").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/1.0/comments/{username}/{commentId}").authenticated()
                .and()
                .authorizeRequests().anyRequest().permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    TokenFilter tokenFilter() {
        return new TokenFilter();
    }
}
