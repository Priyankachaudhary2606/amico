package com.voiceapp.amico.common;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.voiceapp.amico.config.AppUtilityConfig;


/**
 * This is the utility class for methods related to Passcode
 * @author priyankachoudhary
 *
 */
@Repository
public class AppPasscodeUtility extends AbstractTransactionalDao{
	
	@Autowired
	private AppUtilityConfig appUtilityConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppPasscodeUtility.class);

	/**
	 * This method will be invoked  to check if user has added the passcode
	 * @param userEmail
	 * @return passcodeExistenceStatus
	 */
	public Integer checkPasscodeExists(String userEmail) {
		LOGGER.debug("Received request in AppPasscode to check if Passcode Exists"+userEmail);
		Map<String,Object> user_email = new HashMap<> ();
		user_email.put("user_email", userEmail);
		LOGGER.debug("Executing SQL Query to check if passcode exists"+user_email);
		int status = getJdbcTemplate().queryForObject(appUtilityConfig.getCheckpasscodeexists(), user_email, Integer.class);
		return status;
	}
}
