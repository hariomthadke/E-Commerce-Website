package com.nt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
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
                        // Secure all GET and POST requests for specific endpoints
                        .requestMatchers(HttpMethod.GET, "/admin", "/display*", "/delete*", "/updateCategory","/notify",
                                "/updateProduct", "/userProfile", "/showCart", "/removeCart", "/wishlist").authenticated()
                        .requestMatchers(HttpMethod.POST, "/registerAdmin", "/update*", "/addProduct", "/addCategory",
                                "/addToCart", "/checkout", "/placeOrder").authenticated()
                        // Allow unrestricted access to all other endpoints
                        .anyRequest().permitAll();
                })
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection
                .formLogin(login -> {
                    login
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(customAuthenticationSuccessHandler);  // Use the injected handler
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                        .loginPage("/login")  // Replace with your OAuth2 login page
                        .defaultSuccessUrl("/oauth")  // Redirect to this only if no original request
                        .failureUrl("/login?error=true")  // URL to redirect to if login fails
                        .userInfoEndpoint()
                        .userAuthoritiesMapper(authorities -> AuthorityUtils.createAuthorityList("user"));  // Assign role
                })
                .logout(logout -> logout
                    .logoutUrl("/logout")  // URL to trigger the logout
                    .logoutSuccessUrl("/login?logout")  // URL to redirect to after logout
                    .invalidateHttpSession(true)  // Invalidate the HTTP session
                    .deleteCookies("JSESSIONID")  // Delete the JSESSIONID cookie
                    .addLogoutHandler((request, response, authentication) -> {
                        HttpSession session = request.getSession(false);
                        if (session != null) {
                            session.invalidate();  // Clear the session
                        }
                    })
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.sendRedirect("/login?logout");  // Redirect after logout
                    })
                )
                .exceptionHandling(handling -> handling.accessDeniedPage("/accessDenied"));

		return http.build();
	}
}
