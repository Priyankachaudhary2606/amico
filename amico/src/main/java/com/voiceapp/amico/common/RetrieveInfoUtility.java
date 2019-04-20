package com.voiceapp.amico.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.voiceapp.amico.config.AppUtilityConfig;
import com.voiceapp.amico.dto.InformationDetailsDto;

/**
 * 
 * This is the utility class to get the information from database and can be used for various operations
 * 1. Retrieve information against the info_key asked by used
 * 2. Retrieve information against the individual's conatct details
 * 
 * @author priyankachoudhary
 *
 */

@Repository
public class RetrieveInfoUtility extends AbstractTransactionalDao {
	
	@Autowired
	private AppUtilityConfig appUtilityConfig;
	
	@Autowired
	private ReadResponseMessages readResponseMessages;

	private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveInfoUtility.class);
	private static final InfoDetailsRowMapper infoDetails_RowMapper = new InfoDetailsRowMapper();
	
	public InformationDetailsDto getInformationForInfoKey(String email, String info_key) {
		LOGGER.debug("Received request in getInformationForInfoKey in class RetrieveInfoUtility");
		LOGGER.debug("To get information stored by user against info_key"+ info_key);
		LOGGER.debug("Adding parameters for SQL query");
		Map<String, Object> infoParameters = new HashMap<>();
		infoParameters.put("user_email", email);
		infoParameters.put("info_key", info_key);
		try {
			
			LOGGER.debug("Executing SQL query to get information details against info_key");
			return getJdbcTemplate().queryForObject(appUtilityConfig.getInfodetails(), infoParameters, infoDetails_RowMapper);
			
		} catch (DataAccessException e) {
			
			LOGGER.error("An exception occurred while fetching the information asked by user"+e);
			LOGGER.error("In method - getInformationForInfoKey, while getting information details: " +email+info_key);
        	LOGGER.error("Exception is: "+e);
        	LOGGER.error("Returning null");
        	return null;
        	
		}
	}
	
	
	
	public static class InfoDetailsRowMapper implements RowMapper<InformationDetailsDto>
	{
		@Override
		public InformationDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			int info_id = rs.getInt("info_id");
			String info_key = rs.getString("info_key");
			String info_content = rs.getString("info_content");
			String type_of_info= rs.getString("type_of_info");
			String category_of_info= rs.getString("category_of_info");
			String user_email= rs.getString("user_email");
			int passcode= rs.getInt("passcode");
			int lock_flag= rs.getInt("lock_flag");
			
			return new InformationDetailsDto(info_id, info_key, info_content, type_of_info,
					category_of_info, user_email, passcode, lock_flag);
					
			
		}
	}

/**
 * This is the method used to get the individual's personal contact details from the DATABASE
 * which can then be used to share some information
 * @param email
 * @param info_key
 * @return
 */
	
	public String getInformationForIndividualInfo(String email, String p_key) {
		LOGGER.debug("Received request in getInformationForIndividualInfo in class RetrieveInfoUtility");
		LOGGER.debug("To get information stored by user against personal contact"+ p_key);
		LOGGER.debug("Adding parameters for SQL query");
		Map<String, Object> individualInfoParameters = new HashMap<>();
		individualInfoParameters.put("user_email", email);
		individualInfoParameters.put("p_key", p_key);
		try {
			LOGGER.debug("Checking if Personal contact detail exist");
			int checkContactExistence=getJdbcTemplate().queryForObject(appUtilityConfig.getCheckIndividualInfoExists(),individualInfoParameters, Integer.class);
			if(checkContactExistence==1) {
				LOGGER.debug("Personal contact detail exist");
				LOGGER.debug("Executing query to get information against a personal conatct for user");
				return getJdbcTemplate().queryForObject(appUtilityConfig.getIndividualInformation(),individualInfoParameters, String.class);
			}
			else {
				LOGGER.debug("Personal contact detail does not exist");
				return readResponseMessages.getNoInfoFound();
			}
			
		} catch (DataAccessException e) {
			LOGGER.error("Exception occurred while retrieving information against personal contact asked by user");
			LOGGER.error("Exception is"+e+". Returning null");
			return null;
		}
	}
	
	
	
}
