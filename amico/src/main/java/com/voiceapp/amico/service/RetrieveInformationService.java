package com.voiceapp.amico.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voiceapp.amico.common.EmailBodyUtility;
import com.voiceapp.amico.common.ReadApplicationConstants;
import com.voiceapp.amico.common.ReadResponseMessages;
import com.voiceapp.amico.common.RetrieveInfoUtility;
import com.voiceapp.amico.common.SendMailUtility;
import com.voiceapp.amico.dto.InformationDetailsDto;

/**
 * This is service class to get the information asked by user.
 * Following are the tasks performed:
 * 1. Retrieve information to get over voice
 * 2. Retrieve information to get over linked user's mail
 * @author priyankachoudhary
 *
 */

@Service
public class RetrieveInformationService {
	
	@Autowired
	private RetrieveInfoUtility retrieveInfoUtility;
	
	
	@Autowired
	private ReadApplicationConstants readApplicationConstants;
	
	@Autowired
	private ReadResponseMessages readResponseMessages;
	
	@Autowired
	private SendMailUtility sendMailUtility;
	
	@Autowired
	private EmailBodyUtility emailBodyUtility;
	
	String retrieveVoiceInfoResponse;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveInformationService.class);
	
/**
 * This method will be used to retrieve the information by passing the:
 * @param email
 * @param info_key
 * @return retrieveVoiceInfoResponse
 */
	public String retrieveInfoOverVoice(String email, String info_key) {
		LOGGER.debug("Received Request in retrieveInfoOverVoice in class RetrieveInformationService");
		LOGGER.debug("Calling method to get information");
		InformationDetailsDto informationDetailsDto = retrieveInfoUtility.getInformationForInfoKey(email, info_key);
	
		if(informationDetailsDto==null || informationDetailsDto.getUser_email()==null || informationDetailsDto.getUser_email().isEmpty()) {
			LOGGER.debug("Response of retrieved information is null");
			retrieveVoiceInfoResponse=readResponseMessages.getNoInfoFound();
		}
		else {
			LOGGER.debug("Receievd information for user against info_key"+info_key); 
			LOGGER.debug("Checking type of information"+informationDetailsDto.getType_of_info());
			if(informationDetailsDto.getType_of_info().equals(readApplicationConstants.getTextTypeOfInfo())) {
				LOGGER.debug("Executing if as Information is of type Text ");
				LOGGER.debug("Checking the category of information"+informationDetailsDto.getCategory_of_info());
				if(informationDetailsDto.getCategory_of_info().equals(readApplicationConstants.getGeneralCategoryOfInfo())) {
					LOGGER.debug("Executing if as Information belongs to GENERAL category");
					retrieveVoiceInfoResponse=readResponseMessages.getVoiceOutInfo1()+" "+informationDetailsDto.getInfo_key() +" "+readResponseMessages.getVoiceOutInfo2()+" "+informationDetailsDto.getInfo_content()+" "+readResponseMessages.getVoiceOutInfo3();
				}
				else {
					LOGGER.debug("Executing else as Information belongs PERSONAL category");
					LOGGER.debug("Checking if information is locked"+informationDetailsDto.getLock_flag());
					if(informationDetailsDto.getLock_flag()==0) {
						LOGGER.debug("Executing if as Personal information is not locked");
						retrieveVoiceInfoResponse=readResponseMessages.getVoiceOutInfo1()+" "+informationDetailsDto.getInfo_key() +" "+readResponseMessages.getVoiceOutInfo2()+" "+informationDetailsDto.getInfo_content()+" "+readResponseMessages.getVoiceOutInfo3();;
					}
					else {
						LOGGER.debug("Executing else as Personal information is locked");
						retrieveVoiceInfoResponse=readResponseMessages.getInfoLocked1()+" "+informationDetailsDto.getInfo_key()+" "+readResponseMessages.getInfoLocked2();
					}
				}
			}
			else {
				String mailMessage = emailBodyUtility.emailFileMessageRetrieveInfo(email, info_key);
				LOGGER.debug("Executing else as Information is of type File ");
				LOGGER.debug("Mail the information as it is of type file");
				int mailStatus = sendMailUtility.sendInformationOverMail(email,info_key,informationDetailsDto.getInfo_content(), mailMessage);
				LOGGER.debug("Mail Status is " +mailStatus);
				if(mailStatus==1) {
					LOGGER.debug("Mail was sent successfully");
					retrieveVoiceInfoResponse=readResponseMessages.getMailedInfo();
				}
				else {
					LOGGER.debug("Sending mail was unsuccessful");
					retrieveVoiceInfoResponse=readResponseMessages.getMailedUnsuccessful();
				}
				
			
			}
		}
		LOGGER.debug("Returning response for google assistant"+retrieveVoiceInfoResponse);
		return retrieveVoiceInfoResponse;
	}
	
/**
 * This method will be called to retrieve asked information on user's email	, performing following tasks:
 * 1. Checking type of information
 * 2. Checking if information is locked or not
 * 3. Sending Mail
 * @param email
 * @param info_key
 * @return
 */
	
	public String retrieveInfoOverMail(String email, String info_key) {
		int mailStatus=0;
		LOGGER.debug("Received Request in retrieveInfoOverMail in class RetrieveInformationService");
		LOGGER.debug("Calling method to get information");
		InformationDetailsDto informationDetailsDto = retrieveInfoUtility.getInformationForInfoKey(email, info_key);
		if(informationDetailsDto==null || informationDetailsDto.getUser_email()==null || informationDetailsDto.getUser_email().isEmpty()) {
			LOGGER.debug("Response of retrieved information is null");
			retrieveVoiceInfoResponse=readResponseMessages.getNoInfoFound();
		}
		else {
			
			LOGGER.debug("Receievd information for user against info_key"+info_key); 
			LOGGER.debug("Checking type of information"+informationDetailsDto.getType_of_info());
			if(informationDetailsDto.getType_of_info().equals(readApplicationConstants.getTextTypeOfInfo())) {
				String messageBody= emailBodyUtility.emailTextMessageRetrieveInfo(info_key.toUpperCase(), informationDetailsDto.getInfo_content());
				LOGGER.debug("Executing if as Information is of type Text ");
				LOGGER.debug("Checking the category of information"+informationDetailsDto.getCategory_of_info());
				if(informationDetailsDto.getCategory_of_info().equals(readApplicationConstants.getGeneralCategoryOfInfo())) {
					
					LOGGER.debug("Executing if as Information belongs to GENERAL category");
					LOGGER.debug("Sending mail to retrieve information over mail");
					mailStatus = sendMailUtility.sendTextInformationOverMail(email,info_key,messageBody);
					LOGGER.debug("Mail Status is " +mailStatus);
					if(mailStatus==1) {
						LOGGER.debug("Mail was sent successfully");
						retrieveVoiceInfoResponse=readResponseMessages.getMailedInfo();
					}
					else {
						LOGGER.debug("Sending mail was unsuccessful");
						retrieveVoiceInfoResponse=readResponseMessages.getMailedUnsuccessful();
					}
				}
				else {
					LOGGER.debug("Executing else as Information belongs PERSONAL category");
					LOGGER.debug("Checking if information is locked"+informationDetailsDto.getLock_flag());
					if(informationDetailsDto.getLock_flag()==0) {
						LOGGER.debug("Executing if as Personal information is not locked");
						LOGGER.debug("Sending mail to retrieve information over mail");
						mailStatus = sendMailUtility.sendTextInformationOverMail(email,info_key,messageBody);
						LOGGER.debug("Mail Status is " +mailStatus);
						if(mailStatus==1) {
							LOGGER.debug("Mail was sent successfully");
							retrieveVoiceInfoResponse=readResponseMessages.getMailedInfo();
						}
						else {
							LOGGER.debug("Sending mail was unsuccessful");
							retrieveVoiceInfoResponse=readResponseMessages.getMailedUnsuccessful();
						}
					}
					else {
						LOGGER.debug("Executing else as Personal information is locked");
						retrieveVoiceInfoResponse=readResponseMessages.getInfoLocked1()+" "+informationDetailsDto.getInfo_key()+" "+readResponseMessages.getInfoLocked2();
					}
				}
			}
			else {
				String messageBody= emailBodyUtility.emailFileMessageRetrieveInfo(email,informationDetailsDto.getInfo_key().toUpperCase());
				LOGGER.debug("Executing else as Information is of type File ");
				LOGGER.debug("Mail the information as it is of type file");
				mailStatus = sendMailUtility.sendInformationOverMail(email, informationDetailsDto.getInfo_key(), informationDetailsDto.getInfo_content(), messageBody);
				LOGGER.debug("Mail Status is " +mailStatus);
				if(mailStatus==1) {
					LOGGER.debug("Mail was sent successfully");
					retrieveVoiceInfoResponse=readResponseMessages.getMailedInfo();
				}
				else {
					LOGGER.debug("Sending mail was unsuccessful");
					retrieveVoiceInfoResponse=readResponseMessages.getMailedUnsuccessful();
				}
				
			
			}
		}
		LOGGER.debug("Returning response for google assistant"+retrieveVoiceInfoResponse);
		return retrieveVoiceInfoResponse;
	}
	
	

}
