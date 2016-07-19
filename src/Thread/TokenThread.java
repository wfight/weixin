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
				System.out.println("��ȡaccess_token�ɹ�����Чʱ��{}�� token:{}"+ accessToken.getExpiresIn()+"  token  "+accessToken.getToken());
				// ����7000��
				Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
			} else {
				// ���access_tokenΪnull��60����ٻ�ȡ
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
