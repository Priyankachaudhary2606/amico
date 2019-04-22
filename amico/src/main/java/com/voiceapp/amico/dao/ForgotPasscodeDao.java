package com.voiceapp.amico.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.voiceapp.amico.common.AbstractTransactionalDao;
import com.voiceapp.amico.config.ForgotPasscodeConfig;

/**
 * This is the DAO class which will be used to save the new passcode after reseting command will be given
 * @author priyankachoudhary
 *
 */
@Repository
public class ForgotPasscodeDao extends AbstractTransactionalDao{

	@Autowired
	private ForgotPasscodeConfig forgotPasscodeConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasscodeDao.class);
	
	public int resetPasscode(String email, int passcode) {
		LOGGER.debug("Received request to reset passcode in resetPasscode-ForgotPasscodeDao"+email+passcode);
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("user_email",email );
		parameters.put("passcode", passcode);
		try {
			LOGGER.debug("Executing query to reset forgot passcode"+forgotPasscodeConfig.getReplacePasscode());
			return getJdbcTemplate().update(forgotPasscodeConfig.getReplacePasscode(), parameters);
		} catch (DataAccessException e) {
			LOGGER.error("Exception occurred with reseting the passcode"+e);
			LOGGER.error("Returning -1");
			return -1;
		}
	}
}
