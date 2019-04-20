package com.voiceapp.amico.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.voiceapp.amico.common.AbstractTransactionalDao;
import com.voiceapp.amico.config.AppUtilityConfig;
import com.voiceapp.amico.config.StoreIndividualInfoConfig;
import com.voiceapp.amico.dto.StoreIndividualInfoDto;


@Repository
public class StoreIndividualInfoDao extends AbstractTransactionalDao{

	@Autowired
	private AppUtilityConfig appUtilityConfig;
	
	@Autowired
	private StoreIndividualInfoConfig storeIndividualInfoConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(StoreIndividualInfoDao.class);
	
/**
 * This method will be invoked by Store Individual Information service to save individual user's information in database
 * @param storeIndividualInfoDto
 * @return 
 */
	public Integer saveIndividualInformation(StoreIndividualInfoDto storeIndividualInfoDto) {
		LOGGER.debug("Received request in DAO to Store individual Information");
		Map<String,Object> individualInfoParameters = new HashMap<>();
		individualInfoParameters.put("user_email", storeIndividualInfoDto.getUser_email());
		individualInfoParameters.put("p_key", storeIndividualInfoDto.getContact_name());
		individualInfoParameters.put("p_content", storeIndividualInfoDto.getContact_detail());
		try {
			int checkContactExistence=getJdbcTemplate().queryForObject(appUtilityConfig.getCheckIndividualInfoExists(),individualInfoParameters, Integer.class);
			if(checkContactExistence==1) {
				LOGGER.debug("Personal contact detail exist");
				LOGGER.debug("Executing query to get information against a personal conatct for user");
				return getJdbcTemplate().update(storeIndividualInfoConfig.getUpdateIndividualinfo(), individualInfoParameters);
			}
			else {
				LOGGER.debug("Personal contact detail does not exist");
				return getJdbcTemplate().update(storeIndividualInfoConfig.getInsertIndividualinformation(), individualInfoParameters);
			}
		} catch (DataAccessException e) {
			LOGGER.error("Exception handled while storing the Individual Information"+ e);
			return -1;
		}
		
	}
	
}
