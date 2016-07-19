package chat.authenticate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

import model.InputMessage;

import org.w3c.dom.Document; 
import org.w3c.dom.Element; 
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 


@Controller
@RequestMapping("/")
public class Authenticate {
	
	private final String Token = "12345zxcvb";
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody  
	public void checkAuthenticate(HttpServletRequest request, HttpServletResponse response){
		System.out.println("ccc");
		String signature = request.getParameter("signature");  
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");  
        String echostr = request.getParameter("echostr");  
        System.out.println(signature);  
        System.out.println(timestamp);  
        System.out.println(nonce);  
        System.out.println(echostr);
        String ifSuccess = "";
        if(signature!=null && timestamp!=null && nonce !=null && echostr!=null)
        	ifSuccess = checkIfFromWinXin(request,response);
        else
        	System.out.println("Authenticate is failed");
	}

	private String checkIfFromWinXin(HttpServletRequest request, HttpServletResponse response) {
		 	System.out.println("������֤�Ƿ�����΢��");  
	        String signature = request.getParameter("signature");// ΢�ż���ǩ��  
	        String timestamp = request.getParameter("timestamp");// ʱ���  
	        String nonce = request.getParameter("nonce");// �����  
	        String echostr = request.getParameter("echostr");// ����ַ���  
	        List<String> params = new ArrayList<String>();  
	        params.add(Token);  
	        params.add(timestamp);  
	        params.add(nonce);  
	        // 1. ��token��timestamp��nonce�������������ֵ�������  
	        Collections.sort(params, new Comparator<String>() {  
	            @Override  
	            public int compare(String o1, String o2) {  
	                return o1.compareTo(o2);  
	            }  
	        });  
	        // 2. �����������ַ���ƴ�ӳ�һ���ַ�������sha1����  
	        String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));  
	        if (temp.equals(signature)) {  
	            try {  
	                response.getWriter().write(echostr);  
	                System.out.println("�ɹ����� echostr��" + echostr);  
	                return "success";  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        System.out.println("ʧ�� ��֤");  
	        return "fail";  
	}
	
	@RequestMapping(method = RequestMethod.POST)
    @ResponseBody 
	public static String getMessage(HttpServletRequest request, HttpServletResponse response) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException {
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		DocumentBuilder db = dbf.newDocumentBuilder();     
//        Document doc = db.parse(request.getInputStream());
//        
//		String name = getValueByTagName(doc, "ToUserName"); //ToUserName developer loginname
//		response.getWriter().write("");  //response to server
		XStream xstream = new XStream();
		xstream.processAnnotations(InputMessage.class);  
		ObjectInputStream  in = xstream.createObjectInputStream(request.getInputStream());
		InputMessage inputmessage = (InputMessage)in.readObject();
		return inputmessage.getContent();
	}
	
	public String getValueByTagName(Document doc ,String name){
		if(doc ==null){
			return "";
		}
		NodeList p1 = doc.getElementsByTagName(name);
		if(p1!=null && p1.getLength() >0){
			return String.valueOf(p1.item(0).getTextContent());
		}
		return "";
		
	}
}
