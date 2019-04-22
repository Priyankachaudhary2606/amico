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
	
	/**
	 * This method will be used to return the message body of email while mailing text information on linked user email
	 * @param info_key
	 * @param info_content
	 * @return
	 */
	
	public String emailTextMessageRetrieveInfo(String info_key, String info_content) {
		LOGGER.debug("Received request to get the body of Email while retrieving information"+info_key+info_content);
		String emailMessage=null;
		emailMessage="<font color=darkblue>Hi,<br><br>";
		emailMessage += "Please find your "+info_key+" : <br><br><b>";
		emailMessage += info_key+"</b> is <b>"+info_content+"</b><br>";
		emailMessage += "<br>Have a nice day.<br><br>Regards,</font><br>";
		emailMessage += "<font color=#c14b7a><b>AMIGO</b></font>";
		LOGGER.debug("Retrurning email message body for retrieving text info over mail"+emailMessage);
		return emailMessage;
	}
	
	/**
	 * This method will be used to return the message body of email while mailing text information on linked user email
	 * @param info_key
	 * @param info_content
	 * @return
	 */
	
	public String emailTextMessageShareInfo(String sender, String senderName, String receiver, String info_key, String info_content) {
		LOGGER.debug("Received request to get the body of Email while Sharing information"+receiver+info_key+info_content);
		String emailMessage=null;
		emailMessage="<font color=darkblue>Hi,<br><br>";
		emailMessage += "Please find "+info_key.toLowerCase()+" : <br><br><b>";
		emailMessage += info_key+"</b> is <b>"+info_content+"</b><br>";
		emailMessage += "<br>Have a nice day.<br><br>From,<br>";
		emailMessage += senderName +"</font><br>";
		emailMessage += "<font color=#c14b7a><b>"+ sender +"</b></font><br>";
		emailMessage += "<font color=#c14b7a><b>AMIGO user</b></font>";
		LOGGER.debug("Retrurning email message body for retrieving text info over mail"+emailMessage);
		return emailMessage;
	}

/**
 * This method will be used to Email File message to user's email
 * @param email
 * @param info_key
 * @return
 */
	
	public String emailFileMessageRetrieveInfo(String email, String info_key) {
		LOGGER.debug("Received request to get the body of Email while Sharing information"+email+info_key);
		String emailMessage=null;
		emailMessage="<font color=darkblue>Hi,<br><br>";
		emailMessage += "Please find your "+info_key+" attached with the mail<br><br>";
		emailMessage += "<br>Have a nice day.<br><br>Regards,</font><br>";
		emailMessage += "<font color=#c14b7a><b>AMIGO</b></font>";
		LOGGER.debug("Retrurning email message body for retrieving text info over mail"+emailMessage);
		return emailMessage;
	}

/**
 * This method will be used to return the message body of email while mailing file information to receiver's email
 * @param sender
 * @param receiver
 * @param info_key
 * @param info_content
 * @return
 */
	public String emailFileMessageShareInfo(String sender, String senderName, String receiver, String info_key) {
		LOGGER.debug("Received request to get the body of Email while Sharing information"+receiver+info_key);
		String emailMessage=null;
		emailMessage="<font color=darkblue>Hi,<br><br>";
		emailMessage += "Please find "+info_key+" attached with the mail.<br><br>";
		emailMessage += "<br>Have a nice day.<br><br>From,<br>";
		emailMessage += senderName +"</font><br>";
		emailMessage += "<font color=#c14b7a><b>"+ sender +"</b></font><br>";
		emailMessage += "<font color=#c14b7a><b>AMIGO user</b></font>";
		LOGGER.debug("Retrurning email message body for retrieving text info over mail"+emailMessage);
		return emailMessage;
	}

	
	public String forgotPasscode(int passcode) {
		LOGGER.debug("Received request to get the body of email while sending the reset password");
		String emailMessage=null;
		emailMessage="<font color=darkblue>Hi,<br><br>";
		emailMessage += "This is your new passcode <b>"+passcode+"</b> to unlock your personal information with JERRY";
		emailMessage += "<br>Keep it safe & confidential.<br><br>From,<br>";
		emailMessage += "<font color=#c14b7a><b>JERRY</b></font><br>";
		LOGGER.debug("Retrurning email message body for reseting password mail"+emailMessage);
		return emailMessage;
	}
}
