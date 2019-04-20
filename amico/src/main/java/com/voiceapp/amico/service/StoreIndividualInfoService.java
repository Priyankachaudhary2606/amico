package com.voiceapp.amico.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voiceapp.amico.common.ReadResponseMessages;
import com.voiceapp.amico.dao.StoreIndividualInfoDao;
import com.voiceapp.amico.dto.StoreIndividualInfoDto;

@Service
public class StoreIndividualInfoService {
	
	@Autowired
	private StoreIndividualInfoDao storeIndividualInfoDao;
	
	@Autowired
	private ReadResponseMessages readResponseMessages;
	
	String saveIndividualInfoResponse;

	private static final Logger LOGGER = LoggerFactory.getLogger(StoreIndividualInfoService.class);
	
	public String storeIndividualInformation(StoreIndividualInfoDto storeIndividualInfoDto) {
		LOGGER.debug("Request received in StoreIndividualInfoService to storeIndividualInformation");
		LOGGER.debug("Calling DAO method storeIndividualInformation");
		int storeStatus = storeIndividualInfoDao.saveIndividualInformation(storeIndividualInfoDto);
		if(storeStatus==-1) {
			LOGGER.debug("Information couldn't be saved because of some exception");
			saveIndividualInfoResponse=readResponseMessages.getIndividualInformationSaveFailed();
		}
		else{
			LOGGER.debug("Information"+storeIndividualInfoDto.getContact_name()+" has been saved successfully");
			saveIndividualInfoResponse=readResponseMessages.getIndividualInformationSavedSuccessfully1()+" "+storeIndividualInfoDto.getContact_name()+" "+readResponseMessages.getIndividualInformationSavedSuccessfully2()+" "+storeIndividualInfoDto.getContact_name();
		}

		LOGGER.debug("Returning response to Google Assistant from Service"+saveIndividualInfoResponse);
		return saveIndividualInfoResponse;
	}
}
