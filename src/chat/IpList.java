package chat;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


import Thread.TokenThread;
import util.SendHttps;

public class IpList {

	public IpList(){
		
	}
	
	public List<String> getIpList(){
		List<String> ipList = new ArrayList<String>();
		String getIpUrl = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=" + TokenThread.accessToken.getToken();
		JSONObject jsonObject = SendHttps.getMessageFromHttps(getIpUrl);
		if(jsonObject != null) {
			try {
				ipList = (List<String>) jsonObject.get("ip_list");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ipList;
	}
}
