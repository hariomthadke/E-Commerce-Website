package com.nt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpSession;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


	public WebSecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
		this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
	}


    @Bean
    UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth
                            // Secure all GET requests for specific endpoints
                            .requestMatchers(HttpMethod.GET, "/admin", "/display*", "/delete*", "/updateCategory",
                                    "/updateProduct", "/userProfile", "/showCart",
                                    "/removeCart", "/wishlist").authenticated()  // Secure only this endpoint

                            // Secure all POST requests for specific endpoints
                            .requestMatchers(HttpMethod.POST, "/registerAdmin", "/update*", "/addProdut", "/addCategory",
                                    "/addToCart", "/checkout", "/placeOrder").authenticated()
                            // Allow unrestricted access to all other endpoints
                            .anyRequest().permitAll();
                })
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection
                .formLogin(login -> {
                    login
                            .loginPage("/login")
                            .permitAll()
                            .successHandler(customAuthenticationSuccessHandler); // Use the injected handler
                })
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL to trigger the logout
                        .logoutSuccessUrl("/login?logout")  // URL to redirect to after logout
                        .invalidateHttpSession(true)  // Invalidate the HTTP session
                        .deleteCookies("JSESSIONID")  // Delete the JSESSIONID cookie
                        .addLogoutHandler((request, response, authentication) -> {
                            // Custom logout handler to perform additional actions
                            // For example, clearing custom session attributes
                            HttpSession session = request.getSession(false);
                            if (session != null) {
                                session.removeAttribute("activeUser");
                            }
                        })
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Additional actions on successful logout
                            response.sendRedirect("/login?logout");
                        }))
                .exceptionHandling(handling -> handling.accessDeniedPage("/accessDenied"));

		return http.build();
	}
}
