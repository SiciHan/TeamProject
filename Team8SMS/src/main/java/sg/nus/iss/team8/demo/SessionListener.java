package sg.nus.iss.team8.demo;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener{
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
	    se.getSession().setMaxInactiveInterval(5);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

	}}


