package org.storage;

import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class UrlConnectionHelper {


	private HttpURLConnection urlConn; 
	

    public String getUrl(String urlString, String username, String password, Map<String, String> headers)
    {
		String result = null;
		try {
			URL url;
			url = new URL(urlString);
			urlConn = ( HttpURLConnection ) url.openConnection();
			urlConn.setRequestMethod("GET");
			//urlConn.setUseCaches(false);
			if (username != null) {
				String encoding = new sun.misc.BASE64Encoder().encode((username + ":" + password).getBytes());
			    urlConn.setRequestProperty("Authorization", "Basic "+ encoding);
			}
			if (headers != null){
				for (String key : headers.keySet()) {
					urlConn.setRequestProperty(key, headers.get(key));
				}
			}

			StringWriter writer = new StringWriter();
			IOUtils.copy(urlConn.getInputStream(), writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

    }


	public String postStringToUrl(String postString, String urlString, Map<String, String> headers) {
		return this.postStringToUrl(postString, urlString, null, null, headers);
	}


	public String postStringToUrl(String postString, String urlString, String username, String password, Map<String, String> headers) {
		String result = null;
		try {
			URL url;
			DataOutputStream outputStream;
			url = new URL(urlString);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			//urlConn.setUseCaches(false);
			if (username != null) {
				String encoding = new sun.misc.BASE64Encoder().encode((username + ":" + password).getBytes());
			    urlConn.setRequestProperty("Authorization", "Basic "+ encoding);
			}
			if (headers != null){
				for (String key : headers.keySet()) {
					urlConn.setRequestProperty(key, headers.get(key));
				}
			} else {
	            // leave line below for Marcom : publish-to-www.post.json.js			
				urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			}
			outputStream = new DataOutputStream(urlConn.getOutputStream());
			outputStream.writeBytes(postString);
			outputStream.flush();
			outputStream.close();
			StringWriter writer = new StringWriter();
			IOUtils.copy(urlConn.getInputStream(), writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	public String putStringToUrl(String postString, String urlString, Map<String, String> headers) {
		return this.putStringToUrl(postString, urlString, null, null, headers);
	}	

	public String putStringToUrl(String putString, String urlString, String username, String password, Map<String, String> headers) {
		String result = null;
		try {
			URL url;
			DataOutputStream outputStream;
			url = new URL(urlString);
			urlConn = ( HttpURLConnection ) url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("PUT");
			//urlConn.setUseCaches(false);
			if (username != null) {
				String encoding = new sun.misc.BASE64Encoder().encode((username + ":" + password).getBytes());
			    urlConn.setRequestProperty("Authorization", "Basic "+ encoding);
			}
			if (headers != null){
				for (String key : headers.keySet()) {
					urlConn.setRequestProperty(key, headers.get(key));
				}
			} 
				
			outputStream = new DataOutputStream(urlConn.getOutputStream());
			outputStream.writeBytes(putString);
			outputStream.flush();
			outputStream.close();
				
			StringWriter writer = new StringWriter();
			IOUtils.copy(urlConn.getInputStream(), writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
    public String getHeaderField(String name)
    {
    	return urlConn.getHeaderField(name);
    }	
	
}
