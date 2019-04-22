package com.voiceapp.amico.service;

import static org.junit.Assert.assertNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voiceapp.amico.common.EmailBodyUtility;
import com.voiceapp.amico.common.ReadApplicationConstants;
import com.voiceapp.amico.common.ReadResponseMessages;
import com.voiceapp.amico.common.RetrieveInfoUtility;
import com.voiceapp.amico.common.SendMailUtility;
import com.voiceapp.amico.common.SendTextMessageUtility;
import com.voiceapp.amico.common.SendWhatsappMessage;
import com.voiceapp.amico.dto.InformationDetailsDto;

/**
 * This is Service that would be used to perform Sharing asked information
 * 1. Over Mail
 * 2. Over Text message
 * 3. Over WhatsApp
 * @author priyankachoudhary
 *
 */
@Service
public class ShareInformationService {
	
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
	
	@Autowired
	private SendTextMessageUtility sendTextMessageUtility;
	
	@Autowired
	private SendWhatsappMessage sendWhatsappMessage;
	
	String shareInfoOverMailResponse;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShareInformationService.class);
	

/**
 * This method will be called to share asked information on email, performing following tasks:
 * 1. Checking type of information
 * 2. Checking if information is locked or not
 * 3. Sending Mail
 * @param email
 * @param info_key
 * @return
 */
		
	public String shareInformationOverMail(String email, String info_key, String receiverEmail, String username) {
		int mailStatus=0;
		LOGGER.debug("Received Request in shareInformationOverMail in class ShareInformationService");
		LOGGER.debug("Calling method to get information asked to share by "+info_key+" to "+receiverEmail);
		InformationDetailsDto informationDetailsDto = retrieveInfoUtility.getInformationForInfoKey(email, info_key);
		
		if(informationDetailsDto==null || informationDetailsDto.getUser_email()==null || informationDetailsDto.getUser_email().isEmpty()) {
			LOGGER.debug("Response of retrieved information is null");
			shareInfoOverMailResponse=readResponseMessages.getNoInfoFound();
		}
		
		else {
			
			LOGGER.debug("Creating mail body by calling emailTextMessageShareInfo in EmailBodyUtility");
			String messageBodyText= emailBodyUtility.emailTextMessageShareInfo(email, username, receiverEmail,info_key.toUpperCase(), informationDetailsDto.getInfo_content());
			String messageBodyFile= emailBodyUtility.emailFileMessageShareInfo(email, username, receiverEmail,info_key.toUpperCase());
			
			LOGGER.debug("Receievd information for user against info_key"+info_key); 
			LOGGER.debug("Checking category of information"+informationDetailsDto.getCategory_of_info());
			
			if(informationDetailsDto.getCategory_of_info().equals(readApplicationConstants.getPersonalCategoryOfInfo())) {
				
				LOGGER.debug("Executing if as category of info is personal");
				LOGGER.debug("Checking if information is locked - "+informationDetailsDto.getLock_flag());
				
				if(informationDetailsDto.getLock_flag()==1) {
					
					LOGGER.debug("Information is personal & locked");
					shareInfoOverMailResponse=readResponseMessages.getInfoLocked1()+" "+informationDetailsDto.getInfo_key()+" "+readResponseMessages.getInfoLocked2();
				}
				else {
					
					LOGGER.debug("Executing if as personal information is not locked");
					LOGGER.debug("Checking if information is of Type file -"+informationDetailsDto.getType_of_info());
					
					if(informationDetailsDto.getType_of_info().equals(readApplicationConstants.getTextTypeOfInfo())) {
						
						LOGGER.debug("Executing if as information is of type text");
						mailStatus = sendMailUtility.sendTextInformationOverMail(receiverEmail,info_key,messageBodyText);
						LOGGER.debug("Mail Status is " +mailStatus);
						
						if(mailStatus==1) {
							
							LOGGER.debug("Mail was sent successfully");
							shareInfoOverMailResponse=readResponseMessages.getMailSent1()+" "+info_key+" to "+receiverEmail+" "+readResponseMessages.getMailSent2();
						}
						else {
							
							LOGGER.debug("Sending mail was unsuccessful");
							shareInfoOverMailResponse=readResponseMessages.getMailedUnsuccessful();
						}
					}
					else {
						
						LOGGER.debug("Executing if as information is of type file");
						mailStatus = sendMailUtility.sendInformationOverMail(receiverEmail,info_key,informationDetailsDto.getInfo_content(),messageBodyFile);
						LOGGER.debug("Mail Status is " +mailStatus);
						
						if(mailStatus==1) {
							
							LOGGER.debug("Mail was sent successfully");
							shareInfoOverMailResponse=readResponseMessages.getMailSent1()+" "+info_key+" to "+receiverEmail+" "+readResponseMessages.getMailSent2();
						}
						else {
							
							LOGGER.debug("Sending mail was unsuccessful");
							shareInfoOverMailResponse=readResponseMessages.getMailedUnsuccessful();
						}
					}
				}
			}
			
			else {
				
				LOGGER.debug("Executing else as category of Info is General");
				LOGGER.debug("Checking if information is of Type file -"+informationDetailsDto.getType_of_info());
				
				if(informationDetailsDto.getType_of_info().equals(readApplicationConstants.getTextTypeOfInfo())) {
					
					LOGGER.debug("Executing if as information is of type text");
					mailStatus = sendMailUtility.sendTextInformationOverMail(receiverEmail,info_key,messageBodyText);
					LOGGER.debug("Mail Status is " +mailStatus);
					
					if(mailStatus==1) {
						
						LOGGER.debug("Mail was sent successfully");
						shareInfoOverMailResponse=readResponseMessages.getMailSent1()+" "+info_key+" to "+receiverEmail+" "+readResponseMessages.getMailSent2();
					}
					else {
						
						LOGGER.debug("Sending mail was unsuccessful");
						shareInfoOverMailResponse=readResponseMessages.getMailedUnsuccessful();
					}
					
				}
				
				else {
					
					LOGGER.debug("Executing if as information is of type file");
					mailStatus = sendMailUtility.sendInformationOverMail(receiverEmail,info_key,informationDetailsDto.getInfo_content(), messageBodyFile);
					LOGGER.debug("Mail Status is " +mailStatus);
					
					if(mailStatus==1) {
						
						LOGGER.debug("Mail was sent successfully");
						shareInfoOverMailResponse=readResponseMessages.getMailSent1()+" "+info_key+" to "+receiverEmail+" "+readResponseMessages.getMailSent2();
					}
					else {
						
						LOGGER.debug("Sending mail was unsuccessful");
						shareInfoOverMailResponse=readResponseMessages.getMailedUnsuccessful();
					}
				}
				
			}
		}
		LOGGER.debug("Returning response for google assistant from Service"+shareInfoOverMailResponse);
		return shareInfoOverMailResponse;
		
	}
	
/**
 * This is the method when user wants to share something to their close ones having their details already stored
 * such as- only by saying "share my pan card number to my mother's email address" & user have already saved his/her mother's email address in the application
 * @param email
 * @param info_key
 * @param p_key
 * @param username
 * @return
 */
	
	public String shareInformationOverMailToIndividualContact(String email, String info_key, String p_key, String username) {
		String shareInfoToIndividualResponse=readResponseMessages.getMailedUnsuccessful();
		String getReceiverContact=retrieveInfoUtility.getInformationForIndividualInfo(email, p_key);
		if(getReceiverContact==null || getReceiverContact.isEmpty()) {
			LOGGER.debug("While getting personal contact information, Exception occurred");
			LOGGER.debug("Returning mail unsuccessful message");
			shareInfoToIndividualResponse=readResponseMessages.getMailedUnsuccessful();
		}
		else if(getReceiverContact.equals(readResponseMessages.getNoInfoFound())) {
			LOGGER.debug("While getting personal contact information");
			LOGGER.debug("No information found for personal_contact"+p_key);
			shareInfoToIndividualResponse=getReceiverContact;
		}
		else {
			LOGGER.debug("Individual information found for user, that is"+p_key+getReceiverContact);
			shareInfoToIndividualResponse=this.shareInformationOverMail(email, info_key, getReceiverContact, username);
			LOGGER.debug("Received response from share over mail method in Service"+shareInfoToIndividualResponse);
		}
		LOGGER.debug("Returning response from shareInformationToIndividualContact for Google Assistant to Intent Controller"+shareInfoToIndividualResponse);
		return shareInfoToIndividualResponse;
	}
	
/**
 * This method will be invoked to send text message by user over provided  contact number
 * @param email
 * @param info_key
 * @param receiverContactNumber
 * @param username
 * @return
 */
	public String shareInformationOverTextMessage(String email, String info_key, String receiverContactNumber, String username) {
		String shareInfoTextMessageResponse=null;
		LOGGER.debug("Received request in shareInformationOverTextMessage to send text message having information");
		LOGGER.debug("Calling method to get informtion asked by user"+info_key);
		InformationDetailsDto informationDetailsDto = retrieveInfoUtility.getInformationForInfoKey(email, info_key);
		if(informationDetailsDto==null || informationDetailsDto.getUser_email()==null || informationDetailsDto.getUser_email().isEmpty()) {
			LOGGER.debug("Response of retrieved information is null");
			shareInfoTextMessageResponse=readResponseMessages.getNoInfoFound();
		}
		
		else {
			LOGGER.debug("Executing else as information found in the system");
			LOGGER.debug("Checking if information is of type file"+informationDetailsDto.getType_of_info());
			if(informationDetailsDto.getType_of_info().equals(readApplicationConstants.getFileTypeOfInfo())) {
				LOGGER.debug("Executing if as information is of file type");
				shareInfoTextMessageResponse="For "+info_key+" "+readResponseMessages.getMessageHasFileFailed();
			}
			else {
				String messageBody="Hi, "+username+" has shared "+info_key+" --- "+informationDetailsDto.getInfo_content()+". *Do not share.*";
				LOGGER.debug("Executing else as information of type Text");
				LOGGER.debug("Checking if information is personal or general"+informationDetailsDto.getCategory_of_info());
				if(informationDetailsDto.getCategory_of_info().equals(readApplicationConstants.getPersonalCategoryOfInfo())) {
					LOGGER.debug("Executing if as text information is personal");
					LOGGER.debug("Checking if informtion is locked or not"+informationDetailsDto.getLock_flag());
					if(informationDetailsDto.getLock_flag()==1) {
						LOGGER.debug("Executing if as information is locked");
						shareInfoTextMessageResponse=readResponseMessages.getInfoLocked1()+" "+informationDetailsDto.getInfo_key()+" "+readResponseMessages.getInfoLocked2();;
					}
					else {
						int statusSenttext = sendTextMessageUtility.sendTextMessage(email, receiverContactNumber, info_key, messageBody);
						if(statusSenttext==1) {
							LOGGER.debug("Message has been sent successfully");
							shareInfoTextMessageResponse=readResponseMessages.getMessageSent();
						}else {
							LOGGER.debug("Message was not sent successfully");
							shareInfoTextMessageResponse=readResponseMessages.getMessageFailed();
						}
						
					}
				}
				else {
					LOGGER.debug("Executing else as information is general");
					int statusSenttext = sendTextMessageUtility.sendTextMessage(email, receiverContactNumber, info_key, messageBody);
					if(statusSenttext==1) {
						LOGGER.debug("Message has been sent successfully");
						shareInfoTextMessageResponse=readResponseMessages.getMessageSent();
					}else {
						LOGGER.debug("Message was not sent successfully");
						shareInfoTextMessageResponse=readResponseMessages.getMessageFailed();
					}
				}
			}
		}
		
		LOGGER.debug("Returning message from Service after sending the text message"+ shareInfoTextMessageResponse);
		return shareInfoTextMessageResponse;
	}
	
	
	
	
	public String shareInformationOverTextToIndividualContact(String email, String info_key, String p_key, String username) {
		String shareInfoTextToIndividualResponse=readResponseMessages.getMessageFailed();
		String getReceiverContact=retrieveInfoUtility.getInformationForIndividualInfo(email, p_key);
		if(getReceiverContact==null || getReceiverContact.isEmpty()) {
			LOGGER.debug("While getting personal contact information, Exception occurred");
			LOGGER.debug("Returning message unsuccessful message");
			shareInfoTextToIndividualResponse=readResponseMessages.getMessageFailed();
		}
		else if(getReceiverContact.equals(readResponseMessages.getNoInfoFound())) {
			LOGGER.debug("While getting personal contact information");
			LOGGER.debug("No information found for personal_contact"+p_key);
			shareInfoTextToIndividualResponse=getReceiverContact;
		}
		else {
			LOGGER.debug("Individual information found for user, that is"+p_key+getReceiverContact);
			shareInfoTextToIndividualResponse=this.shareInformationOverTextMessage(email, info_key, getReceiverContact, username);
			LOGGER.debug("Received response from share over text message method in Service"+shareInfoTextToIndividualResponse);
		}
		LOGGER.debug("Returning response from shareInformationOverTextToIndividualContact for Google Assistant to Intent Controller"+shareInfoTextToIndividualResponse);
		return shareInfoTextToIndividualResponse;
	}
		
		
/**
 * This method will be invoked to send  whatsapp message	
 * @param email
 * @param info_key
 * @param receiverContactNumber
 * @param username
 * @return
 */
	public String shareInfoOverWhatsappMessage(String email, String info_key, String receiverContactNumber, String username) {
		String shareInfoWhatsappMessageResponse=null;
		LOGGER.debug("Received request in shareInfoOverWhatsappMessage to send WHATSAPP message having information");
		LOGGER.debug("Calling method to get informtion asked by user"+info_key);
		InformationDetailsDto informationDetailsDto = retrieveInfoUtility.getInformationForInfoKey(email, info_key);
		if(informationDetailsDto==null || informationDetailsDto.getUser_email()==null || informationDetailsDto.getUser_email().isEmpty()) {
			LOGGER.debug("Response of retrieved information is null");
			shareInfoWhatsappMessageResponse=readResponseMessages.getNoInfoFound();
		}
		
		else {
			LOGGER.debug("Executing else as information found in the system");
			LOGGER.debug("Checking if information is of type file"+informationDetailsDto.getType_of_info());
			if(informationDetailsDto.getType_of_info().equals(readApplicationConstants.getFileTypeOfInfo())) {
				LOGGER.debug("Executing if as information is of file type");
				shareInfoWhatsappMessageResponse="For "+info_key+" "+readResponseMessages.getMessageHasFileFailed();
				
				/****************************************FILE****************************************/

				LOGGER.debug("Executing else as information of type Text");
				LOGGER.debug("Checking if information is personal or general"+informationDetailsDto.getCategory_of_info());
				if(informationDetailsDto.getCategory_of_info().equals(readApplicationConstants.getPersonalCategoryOfInfo())) {
					LOGGER.debug("Executing if as text information is personal");
					LOGGER.debug("Checking if informtion is locked or not"+informationDetailsDto.getLock_flag());
					if(informationDetailsDto.getLock_flag()==1) {
						LOGGER.debug("Executing if as information is locked");
						shareInfoWhatsappMessageResponse=readResponseMessages.getInfoLocked1()+" "+informationDetailsDto.getInfo_key()+" "+readResponseMessages.getInfoLocked2();;
					}
					else {
						int statusSenttext = sendWhatsappMessage.sendDocOverWhatsapp(receiverContactNumber);
						if(statusSenttext==1) {
							LOGGER.debug("Message has been sent successfully");
							shareInfoWhatsappMessageResponse=readResponseMessages.getMessageSent();
						}else {
							LOGGER.debug("Message was not sent successfully");
							shareInfoWhatsappMessageResponse=readResponseMessages.getMessageFailed();
						}
						
					}
				}
				else {
					LOGGER.debug("Executing else as information is general");
					int statusSenttext = sendWhatsappMessage.sendDocOverWhatsapp(receiverContactNumber);
					if(statusSenttext==1) {
						LOGGER.debug("Message has been sent successfully");
						shareInfoWhatsappMessageResponse=readResponseMessages.getMessageSent();
					}else {
						LOGGER.debug("Message was not sent successfully");
						shareInfoWhatsappMessageResponse=readResponseMessages.getMessageFailed();
					}
				}
			
				/**********************************END FILE TYPE ************************************/

			}
			else {
				String messageBody="Hi, "+username+" has shared "+info_key+" -- "+informationDetailsDto.getInfo_content()+". *Do not share.*";
				LOGGER.debug("Executing else as information of type Text");
				LOGGER.debug("Checking if information is personal or general"+informationDetailsDto.getCategory_of_info());
				if(informationDetailsDto.getCategory_of_info().equals(readApplicationConstants.getPersonalCategoryOfInfo())) {
					LOGGER.debug("Executing if as text information is personal");
					LOGGER.debug("Checking if informtion is locked or not"+informationDetailsDto.getLock_flag());
					if(informationDetailsDto.getLock_flag()==1) {
						LOGGER.debug("Executing if as information is locked");
						shareInfoWhatsappMessageResponse=readResponseMessages.getInfoLocked1()+" "+informationDetailsDto.getInfo_key()+" "+readResponseMessages.getInfoLocked2();;
					}
					else {
						int statusSenttext = sendWhatsappMessage.sendTextWhatsappMessage(email, receiverContactNumber, info_key, messageBody);
						if(statusSenttext==1) {
							LOGGER.debug("Message has been sent successfully");
							shareInfoWhatsappMessageResponse=readResponseMessages.getMessageSent();
						}else {
							LOGGER.debug("Message was not sent successfully");
							shareInfoWhatsappMessageResponse=readResponseMessages.getMessageFailed();
						}
						
					}
				}
				else {
					LOGGER.debug("Executing else as information is general");
					int statusSenttext = sendWhatsappMessage.sendTextWhatsappMessage(email, receiverContactNumber, info_key, messageBody);
					if(statusSenttext==1) {
						LOGGER.debug("Message has been sent successfully");
						shareInfoWhatsappMessageResponse=readResponseMessages.getMessageSent();
					}else {
						LOGGER.debug("Message was not sent successfully");
						shareInfoWhatsappMessageResponse=readResponseMessages.getMessageFailed();
					}
				}
			}
		}
		
		LOGGER.debug("Returning message from Service after sending the WHATSAPP message"+ shareInfoWhatsappMessageResponse);
		return shareInfoWhatsappMessageResponse;
	}
	
	
	
	
	public String shareInfoOverWhatsappToIndividualContact(String email, String info_key, String p_key, String username) {
		LOGGER.debug("Request received in service to send whatsapp message over individual contact");
		String shareInfoWhatsappToIndividualResponse=readResponseMessages.getMessageFailed();
		String getReceiverContact=retrieveInfoUtility.getInformationForIndividualInfo(email, p_key);
		if(getReceiverContact==null || getReceiverContact.isEmpty()) {
			LOGGER.debug("While getting personal contact information, Exception occurred");
			LOGGER.debug("Returning message unsuccessful message");
			shareInfoWhatsappToIndividualResponse=readResponseMessages.getMessageFailed();
		}
		else if(getReceiverContact.equals(readResponseMessages.getNoInfoFound())) {
			LOGGER.debug("While getting personal contact information");
			LOGGER.debug("No information found for personal_contact"+p_key);
			shareInfoWhatsappToIndividualResponse=getReceiverContact;
		}
		else {
			LOGGER.debug("Individual information found for user, that is"+p_key+getReceiverContact);
			shareInfoWhatsappToIndividualResponse=this.shareInfoOverWhatsappMessage(email, info_key, getReceiverContact, username);
			LOGGER.debug("Received response from share over text message method in Service"+shareInfoWhatsappToIndividualResponse);
		}
		LOGGER.debug("Returning response from shareInformationOverTextToIndividualContact for Google Assistant to Intent Controller"+shareInfoWhatsappToIndividualResponse);
		return shareInfoWhatsappToIndividualResponse;
	}
		
		

	
}
