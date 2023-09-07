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

                // ALL
                .antMatchers(HttpMethod.POST, "/api/login", "/api/logout", "/api/clients").permitAll()
                .antMatchers(HttpMethod.GET,"/web/index.html", "/web/js/index.js", "/web/css/style.css", "/web/img/**").permitAll()

                // CLIENT
                .antMatchers(HttpMethod.GET,
                        "/api/clients/current", "/api/accounts", "/api/accounts/{id}", "/api/clients/current/accounts",
                        "/api/transactions", "/api/clients/current/cards", "/api/loans", "/web/accounts.html", "/web/account.html",
                        "/web/cards.html", "/web/create-cards.html", "/web/transfers.html", "/web/loan-application.html",
                        "/web/css/cards.css", "/web/js/account.js","/web/js/accounts.js","/web/js/cards.js", "/web/js/create-cards.js",
                        "/web/js/transfers.js", "/web/js/loan-application.js"
                        ).hasAuthority("CLIENT")

                .antMatchers(HttpMethod.POST,"/api/clients/current/accounts", "/api/transactions",
                        "/api/clients/current/cards", "/api/loans"
                        ).hasAuthority("CLIENT")

                // ADMIN
                .antMatchers(HttpMethod.GET,"/api/clients", "/api/clients/{id}", "/rest/**", "/h2-console", "/manager.html", "manager.js"
                    ).hasAuthority("ADMIN")

                //
                .anyRequest().denyAll()

                //.antMatchers("/**").permitAll()

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
