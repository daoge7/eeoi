package cn.ccsit.common.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Http请求工具类
 */
public class HttpRequestUtil {

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "application/json");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("content-Type",
					"application/x-www-form-urlencoded"); // 设置发送数据的格式
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = null;
			conn = (HttpURLConnection) realUrl.openConnection();
			// 打开和URL之间的连接

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST"); // POST方法

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "application/json;charset=UTF-8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8"); // 设置发送数据的格式

			conn.connect();
			OutputStream outputStream = conn.getOutputStream();
			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(outputStream, "UTF-8");
			// 发送请求参数
			out.write(param);
			// flush输出流的缓冲
			out.flush();
			result = readStrByCode(conn.getInputStream(), "UTF-8");
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String readStrByCode(InputStream is, String code) {
	    StringBuilder builder = new StringBuilder();
	    BufferedReader reader = null;
	    String line = "";
	    try{
	    	reader = new BufferedReader(new InputStreamReader(is, code));
	    	while ((line = reader.readLine()) != null){
	    		builder.append(line);
	    	}
	    }catch(Exception e){
	    	e.printStackTrace();
	    	try{
	    		reader.close();
	    	}
	    	catch (IOException e1) {
	    		e1.printStackTrace();
	    	}
	    }finally {
	    	try{
	    	  reader.close();
	    	}catch (IOException e) {
	    		e.printStackTrace();
	    	}
	    }
	    return builder.toString();
	}
	
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String filePath, String fileName) {
    	String cookie="";//HttpLogin.getEquasisCookie();
        BufferedReader in = null;
        try {
            String urlNameString = param==null ? url : (url + "?" + param);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection.setRequestProperty("Content-Encoding", "gzip");
            connection.setRequestProperty("Content-Type", "application/pdf");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cookie", cookie);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
            // 建立实际的连接
            connection.connect();
            InputStream ins= connection.getInputStream();
            fileName=filePath + File.separator + fileName;
            
            OutputStream os = new FileOutputStream(new File(fileName));
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            	os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
            return fileName;
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
	
	/**
	 * 编码
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncode(String source, String encode) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "0";
		}
		return result;
	}

	public static String urlEncodeGBK(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "0";
		}
		return result;
	}

//	public static String getUrlResult(String serviceName,String serviceMethod,String params){
//		String rs = HttpRequestUtil.sendPost(ConfigUtil.getPropertyVal("cfg.properties","ssmis_data_server_url"), "serviceName="+serviceName
//				+"&serviceMethod="+serviceMethod+"&"+params);
//		return rs;
//	}
//
//	public static String getOeMisUrlResult(String serviceName,String serviceMethod,String params){
//		String postStr = ConfigUtil.getPropertyVal("cfg.properties","oemis_data_server_url")+ "/" +serviceName+"/"+serviceMethod;
//		String rs = HttpRequestUtil.sendPost(postStr, params);
//		return rs;
//	}
//
//	public static String getSdsUrlResult(String serviceName,String serviceMethod,String params){
//		String postStr = ConfigUtil.getPropertyVal("cfg.properties","sds_data_server_url")+"/"+serviceName+"/"+serviceMethod;
//		String rs = HttpRequestUtil.sendPost(postStr, params);
//		return rs;
//	}
//
//	public static String getCsmUrlResult(String serviceName,String serviceMethod,String params){
//		String postStr = ConfigUtil.getPropertyVal("cfg.properties","csm_data_server_url")+"/"+serviceName+"/"+serviceMethod;
//		String rs = HttpRequestUtil.sendPost(postStr, params);
//		return rs;
//	}
	
	public static void main(String[] args) {
//		String p = HttpRequestUtil.sendGet( "http://suppliernet.ccs.org.cn/ccs/serarchListCcsSuClient.do", "serviceId=TM&certCode=&clientNameCn=&dueDate=&country=&page=1&rows=10");
//		String p = HttpRequestUtil.sendPost( "http://172.26.200.180:7001/ssmis-data-server/ws/csmService/loginApp", "paramJson={'userName':'jushuairan','pwd':'123456'}");
//		String p = HttpRequestUtil.sendPost( "http://172.26.200.180:7001/ssmis-java/app/jpush", "{'pushPersonId':'system','title':'ssmis2015消息推送','msg':'测试消息推送，请勿回复','alias':'0166370','pushRange':'3' }");
		/*String p1 = HttpRequestUtil.sendPost(
				"http://127.0.0.1:8080/ds/ws/ssmisService/testRestPost",
				"info=222");
		System.out.println(p1);
		customAddressJson={'id':'','':'','':'','':'','':'','':''};
		String g = HttpRequestUtil.sendGet(
				"http://127.0.0.1:8080/ds/ws/ssmisService/testRestGet",
				"info=1111");
				System.out.println(g);
				*/
//		String p = HttpRequestUtil.sendPost( "http://172.24.2.164/csm/ws/appService/saveCustomAddress", "customAddressJson={\"id\":\"\",\"name\":\"name55\",\"name_en\":\"name_en4\",\"contactPerson\":\"contactPerson\",\"contactPerson_en\":\"contactPerson_en\",\"contactPersonPosition\":\"contactPersonPosition\",\"regAddress\":\"regAddress\",\"officeAddress\":\"officeAddress\",\"officeAddress_en\":\"officeAddress_en\",\"postCode\":\"postCode\",\"telNumber\":\"telNumber\",\"fax\":\"fax\",\"email\":\"email\",\"companyId\":\"1\",\"orgType\":\"1\",\"isDefault\":\"1\", \"isValid\":\"1\"}");
		String p = HttpRequestUtil.sendPost( "http://172.24.2.164/csm/ws/appService/removeCustomAddress", "paramJson={'id':'2c988224634df27401634df541d60000'}");
//		String p = HttpRequestUtil.sendPost( "http://172.26.200.180:7001/ssmis-java/app/jpush", "paramInfo={'pushPersonId':'system','title':'ssmis2015消息推送','msg':'测试消息推送，请勿回复','alias':'0166370','pushRange':'3' }");

		System.out.println(p);
//		String g = HttpRequestUtil.sendGet(
//				"http://172.24.2.142/sd/ws/ssmisService/testRestGet",
//				"info=1111");
		
//		CustomAddress o =new CustomAddress();
//		o.setCompanyId("1");
//		o.setContactPerson("contactPerson");
//		o.setContactPerson_en("contactPerson_en");
//		o.setContactPersonPosition("contactPersonPosition");
//		o.setEmail("email");
//		o.setFax("fax");
//		o.setId("");
//		o.setIsDefault("1");
//		o.setName("name");
//		o.setName_en("name_en");
//		o.setOfficeAddress("officeAddress");
//		o.setOfficeAddress_en("officeAddress_en");
//		o.setOrgType("1");
//		o.setPostCode("postCode");
//		o.setRegAddress("regAddress");
//		o.setTelNumber("telNumber");
//		Gson gson = new Gson();
//		System.out.println(gson.toJson(o));
		
	}

}
