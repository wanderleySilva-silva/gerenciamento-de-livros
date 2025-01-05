package estudos.springboot.thymeleaf.security;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("administrador/home");
		} else {
			request.getSession().setAttribute("errorMessage", "Permissão negada.");
			response.sendRedirect("/login");
		}
	}
}
