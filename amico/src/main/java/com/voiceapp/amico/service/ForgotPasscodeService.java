package com.voiceapp.amico.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voiceapp.amico.common.AppPasscodeUtility;
import com.voiceapp.amico.common.EmailBodyUtility;
import com.voiceapp.amico.common.ReadResponseMessages;
import com.voiceapp.amico.common.SendMailUtility;
import com.voiceapp.amico.dao.ForgotPasscodeDao;


@Service
public class ForgotPasscodeService {
	
	@Autowired
	private ForgotPasscodeDao forgotPasscodeDao;
	
	@Autowired
	private ReadResponseMessages readResponseMessages;
	
	@Autowired
	private AppPasscodeUtility appPasscodeUtility;
	
	@Autowired
	private EmailBodyUtility emailBodyUtility;
	
	@Autowired
	private SendMailUtility sendMailUtility;
	
	String forgotPasscodeResponse;

	private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasscodeService.class);
	
	public String forgotPasscode(String email) {
		LOGGER.debug("Request received in Service to reset Passcode when user forgets one");
		LOGGER.debug("Calling method to check if passcode exists");
		int checkPasscodeExistStatus=appPasscodeUtility.checkPasscodeExists(email);
		LOGGER.debug("Received response if passcode exists or not" +checkPasscodeExistStatus);
		if(checkPasscodeExistStatus==1) {
			LOGGER.debug("Passcode exists for user"+email);
			LOGGER.debug("Generating new passcode");
			int generatedPasscode = (int)(Math.random()*9000)+1000;
			LOGGER.debug("Calling mehod to add new passcode --"+generatedPasscode+" in the system");
			int passcodeResetStatus = forgotPasscodeDao.resetPasscode(email, generatedPasscode);
			LOGGER.debug("status of passcode is"+passcodeResetStatus);
			if(passcodeResetStatus==1) {
				String messageBody=emailBodyUtility.forgotPasscode(generatedPasscode);
				LOGGER.debug("Passcode saved successfully");
				LOGGER.debug("Sharing passcode over user's mail ID");
				int mailStatus=sendMailUtility.sendTextInformationOverMail(email, "New Passcode", messageBody);
				if(mailStatus==0) {
					LOGGER.debug("Mail could'nt be sent");
					forgotPasscodeResponse=readResponseMessages.getMailedUnsuccessful();
				}
				else {
					LOGGER.debug("Passcode has been reset & mailed suucessfuly");
						forgotPasscodeResponse=readResponseMessages.getNewPasscodeMailed();
				}
			}
			else {
				LOGGER.debug("Passcode couldn't be rest ");
				forgotPasscodeResponse=readResponseMessages.getApplicationFailMessage();
			}
		}
		else {
			LOGGER.debug("Passcode doesn't exist");
			forgotPasscodeResponse=readResponseMessages.getNoPasscodeExistsToReset();
		}
		
		LOGGER.debug("Returning response from service for goolgle assistant"+forgotPasscodeResponse);
		return forgotPasscodeResponse;
		
	}
	
	
}
