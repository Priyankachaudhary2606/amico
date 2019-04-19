package com.voiceapp.amico.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author priyankachoudhary
 * This is the class used to get message body of mails while sending some information over a mail
 *
 */
@Component
public class EmailBodyUtility {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailBodyUtility.class);
	
	public String emailTextMessageRetrieveInfo(String info_key, String info_content) {
		LOGGER.debug("Received request to get the body of Email while retrieving information"+info_key+info_content);
		String emailMessage=null;
		emailMessage="<font color=darkblue>Hi,<br><br>";
		emailMessage += "Please find your "+info_key+" : <br><br><b>";
		emailMessage += info_key+"</b> is <b>"+info_content+"</b><br>";
		emailMessage += "<br>Have a nice day.<br><br>Regards,</font><br>";
		emailMessage += "<font color=#c14b7a><b>AMIGO</b></font>";
		LOGGER.debug("Retrurning email message body for retrieving text info over mail");
		return emailMessage;
	}

}
