package servlet;

import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import Thread.TokenThread;

public class InitServlet extends HttpServlet{
	

	public void init() throws ServletException {
		TokenThread.appid = getInitParameter("appid");    
        TokenThread.appsecret = getInitParameter("appsecret");
        System.out.println("InitServlet started and appid = "+TokenThread.appid+" appsecret =  "+ TokenThread.appsecret);
        
        new Thread(new TokenThread()).start();
	}

	
	
}
