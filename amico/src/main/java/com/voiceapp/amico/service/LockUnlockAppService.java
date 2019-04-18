package com.voiceapp.amico.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voiceapp.amico.common.AppPasscodeUtility;
import com.voiceapp.amico.common.ReadResponseMessages;
import com.voiceapp.amico.dao.LockUnlockDao;

/**
 * This is service class to lock or unlock app
 * using passcode by respective user
 * 
 * @author priyankachoudhary
 *
 */

@Service
public class LockUnlockAppService {
	
	@Autowired
	private LockUnlockDao lockUnlockDao;
	
	@Autowired
	private AppPasscodeUtility appPasscodeUtility;
	
	@Autowired
	private ReadResponseMessages readResponseMessages;
	
	int checkPasscodeStatus;
	int lockAppStatus;
	int unlockAppStatus;
	int savePasscodeStatus;
	int matchPasscodeStatus;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LockUnlockAppService.class);

/**
 * This method will be called by Lock Intent controller to lock the app
 * It will perform following tasks
 * 1. Check if Passcode Exists
 * 2. Check if passcode match
 * 3. Lock the app
 * 4. Save new passcode
 * @param email
 * @param passcode
 * @return
 */
	public String lockApp(String email, int passcode) {
		LOGGER.debug("Received request to lock personal information in Service LockUnlockAppService");
		String lockAppResponse=null;
		LOGGER.debug("Calling method in AppPasscodeUtility class to check if passcode exists");
		checkPasscodeStatus=appPasscodeUtility.checkPasscodeExists(email);
		LOGGER.debug("Status of Passcode existence is "+checkPasscodeStatus);
		if(checkPasscodeStatus==1) {
			LOGGER.debug("Passcode exists in app for the respective user");
			LOGGER.debug("Sending request to LockUnlock DAO to match the passcode");
			matchPasscodeStatus=lockUnlockDao.matchPasscode(email, passcode);
			LOGGER.debug("Status of matching the passcode is"+matchPasscodeStatus);
			if(matchPasscodeStatus==1) {
				LOGGER.debug("Passcode matched successfully");
				LOGGER.debug("Sending request to LockUnlock DAO to lock app");
				lockAppStatus=lockUnlockDao.lockApp(email, passcode);
				if(lockAppStatus==1) {
					LOGGER.debug("App locked successfully");
					lockAppResponse=readResponseMessages.getLockAppSuccessMessage();
				}
				else {
					LOGGER.debug("App couldn't be locked even after matching the lock");
					lockAppResponse=readResponseMessages.getLockAppFailedMessage();
				}
			}
			else {
				LOGGER.debug("Passcode didn't match");
				lockAppResponse=readResponseMessages.getPasscodeMismatch();
			}
		}
		else {
			LOGGER.debug("Passcode does not exist");
			LOGGER.debug("Adding the input passcode for lock as the new passcode");
			savePasscodeStatus=lockUnlockDao.savePasscode(email, passcode);
			if(savePasscodeStatus==1) {
				LOGGER.debug("Passcode saved successfully");
				LOGGER.debug("Sending request to lock the app");
				lockAppStatus=lockUnlockDao.lockApp(email, passcode);
				LOGGER.debug("Status of locking app is "+ lockAppStatus);
				if(lockAppStatus==1) {
					LOGGER.debug("Locked App successfully");
					lockAppResponse=readResponseMessages.getSaveCodeLockAppSuccessMessage1()+" "+passcode+" "+readResponseMessages.getSaveCodeLockAppSuccessMessage2();
				}
				else {
					LOGGER.debug("App couldn't be locked");
					lockAppResponse=readResponseMessages.getSaveCodeLockFailedMessage1()+" "+ passcode +" "+readResponseMessages.getSaveCodeLockFailedMessage2();
				}
			}
			else {
				LOGGER.debug("Application couldn't save the passcode");
				lockAppResponse=readResponseMessages.getLockAppFailedMessage();
			}
		}
		LOGGER.debug("Returning lock app response to Google Assistant"+lockAppResponse);
		return lockAppResponse;
	}
	
/**
 * This is the method invoked by UnlockApp Intent controller.
 * This will perform following tasks:
 * 1. Check if passcode exists
 * 2. Check if passcode match
 * 2. Unlock personal information
 * 3. Return response message
 * @param email
 * @param passcode
 * @return unlockAppStatus
 */
	public String unlockApp(String email, int passcode) {
		LOGGER.debug("Received request to unlock personal information in Service LockUnlockAppService");
		String unlockAppResponse=null;
		LOGGER.debug("Calling method in AppPasscodeUtility class to check if passcode exists");
		checkPasscodeStatus=appPasscodeUtility.checkPasscodeExists(email);
		LOGGER.debug("Status of Passcode existence is "+checkPasscodeStatus);
		if(checkPasscodeStatus==1) {
			LOGGER.debug("Passcode exists in app for the respective user");
			LOGGER.debug("Sending request to LockUnlock DAO to match the passcode");
			matchPasscodeStatus=lockUnlockDao.matchPasscode(email, passcode);
			LOGGER.debug("Status of matching the passcode is"+matchPasscodeStatus);
			if(matchPasscodeStatus==1) {
				LOGGER.debug("Passcode matched successfully");
				LOGGER.debug("Sending request to LockUnlock DAO to unlock app");
				unlockAppStatus=lockUnlockDao.unlockApp(email, passcode);
				if(unlockAppStatus==1) {
					LOGGER.debug("App locked successfully");
					unlockAppResponse=readResponseMessages.getUnlockSuccessFulMessage();
				}
				else {
					LOGGER.debug("App couldn't be locked even after matching the lock");
					unlockAppResponse=readResponseMessages.getUnlockFailedMessage();
				}
			}
			else {
				LOGGER.debug("Passcode didn't match");
				unlockAppResponse=readResponseMessages.getPasscodeMismatch();
			}
		}
		else {
			LOGGER.debug("Passcode does not exist");
			unlockAppResponse=readResponseMessages.getUnlockPasscodeEmpty();
		}
		LOGGER.debug("Returning lock app response to Google Assistant"+unlockAppResponse);
		
		return unlockAppResponse;
	}
	
}
