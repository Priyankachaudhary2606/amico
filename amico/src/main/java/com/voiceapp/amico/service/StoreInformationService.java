package com.voiceapp.amico.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.dialogflow_fulfillment.v2.DialogflowFulfillment;
import com.voiceapp.amico.common.AppPasscodeUtility;
import com.voiceapp.amico.common.ReadApplicationConstants;
import com.voiceapp.amico.common.ReadResponseMessages;
import com.voiceapp.amico.dao.StoreInformationDao;
import com.voiceapp.amico.dto.StoreInformationDto;


/**
 * 
 * @author priyankachoudhary
 * This service will be invoked by Intent controller to Store Information
 * i.e., saving the information of user in database by calling respective method in DAO 
 * @LastUpdatedOn - 15/4/2019
 * @reviewed_by -  
 */
@Service
public class StoreInformationService{

	private static final Logger LOGGER = LoggerFactory.getLogger(StoreInformationService.class);
	
	public String storeInfoStatus=null;
	
	@Autowired 
	private ReadApplicationConstants readApplicationConstants;
	
	@Autowired
	private StoreInformationDao storeInformationDao;
	
	@Autowired
	private ReadResponseMessages readResponseMessages;
	
	@Autowired
	private AppPasscodeUtility appPasscodeUtility;
	
	/**
	 * This method will be main performer to store any information, by performing following tasks:
	 * 1. Check the category of information
	 * 2. Call method to save general information
	 * 3. Call method to save personal information
	 * 4. Return response after saving information
	 * @param userEmail
	 * @param infoKey
	 * @param infoContent
	 * @param categoryOfInfo
	 * @return storeInfoStatus
	 */	
	public String storeInformation(StoreInformationDto storeInformationDto) {
		LOGGER.debug("Request received from StoreInfo intent controller in storeInformation Service");
		
		if(storeInformationDto.getCategoryOfInfo().equals(readApplicationConstants.getPersonalCategoryOfInfo())) {
			LOGGER.debug("True if condition, information is Personal");
			LOGGER.debug("Calling storePersonalInfo method in service");
			storeInfoStatus=this.storePersonalInfo(storeInformationDto);
			LOGGER.debug("Returning storeInfoStatus after storing the personal info");
			return storeInfoStatus;
		}
		else {
			LOGGER.debug("False if condition, information is General"+storeInformationDto.getCategoryOfInfo());
			LOGGER.debug("Calling storeGeneralInfo method in service");
			storeInfoStatus=this.storeGeneralInfo(storeInformationDto);
			LOGGER.debug("Returning storeInfoStatus after storing the general info");
			return storeInfoStatus;
		}
		
	}
	
	/**
	 * This method will be invoked by storeInformation from this service & will save the personal information in database 
	 * @param storeInformationDto
	 * @return storeInfoStatus
	 */
	public String storePersonalInfo(StoreInformationDto storeInformationDto) {
		
		int checkPasscodeExistence = appPasscodeUtility.checkPasscodeExists(storeInformationDto.getUserEmail());
		LOGGER.debug("Passcode Existence Status is " + checkPasscodeExistence);
		int storePersonalInfoStatus = storeInformationDao.saveInformation(storeInformationDto);
		LOGGER.debug("Stored Information Status is " + storePersonalInfoStatus);
		if(checkPasscodeExistence == 0 && storePersonalInfoStatus == 1) {
			LOGGER.debug("Information has been stored successfully but Passcode doesn't exists");
			LOGGER.debug("Returning response message for Google Assistant");
			storeInfoStatus=readResponseMessages.getInformationStoredSuccessfully1()+" "+storeInformationDto.getInfoKey()+" "+readResponseMessages.getInformationStoredSuccessfully2() +" "+ readResponseMessages.getPasscodeIsEmpty();
		}
		else if(checkPasscodeExistence == 1 && storePersonalInfoStatus == 1){
			LOGGER.debug("Information has been stored successfully but Passcode exists");
			LOGGER.debug("Returning response message for Google Assistant");
			storeInfoStatus=readResponseMessages.getInformationStoredSuccessfully1()+" "+storeInformationDto.getInfoKey()+" "+readResponseMessages.getInformationStoredSuccessfully2() ;
		}
		else {
			LOGGER.debug("Storing information process couldn't be done successfully");
			LOGGER.debug("Returning response message for Google Assistant");
			storeInfoStatus= readResponseMessages.getErrorStoreInformation();
		}
		LOGGER.debug("Response is "+ storeInfoStatus);
		return storeInfoStatus;
	}
	
	/**
	 * This method will be invoked by storeInformation from this service & will save the general information in database 
	 * @param storeInformationDto
	 * @return storeInfoStatus
	 */
	public String storeGeneralInfo(StoreInformationDto storeInformationDto) {
		int storeGeneralInfoStatus = storeInformationDao.saveInformation(storeInformationDto);
		if(storeGeneralInfoStatus == 1) {
			LOGGER.debug("Information stored successfully");
			storeInfoStatus=readResponseMessages.getInformationStoredSuccessfully1()+" "+storeInformationDto.getInfoKey()+" "+readResponseMessages.getInformationStoredSuccessfully2();
		}
		else {
			LOGGER.debug("Storing information process couldn't be done successfully");
			storeInfoStatus = readResponseMessages.getErrorStoreInformation();
		}
		LOGGER.debug("Returning response message for Google Assistant");
		LOGGER.debug("Response is "+ storeInfoStatus);
		return storeInfoStatus;
	}

}
