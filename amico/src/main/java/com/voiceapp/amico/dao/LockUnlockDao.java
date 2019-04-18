package com.voiceapp.amico.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.voiceapp.amico.common.AbstractTransactionalDao;
import com.voiceapp.amico.config.LockUnlockAppConfig;

/**
 * 
 * @author priyankachoudhary
 * This class will be invoked to lock/unlock the application
 * This class will be performing following tasks:
 * 1. Lock app
 * 2. Unlock app
 * 3. match passcode
 * 4. add new passcode
 * @reviewed_by 
 *
 */

@Repository
public class LockUnlockDao extends AbstractTransactionalDao{
		
	@Autowired
	private LockUnlockAppConfig lockUnlockAppConfig;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LockUnlockDao.class);
	
/**
 * Method to lock all the personal information of user
 * @param email
 * @param passcode
 * @return lockAppStatus
 */
	public int lockApp(String email, int passcode) {
		LOGGER.debug("Received request in LockUnlockDao to lock application");
		Map<String, Object> lockparameters= new HashMap<>();
		int lockAppStatus=-1;
		LOGGER.debug("Adding parameters for SQL query");
		lockparameters.put("user_email", email);
		lockparameters.put("passcode", passcode);
		try {
			LOGGER.debug("Executing query to lock personal information of user");
			lockAppStatus=getJdbcTemplate().update(lockUnlockAppConfig.getLockapp(), lockparameters);
		} catch (Exception e) {
			LOGGER.error("Error occurred while locking app information"+e);
			lockAppStatus=-1;
		}
		return lockAppStatus;
	}
	
/**
 * Method to Unlock the personal information in app
 * @param email
 * @param passcode
 * @return
 */
	public int unlockApp(String email, int passcode) {
		LOGGER.debug("Received request in LockUnlockDao to unlock application");
		Map<String, Object> unlockparameters= new HashMap<>();
		int unlockAppStatus=-1;
		LOGGER.debug("Adding parameters for SQL query");
		unlockparameters.put("user_email", email);
		unlockparameters.put("passcode", passcode);
		try {
			LOGGER.debug("Executing query to unlock personal information of user");
			unlockAppStatus=getJdbcTemplate().update(lockUnlockAppConfig.getUnlockapp(), unlockparameters);
		} catch (Exception e) {
			LOGGER.error("Error occurred while unlocking app information "+e);
			unlockAppStatus=-1;
		}
		return unlockAppStatus;
	}
	
/**
 * Method to match passcode used to lock application
 * @param email
 * @param passcode
 * @return matchPasscodeStatus
 */
	public int matchPasscode(String email, int passcode) {
		LOGGER.debug("Received request in LockUnlockDao to match passcode of application");
		Map<String, Object> matchPasscodeParameters= new HashMap<>();
		int matchPasscodeStatus=-1;
		LOGGER.debug("Adding parameters for SQL query");
		matchPasscodeParameters.put("user_email", email);
		matchPasscodeParameters.put("passcode", passcode);
		try {
			LOGGER.debug("Executing query to match passcode of user");
			matchPasscodeStatus=getJdbcTemplate().queryForObject(lockUnlockAppConfig.getMatchpasscode(), matchPasscodeParameters, Integer.class);
		} catch (Exception e) {
			LOGGER.error("Error occurred while matching the passcode"+e);
			matchPasscodeStatus=-1;
		}
		return matchPasscodeStatus;
	}

/**
 * Method to save passcode to lock/unlock application
 * @param email
 * @param passcode
 * @return savePasscodeStatus
 */
	public int savePasscode(String email, int passcode) {
		LOGGER.debug("Received request in LockUnlockDao to save the passcode for user");
		Map<String, Object> savePasscodeParameters= new HashMap<>();
		int savePasscodeStatus=-1;
		LOGGER.debug("Adding parameters for SQL query"+email+passcode);
		savePasscodeParameters.put("user_email", email);
		savePasscodeParameters.put("passcode", passcode);
		try {
			LOGGER.debug("Executing query to save passcode of user");
			savePasscodeStatus=getJdbcTemplate().update(lockUnlockAppConfig.getSavepasscode(), savePasscodeParameters);
		} catch (Exception e) {
			LOGGER.error("Error occurred while saving the passcode "+e);
			savePasscodeStatus=-1;
		}
		return savePasscodeStatus;
	}
	
}
