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
import com.voiceapp.amico.config.StoreInformationConfig;
import com.voiceapp.amico.dto.StoreInformationDto;

/**
 * 
 * @author priyankachaudhary
 *This DAO class will be used to save any information in database
 *this will be invoked by StoreInformationService
 *@LastUpdatedOn - 15/04/2019
 *@reviewed_by -
 */
@Repository
public class StoreInformationDao extends AbstractTransactionalDao{
	
	@Autowired
	private StoreInformationConfig storeInformationConfig;
	
	@Autowired
	private AppUtilityConfig appUtilityConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(StoreInformationDao.class);
	
	/**
	 * This method will be invoked by Store Information service to save information
	 * @param storeInformationDto
	 * @return savingStatus
	 */
	public Integer saveInformation(StoreInformationDto storeInformationDto) {
		int saveInfoStatus;
		LOGGER.debug("Received request in DAO from StoreInfoService to Store Information");
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("user_email", storeInformationDto.getUserEmail());
		parameters.put("info_key",storeInformationDto.getInfoKey());
		parameters.put("info_content",storeInformationDto.getInfoContent());
		parameters.put("type_of_info",storeInformationDto.getTypeOfInfo());
		parameters.put("category_of_info",storeInformationDto.getCategoryOfInfo());
		
		LOGGER.debug("Executing query to check if record already exists");
		try {
			int checkRecordExistence = getJdbcTemplate().queryForObject(appUtilityConfig.getCheckrecordexists(), parameters, Integer.class);
			
			if(checkRecordExistence == 0) {
				LOGGER.debug("Record doesn't exists");
				LOGGER.debug("Executing query to insert information");
				saveInfoStatus = getJdbcTemplate().update(storeInformationConfig.getStoreinformation(), parameters);
			}
			else {
				LOGGER.debug("Record already exists");
				LOGGER.debug("Executing query to update information");
				saveInfoStatus = getJdbcTemplate().update(storeInformationConfig.getUpdateinfo(), parameters);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while storing the information"+e);
			saveInfoStatus=-1;
			LOGGER.debug("Returning response to Service - "+saveInfoStatus);
			return saveInfoStatus;
		}
		LOGGER.debug("Returning response to Service - if Information has been saved successfully"+saveInfoStatus);
		return saveInfoStatus;
	}

}
