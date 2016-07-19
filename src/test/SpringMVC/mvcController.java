package test.SpringMVC;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import Thread.TokenThread;

@Controller
@RequestMapping("/mvc")
public class mvcController {
	
	@RequestMapping("/hello")
    public String hello(ModelMap model){
		String accessToken = TokenThread.accessToken.getToken();
		model.put("access_token", accessToken);
        return "hello";
    }
}
