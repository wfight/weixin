package chat.access_token;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access")
public class AccessTokenOld {
	
	private AccessTokenOld(){
		Runnable myRunnable = new Runnable(){
			public void run(){
				while(true){
					try {
						Thread.sleep(1000*expires_in);
						//getexprint and accesstoken;
						sendHttpsRequest();
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		Thread thread = new Thread(myRunnable);
		thread.start();
	}
	
	private String access_token = "";
	
	private long expires_in =0;
	
	private static final AccessTokenOld accessToken = new AccessTokenOld();
	
	public static AccessTokenOld getInstance() {
		return accessToken;
	}
	
	public String getAccessToken (boolean flag) { // if flag is true ,then get the token from the weixin service , else get the token in expires_in;
		if(flag == true){
			sendHttpsRequest();
			return access_token;
		}else {
			if(access_token == null || access_token == ""){
				sendHttpsRequest();
			} else {
				return access_token;
			}
		}
		return access_token;
	}
	
	@RequestMapping("/token")
	public String getAccessToken (ModelMap model) { // if flag is true ,then get the token from the weixin service , else get the token in expires_in;
		access_token = "111";
		sendHttpsRequest();
		if(access_token == null || access_token == "")
			access_token = "111";
		model.addAttribute("access_token", access_token);
		return "hello";
	}
	
	private void sendHttpsRequest(){
		String httpsURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxce59db7d468079f4&secret=76ed35097489a0eff0dc6189427a5497";
		try {
	
			URL myurl = new URL(httpsURL);
			HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
			con.setRequestMethod("GET"); // 必须是get方式请求
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			con.setDoOutput(true);
			con.setDoInput(true);
			
			int returnCode = con.getResponseCode();
			if(returnCode == 200){
				DataInputStream input = new DataInputStream( con.getInputStream() ); 
				String result = "";
				while(input.readLine() !=null ){ 
					result = result + input.readLine();
				}
		        JSONObject jasonResult = new JSONObject(result);
		        String temp_access_token = (String) jasonResult.get("access_token");
		        long temp_expires_in = (long) jasonResult.get("expires_in");
		        if(temp_access_token!=null && temp_expires_in!=0){
		        	access_token = temp_access_token;
		        	expires_in = temp_expires_in;
		        }else{
		        	System.out.println("get the errcode" + jasonResult.get("errcode"));
		        }
				input.close(); 
			}else{
				System.out.println("can not get the access_token and returnCode=" + returnCode);
			}
			con.disconnect();
			System.out.println("access_token:"+access_token); 
			System.out.println("expires_in"+ expires_in); 
		} catch (Exception e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
