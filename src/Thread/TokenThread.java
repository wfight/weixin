package Thread;
import chat.access_token.AccessToken;
import util.WeiXinUtil;

public class TokenThread implements Runnable{
	public static String appid = "";
	public static String appsecret = "";
	public static AccessToken accessToken = null;

	
	public void run(){
		try {
			accessToken = WeiXinUtil.getAccessToken(appid, appsecret);
			if (null != accessToken) {
				System.out.println("获取access_token成功，有效时长{}秒 token:{}"+ accessToken.getExpiresIn()+"  token  "+accessToken.getToken());
				// 休眠7000秒
				Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
			} else {
				// 如果access_token为null，60秒后再获取
				Thread.sleep(60 * 1000);
			}
		} catch (InterruptedException e) {
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e1) {
				System.out.println(e1);
			}
		}
	}
	
}
