package sg.nus.iss.team8.demo;

import javax.activation.DataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;


import sg.nus.iss.team8.demo.models.UserSession;
import sg.nus.iss.team8.demo.services.UserService;
import sg.nus.iss.team8.demo.services.UserServiceImplementation;

@Configuration
@EnableWebSecurity
@Controller
@SessionAttributes("user")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	javax.sql.DataSource dataSource;
	
	@Autowired
    SecurityHandler successHandler;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.sessionManagement().maximumSessions(2); // allow creation of 2 sessions only for authenticated user
		String[] staticResources  =  {	
		        "/src/main/resources/static/images/*",
		        "/images/**",
		        "/fonts/**",
		        "/scripts/**",
		    };
		
		http
				.authorizeRequests() // need to authorize the requests
				.antMatchers("/home").permitAll() // home can accessed by all
				.antMatchers("/logout").permitAll()
				.antMatchers("/logoutSuccess").permitAll()
				.antMatchers(staticResources).permitAll()				
				.antMatchers("/administrator/*").hasAuthority("Admin")
				.antMatchers("/facultymanagement").hasAuthority("Admin")
				.antMatchers("/addfaculty/*").hasAuthority("Admin")
				.antMatchers("/savefaculty/*").hasAuthority("Admin")
				.antMatchers("/editfaculty/*").hasAuthority("Admin")
				.antMatchers("/deletefaculty/*").hasAuthority("Admin")
				.antMatchers("/savecurrentsemester/*").hasAuthority("Admin")
				.antMatchers("/studentmanagement/*").hasAuthority("Admin")
				.antMatchers("/addstudent/*").hasAuthority("Admin")
				.antMatchers("/savestudent/*").hasAuthority("Admin")
				.antMatchers("/editstudent/*").hasAuthority("Admin")
				.antMatchers("/deletestudent/*").hasAuthority("Admin")
				.antMatchers("/courseapplication/*").hasAuthority("Admin")
				.antMatchers("/acceptcourse/*").hasAuthority("Admin")
				.antMatchers("/rejectcourse/*").hasAuthority("Admin")
				.antMatchers("/courserunmanagement/*").hasAuthority("Admin")
				.antMatchers("/addcourserun/*").hasAuthority("Admin")
				.antMatchers("/savecourserun/*").hasAuthority("Admin")
				.antMatchers("/editcourserun/*").hasAuthority("Admin")
				.antMatchers("/deletecourserun/*").hasAuthority("Admin")
				.antMatchers("/viewcourserun/*").hasAuthority("Admin")
				.antMatchers("/leaveapplication/*").hasAuthority("Admin")
				.antMatchers("/approveleave/*").hasAuthority("Admin")
				.antMatchers("/rejectleave/*").hasAuthority("Admin")
				.antMatchers("/departmentmanagement/*").hasAuthority("Admin")
				.antMatchers("/adddepartment/*").hasAuthority("Admin")
				.antMatchers("/savedepartment/*").hasAuthority("Admin")
				.antMatchers("/editdepartment/*").hasAuthority("Admin")
				.antMatchers("/deletedepartment/*").hasAuthority("Admin")
				.antMatchers("/admin_movementregister/*").hasAuthority("Admin")
				.antMatchers("/downloadCSV/*").hasAuthority("Admin")
				.antMatchers("/viewstudentcourses/*").hasAuthority("Admin")
				.antMatchers("/student/*").hasAuthority("Student")
				.antMatchers("/faculty/*").hasAnyAuthority("Faculty","Admin")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login").permitAll()
				.successHandler(successHandler)
				.and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
				.and();
				
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/logoutSuccess")
		.logoutSuccessHandler(logoutSuccessHandler());
		
	//	.logoutSuccessHandler(logoutSuccessHandler());
		//http.sessionManagement().invalidSessionUrl("/UserNotFound");
		
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.jdbcAuthentication()
		 .dataSource(dataSource)
		 .usersByUsernameQuery("select username,passwordHash,enabled from allusers where username =?")
		 .authoritiesByUsernameQuery("select username,userType from allusers where username =?")
		 .passwordEncoder(new BCryptPasswordEncoder());
		 
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();
	}
	
	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
	    return new CustomLogoutSuccessHandler();
	}
	 

//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		UserDetails user =
//			 User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("password")
//				.roles("USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(user);
//	}
//	

	
}