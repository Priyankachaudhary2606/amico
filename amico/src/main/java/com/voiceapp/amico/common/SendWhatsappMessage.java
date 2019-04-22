package com.voiceapp.amico.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SendWhatsappMessage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendWhatsappMessage.class);
	
	public int sendTextWhatsappMessage(String email, String receiverContact, String info_key, String messageBody) {
		try {
			LOGGER.debug("Received request to send Whatsapp Message in utility class - SendWhatsappMessage");
			
			URL url = new URL("https://www.waboxapp.com/api/send/chat"); 
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
			con.setRequestMethod("POST");
			String urlParams= "token=207aba7a277e8025cf758381768680d95cbd7825befdf&uid=918527526472&to=91"+receiverContact+"&custom_uid=msg-1171&text="+messageBody;
			con.setRequestProperty("Content-length", String.valueOf(urlParams.length()));
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParams);
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			LOGGER.debug("Sending 'POST' request to URL : " + url);
			LOGGER.debug("Post Parameters"+urlParams);
			LOGGER.debug("Response Code"+ responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			LOGGER.debug("Response "+response.toString());
			
			LOGGER.debug("Sent whatsapp message successfully");
			LOGGER.debug("Returning 1");
			return 1;
		} catch (Exception e) {
			LOGGER.error("Couldn't send whatsapp message"+e);
			LOGGER.error("Returning 0");
			return 0;
		}
			
	}
	
	
	public int sendDocOverWhatsapp(String receiverContact) {
		try {
			LOGGER.debug("Received request to send Whatsapp Message in utility class - SendWhatsappMessage");
			
			URL url = new URL("https://www.waboxapp.com/api/send/media"); 
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
			con.setRequestMethod("POST");
			String urlParams= "token=207aba7a277e8025cf758381768680d95cbd7825befdf&uid=918527526472&to=91"+receiverContact+"&custom_uid=msg-1171&text=https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
			con.setRequestProperty("Content-length", String.valueOf(urlParams.length()));
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParams);
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			LOGGER.debug("Sending 'POST' request to URL : " + url);
			LOGGER.debug("Post Parameters"+urlParams);
			LOGGER.debug("Response Code"+ responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			LOGGER.debug("Response "+response.toString());
			
			LOGGER.debug("Sent document over whatsapp message successfully");
			LOGGER.debug("Returning 1");
			return 1;
		} catch (Exception e) {
			LOGGER.error("Couldn't send document whatsapp message"+e);
			LOGGER.error("Returning 0");
			return 0;
		}
		
		
	}
	
	
	 
	
	
}
