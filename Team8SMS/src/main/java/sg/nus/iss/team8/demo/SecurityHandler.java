package sg.nus.iss.team8.demo;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component("myAuthenticationSuccessHandler")
public class SecurityHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	 private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.Authentication authentication) throws IOException, ServletException {

	//	setUseReferer(true);

		System.out.println("Logged in Success");
		HttpSession session = request.getSession();
		handle(request, response, authentication);
		if (session != null) {

			System.out.println(authentication.getPrincipal().toString());
			System.out.println(authentication.getName());
		}

		
		session.setMaxInactiveInterval(5); //30 mins
	
		
		

	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			logger.debug(" Unable to redirect to " + targetUrl);
			return;
		}
		
		 redirectStrategy.sendRedirect(request, response, targetUrl);
		 
	}

	protected String determineTargetUrl(Authentication authentication) {
		boolean isStudent = false;
		boolean isAdmin = false;
		boolean isFaculty = false;

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("Admin")) {
				isAdmin = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("Faculty")) {
				isFaculty = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("Student")) {
				isStudent = true;
				break;
			}
		}

		if (isStudent) {
			return "/student/applycourse";
		} else if (isAdmin) {
			return "/facultymanagement"; // change to administrator 
		} else if (isFaculty) {
			return "/faculty/home";
		} else {
			throw new IllegalStateException();
		}
	}
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
