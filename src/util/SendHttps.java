package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import chat.access_token.AccessToken;

public class SendHttps {

	public static JSONObject getMessageFromHttps(String url) {
		String httpsURL = url;
		StringBuffer buffer = new StringBuffer();
		JSONObject jsonObject = null;
		try {

			URL myurl = new URL(httpsURL);
			HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
			con.setRequestMethod("GET"); // 必须是get方式请求
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.connect();
			
			// 将返回的输入流转换成字符串
			InputStream inputStream = con.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			con.disconnect();
			jsonObject = new JSONObject(buffer.toString());
			con.disconnect();
		} catch (Exception e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
}
