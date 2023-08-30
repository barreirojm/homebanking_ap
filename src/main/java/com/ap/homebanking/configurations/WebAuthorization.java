package com.ap.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.springframework.security.config.http.MatcherType.ant;

@EnableWebSecurity
@Configuration
public class WebAuthorization {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http.authorizeRequests()

                /*// ALL
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/logout").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers("/web/index.html", "/web/js/index.js", "/web/css/style.css", "/web/img/**").permitAll()

                // CLIENT
                .antMatchers("/api/clients/current").hasAuthority("CLIENT")
                .antMatchers("/api/accounts", "/api/accounts/{id}").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/transactions").hasAuthority("CLIENT")
                .antMatchers("/api/transactions").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers("/web/accounts.html", "/web/account.html", "/web/cards.html", "/web/create-cards.html", "/web/transfers.html").hasAuthority("CLIENT")
                .antMatchers("/web/css/cards.css", "/web/js/account.js","/web/js/accounts.js","/web/js/cards.js", "/web/js/create-cards.js", "/web/js/transfers.js").hasAuthority("CLIENT")

                // ADMIN
                .antMatchers("/api/clients").hasAuthority("ADMIN")
                .antMatchers("/api/clients/{id}").hasAuthority("ADMIN")
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console").hasAuthority("ADMIN")
                .antMatchers("/manager.html", "manager.js").hasAuthority("ADMIN")

                .anyRequest().denyAll()*/

                .antMatchers("/**").permitAll()

                ;

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }
}
