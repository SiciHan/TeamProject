package sg.nus.iss.team8.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CustomLogoutSuccessHandler extends
SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
	
	@Override
    public void onLogoutSuccess(
      HttpServletRequest request, 
      HttpServletResponse response, 
      Authentication authentication) 
      throws IOException, ServletException {
		
		
		
		System.out.println("Logged Out Successful");

	//	System.out.println(authentication.getPrincipal());
  
       // String refererUrl = request.getHeader("Referer");
      //  auditService.track("Logout from: " + refererUrl);
		
		 if (authentication != null) {
	            try {
	            	 System.out.println("User Successfully Logout");
	            	request.getSession().invalidate();     
	            	 response.sendRedirect(request.getContextPath() + "/logoutSuccess");
	            	 System.out.println("end of logout!");
	                //you can add more codes here when the user successfully logs out,
	                //such as updating the database for last active.
	            } catch (Exception e) {
	            	
	            	//java.lang.NullPointerException
	                e.printStackTrace();
	            }
	        }
		 
		
	 
	//	 response.setStatus(HttpServletResponse.SC_OK);
	        //redirect to login
	//	 response.sendRedirect("/login");
 
    //    super.onLogoutSuccess(request, response, authentication);
    }
	

}
