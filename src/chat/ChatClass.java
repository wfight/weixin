package chat;

import java.io.DataInputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import chat.access_token.AccessTokenOld;

@Controller
@RequestMapping("/chat")
public class ChatClass {
	
	@RequestMapping("/getIp")
    public String getIp(ModelMap model){        
        AccessTokenOld token = AccessTokenOld.getInstance();
        String access_token = token.getAccessToken(false);
        model.addAttribute("access", access_token);
		String httpsURL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="+access_token;
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
				input.close(); 
				con.disconnect();

		        JSONObject jasonResult = new JSONObject(result);
		        JSONArray ipList = (JSONArray) jasonResult.get("ip_list");
		        if(ipList!=null && ipList.length()!=0){
		        	model.addAttribute("ipList",ipList);
		        	return "success";
		        }else{
		        	System.out.println("get the errcode" + jasonResult.get("errcode"));
		        	return "fail";
		        }
			}else{
				System.out.println("can not get the access_token and returnCode=" + returnCode);
				model.addAttribute("returnCode",returnCode);
				con.disconnect();

				return "hello";
			}
		} catch (Exception e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		}
		
		return "fail";
    }
}
