package com.nt.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.nt.model.Admins;
import com.nt.model.Users;
import com.nt.service.IAdminsService;
import com.nt.service.IUsersService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private IUsersService userService;

	@Autowired
	private IAdminsService adminService;

	//On Authentication success
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		System.out.println("CustomAuthenticationSuccessHandler.onAuthenticationSuccess()");
		Object principal = authentication.getPrincipal();

		if (principal instanceof MyUserDetails) {
			MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
			HttpSession session = request.getSession();
			String role= userDetails.getRole();
			if(role.equals("user")) {
				Optional<Users> us=userService.findByUsername(userDetails.getUsername());
				if(us.isPresent()) {
					session.setAttribute("activeUser", us.get());
					session.setAttribute("activeUserRole", role);
					response.sendRedirect("/");
				}

			}else if(role.equals("admin")) {
				Optional<Admins> ad=adminService.findByUsername(userDetails.getUsername());
				if(ad.isPresent()) {
					session.setAttribute("activeUser", ad.get());
					session.setAttribute("activeUserRole", role);
					response.sendRedirect("/admin");
				}	
			}
		}
	}
}
