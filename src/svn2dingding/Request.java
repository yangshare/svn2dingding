package svn2dingding;
/**
* @author 杨成 E-mail:yangcheng@wiswit.com
* @version 创建时间：2017年4月11日 下午3:02:37
* 类说明
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Request {
	public static void main(String[] args) throws Exception {
		String token = args[0];
		String content = args[1];
		content = "{\"msgtype\": \"text\",\"text\": {\"content\": \""+content+"\"},\"at\": {\"isAtAll\": true}}";  
		httpsRequest("https://oapi.dingtalk.com/robot/send?access_token="+token, "POST", content);
		System.out.println("OK");
		System.exit(0);
	}

	/**
	 * 发送https请求
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
		HttpsURLConnection conn = null;
		BufferedReader bufferedReader = null;
		try {
			URL url = new URL(requestUrl);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/json");
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("utf-8"));
				outputStream.close();
			}
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			return buffer.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
