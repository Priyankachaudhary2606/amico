package com.voiceapp.amico.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.SignIn;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.voiceapp.amico.common.GetLinkedUserDetails;
import com.voiceapp.amico.common.ReadApplicationConstants;
import com.voiceapp.amico.common.ReadResponseMessages;
import com.voiceapp.amico.dto.LinkedUserProfileDto;
import com.voiceapp.amico.dto.StoreInformationDto;
import com.voiceapp.amico.service.AddNewUserService;
import com.voiceapp.amico.service.LockUnlockAppService;
import com.voiceapp.amico.service.RetrieveInformationService;
import com.voiceapp.amico.service.ShareInformationService;
import com.voiceapp.amico.service.StoreInformationService;

/**
 * 
 * @author priyankachaudhary
 * @StartDate 9/4/2019
 * @LastUpdated 15/4/2019
 * With various Intent in Dialogflow different functions need to be mapped against each intent.
 * Intent Controller will get the request from Main Controller when Application URL will be accessed
 * And against each Intent identified from @ForIntent has a function map which processing the required tasks
 * 
 *
 */


@Component
public class IntentController extends DialogflowApp{
		
	
	@Autowired
	private AddNewUserService addNewUserService;
	
	@Autowired
	private StoreInformationService storeInformationService;
	
	@Autowired
	private StoreInformationDto storeInformationDto;
	
	@Autowired
	private GetLinkedUserDetails linkedUserDetails;
	
	@Autowired
	private ReadResponseMessages readResponseMessages;
	
	@Autowired
	private LockUnlockAppService lockUnlockAppService;
	
	@Autowired
	private RetrieveInformationService retrieveInformationService;
	
	@Autowired
	private ShareInformationService shareInformationService;
	
	
	String categoryInfo;
	String email;
	String firstName;
	String lastName;
	String accessToken;
	String welcomeResponse;
	String storeInfoResponse;
	String lockAppResponse;
	String unlockAppResponse;
	String retrieveInfoVoiceResponse;
	String shareInfoResponse;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IntentController.class);
	
	/**
	 * This method will be invoked by main controller when Welcome Intent will be called
	 * It performs following tasks -
	 * 1. Retrieve access token
	 * 2. Get Email id from auth0 authorization server using access token
	 * 3. Check if user exists or not
	 * 4. if user is new then add him/her to the database
	 * 5. Send Welcome response to user
	 * @param request
	 * @return ActionResponse Welcome Message
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@ForIntent("WelcomeIntent")
	public ActionResponse welcome(ActionRequest request) throws IOException, JSONException {
		LOGGER.debug("Request received from Main Controller in Welcome Intent");
		ResponseBuilder rb = getResponseBuilder(request);
		LOGGER.debug("Retrieving access token from request");
		accessToken=request.getUser().getAccessToken();		
		LOGGER.debug("Calling method to get email id of user using accessToken");
		email = linkedUserDetails.getUserEmail(accessToken);
		if(email==null || email.isEmpty()) {
			LOGGER.error("Could not get email id of user");
			welcomeResponse = readResponseMessages.getErrorWelcomeMessage();
		}else {
			LOGGER.debug("Calling AddNewUserService method to add new user in the system");
			welcomeResponse = addNewUserService.addUser(email);
		}
		rb.add(welcomeResponse);
		return rb.build();
	}

	
	/**
	 * This method will be invoked when bye intent will be said by user
	 * @param request
	 * @return response to end the conversation
	 */
	
	@ForIntent("bye")
	 public ActionResponse make_name(ActionRequest request) {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
	    responseBuilder.add(
	        "Bye, Will see you soon.").endConversation();
		 
	    return responseBuilder.build();
	}

	/**
	 * This method will be invoked when intent to store information will be asked by the user
	 * @param request
	 * @throws IOException 
	 * @throws JSONException 
	 * @response response to tell user if information has been saved successfully
	 */
	@ForIntent("StoreInfo")
	public ActionResponse storeInformation(ActionRequest request) throws JSONException, IOException{
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		LOGGER.debug("Received request in StoreInfo intent - Intent Controller ");
		LOGGER.debug("Retrieving access token from request");
		accessToken=request.getUser().getAccessToken();		
		LOGGER.debug("Calling method to get email id of user using accessToken");
		email = linkedUserDetails.getUserEmail(accessToken);
		
		if(email==null || email.isEmpty()) {
			LOGGER.error("Could not get email id of user");
			storeInfoResponse = readResponseMessages.getErrorWelcomeMessage();
		}
		else {
			LOGGER.debug("Got email id of user"+email);
			storeInformationDto = new StoreInformationDto(email, (String) request.getParameter("info_key"), (String) request.getParameter("info_content"),(String) request.getParameter("category_of_info"), "text");
			LOGGER.debug("Calling storeInformation method in StoreInformation Service passing storeInformation DTO ");
			storeInfoResponse = storeInformationService.storeInformation(storeInformationDto);
			LOGGER.debug("Received response from service after Store Information process ");
		}
		
		LOGGER.debug("Building response to return the Google Assistant "+storeInfoResponse);
		responseBuilder.add(storeInfoResponse);
		return responseBuilder.build();
	}
	
	
	/**
	 * This method will be invoked when intent to Lock Personal information will be asked by the user after Storing the infromation
	 * @param request
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@ForIntent("Lock")
	public ActionResponse lockApp(ActionRequest request) throws JSONException, IOException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		LOGGER.debug("Received request in Lock information intent - Intent Controller ");
		LOGGER.debug("Retrieving access token from request");
		accessToken=request.getUser().getAccessToken();		
		LOGGER.debug("Calling method to get email id of user using accessToken");
		email = linkedUserDetails.getUserEmail(accessToken);
		
		if(email==null || email.isEmpty()) {
			LOGGER.error("Could not get email id of user");
			lockAppResponse = readResponseMessages.getErrorWelcomeMessage();
		}
		else {
			LOGGER.debug("Got email id of user"+email);
			LOGGER.debug("Retrieving passcode");
			Double passkey = (Double) request.getParameter("passcode");
			int passcode = passkey.intValue();
			LOGGER.debug("Calling lockApp method in LockUnlock Service passing email id & passcode "+email+" "+passcode);
			lockAppResponse = lockUnlockAppService.lockApp(email, passcode);
			LOGGER.debug("Received response from service after lock App process ");
		}
		
		LOGGER.debug("Building response to return the Google Assistant "+lockAppResponse);
		responseBuilder.add(lockAppResponse);
		return responseBuilder.build();
	}
	
/**
 * This is the method which will be invoked anytime independently to lock the personal infromation
 * @param request
 * @return
 * @throws JSONException
 * @throws IOException
 */
	@ForIntent("LockIndependent")
	public ActionResponse lock_App(ActionRequest request) throws JSONException, IOException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		LOGGER.debug("Received request in LockIndependent information intent - Intent Controller ");
		LOGGER.debug("Retrieving access token from request");
		accessToken=request.getUser().getAccessToken();		
		LOGGER.debug("Calling method to get email id of user using accessToken");
		email = linkedUserDetails.getUserEmail(accessToken);
		
		if(email==null || email.isEmpty()) {
			LOGGER.error("Could not get email id of user");
			lockAppResponse = readResponseMessages.getErrorWelcomeMessage();
		}
		else {
			LOGGER.debug("Got email id of user"+email);
			LOGGER.debug("Retrieving passcode");
			Double passkey = (Double) request.getParameter("passcode");
			int passcode = passkey.intValue();
			LOGGER.debug("Calling lockApp method in LockUnlock Service passing email id & passcode "+email+" "+passcode);
			lockAppResponse = lockUnlockAppService.lockApp(email, passcode);
			LOGGER.debug("Received response from service after lock App process ");
		}
		
		LOGGER.debug("Building response to return the Google Assistant "+lockAppResponse);
		responseBuilder.add(lockAppResponse);
		return responseBuilder.build();
	}
	
/**
 * This method will be invoked when user attempts to unlock their personal information after retrieving the information
 * @param request
 * @return responseBuilder
 * @throws JSONException
 * @throws IOException
 */
	@ForIntent("Unlock")
	public ActionResponse unlockApp(ActionRequest request) throws JSONException, IOException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		LOGGER.debug("Received request in Unlock information intent - Intent Controller ");
		LOGGER.debug("Retrieving access token from request");
		accessToken=request.getUser().getAccessToken();		
		LOGGER.debug("Calling method to get email id of user using accessToken");
		email = linkedUserDetails.getUserEmail(accessToken);
		
		if(email==null || email.isEmpty()) {
			LOGGER.error("Could not get email id of user");
			unlockAppResponse = readResponseMessages.getErrorWelcomeMessage();
		}
		else {
			LOGGER.debug("Got email id of user"+email);
			LOGGER.debug("Retrieving passcode");
			Double passkey = (Double) request.getParameter("passcode");
			int passcode = passkey.intValue();
			LOGGER.debug("Calling unlockApp method in LockUnlock Service passing email id & passcode "+email+" ,"+passcode);
			unlockAppResponse = lockUnlockAppService.unlockApp(email, passcode);
			LOGGER.debug("Received response from service after unlock App process ");
		}
		
		LOGGER.debug("Building response to return the Google Assistant "+unlockAppResponse);
		responseBuilder.add(unlockAppResponse);
		return responseBuilder.build();
	}

/**
 * This method will be invoked when user attempts to unlock their personal information any time independently, without following some intent
 * @param request
 * @return
 * @throws JSONException
 * @throws IOException
 */
	
	@ForIntent("UnlockIndependent")
	public ActionResponse unlock_App(ActionRequest request) throws JSONException, IOException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		LOGGER.debug("Received request in unlock information intent - Intent Controller ");
		LOGGER.debug("Retrieving access token from request");
		accessToken=request.getUser().getAccessToken();		
		LOGGER.debug("Calling method to get email id of user using accessToken");
		email = linkedUserDetails.getUserEmail(accessToken);
		
		if(email==null || email.isEmpty()) {
			LOGGER.error("Could not get email id of user");
			unlockAppResponse = readResponseMessages.getErrorWelcomeMessage();
		}
		else {
			LOGGER.debug("Got email id of user"+email);
			LOGGER.debug("Retrieving passcode");
			Double passkey = (Double) request.getParameter("passcode");
			int passcode = passkey.intValue();
			LOGGER.debug("Calling unlockApp method in LockUnlock Service passing email id & passcode "+email+" ,"+passcode);
			unlockAppResponse = lockUnlockAppService.unlockApp(email, passcode);
			LOGGER.debug("Received response from service after unlock App process ");
		}
		
		LOGGER.debug("Building response to return the Google Assistant "+unlockAppResponse);
		responseBuilder.add(unlockAppResponse);
		return responseBuilder.build();
	}

/**
 * This method will be invoked when user attempts to unlock their personal information as a followed up intent of UnlockShareFollowup
 * @param request
 * @return
 * @throws JSONException
 * @throws IOException
 */
	
	@ForIntent("UnlockShareFollowup")	
	public ActionResponse unlock_share_intent(ActionRequest request) throws JSONException, IOException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		LOGGER.debug("Received request in unlock information intent - Intent Controller ");
		LOGGER.debug("Retrieving access token from request");
		accessToken=request.getUser().getAccessToken();		
		LOGGER.debug("Calling method to get email id of user using accessToken");
		email = linkedUserDetails.getUserEmail(accessToken);
		
		if(email==null || email.isEmpty()) {
			LOGGER.error("Could not get email id of user");
			unlockAppResponse = readResponseMessages.getErrorWelcomeMessage();
		}
		else {
			LOGGER.debug("Got email id of user"+email);
			LOGGER.debug("Retrieving passcode");
			Double passkey = (Double) request.getParameter("passcode");
			int passcode = passkey.intValue();
			LOGGER.debug("Calling unlockApp method in LockUnlock Service passing email id & passcode "+email+" ,"+passcode);
			unlockAppResponse = lockUnlockAppService.unlockApp(email, passcode);
			LOGGER.debug("Received response from service after unlock App process ");
		}
		
		LOGGER.debug("Building response to return the Google Assistant "+unlockAppResponse);
		responseBuilder.add(unlockAppResponse);
		return responseBuilder.build();
	}

/**
 * This method will be invoked when user will ask to retrieve the information over voice
 * @param request
 * @return
 * @throws JSONException
 * @throws IOException
 */
	@ForIntent("RetrieveInfo")
	public ActionResponse retrieveInfoOverVoice(ActionRequest request) throws JSONException, IOException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		LOGGER.debug("Received request in retrieve information intent - Intent Controller ");
		LOGGER.debug("Retrieving access token from request");
		accessToken=request.getUser().getAccessToken();		
		LOGGER.debug("Calling method to get email id of user using accessToken");
		email = linkedUserDetails.getUserEmail(accessToken);
		
		if(email==null || email.isEmpty()) {
			LOGGER.error("Could not get email id of user");
			unlockAppResponse = readResponseMessages.getErrorWelcomeMessage();
		}
		else {
			LOGGER.debug("Got email id of user"+email);
			LOGGER.debug("Retrieving infomation key");
			String info_key = (String) request.getParameter("info_key");
			LOGGER.debug("Calling retrieveInfoOverVoice method in RetrieveInformationService passing email id & info key "+email+" ,"+info_key);
			retrieveInfoVoiceResponse = retrieveInformationService.retrieveInfoOverVoice(email, info_key);
			LOGGER.debug("Received response from service after retrieving information process ");
		}
		
		LOGGER.debug("Building response to return the Google Assistant "+retrieveInfoVoiceResponse);
		responseBuilder.add(retrieveInfoVoiceResponse);
		return responseBuilder.build();
	}
	
/**
 * This method will be invoked when user will ask to retrieve the information over user's email id
 * @param request
 * @return
 * @throws JSONException
 * @throws IOException
 */
	@ForIntent("RetrieveInfoMail")
	public ActionResponse retrieveInfoOverMail(ActionRequest request) throws JSONException, IOException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		LOGGER.debug("Received request in retrieve information over mail intent - Intent Controller ");
		LOGGER.debug("Retrieving access token from request");
		accessToken=request.getUser().getAccessToken();		
		LOGGER.debug("Calling method to get email id of user using accessToken");
		email = linkedUserDetails.getUserEmail(accessToken);
		
		if(email==null || email.isEmpty()) {
			LOGGER.error("Could not get email id of user");
			unlockAppResponse = readResponseMessages.getErrorWelcomeMessage();
		}
		else {
			LOGGER.debug("Got email id of user"+email);
			LOGGER.debug("Retrieving infomation key");
			String info_key = (String) request.getParameter("info_key");
			LOGGER.debug("Calling retrieveInfoOverMail method in RetrieveInformationService passing email id & info key "+email+" ,"+info_key);
			retrieveInfoVoiceResponse = retrieveInformationService.retrieveInfoOverMail(email, info_key);
			LOGGER.debug("Received response from service after retrieving information over mail process ");
		}
		
		LOGGER.debug("Building response to return the Google Assistant "+retrieveInfoVoiceResponse);
		responseBuilder.add(retrieveInfoVoiceResponse);
		return responseBuilder.build();
	}
	
/**
 * 
 * 
 * @param request
 * @return
 * @throws JSONException
 * @throws IOException
 */
	@ForIntent("ShareInfo")
	public ActionResponse shareInformation(ActionRequest request) throws JSONException, IOException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		LOGGER.debug("Received request in share information over mail intent - Intent Controller ");
		LOGGER.debug("Retrieving access token from request");
		accessToken=request.getUser().getAccessToken();		
		LOGGER.debug("Calling method to get email id of user using accessToken");
		email = linkedUserDetails.getUserEmail(accessToken);
		
		if(email==null || email.isEmpty()) {
			LOGGER.error("Could not get email id of user");
			unlockAppResponse = readResponseMessages.getErrorWelcomeMessage();
		}
		else {
			String username = linkedUserDetails.getUsername(accessToken);
			LOGGER.debug("Got email id & username of user"+email+username);
			LOGGER.debug("Retrieving infomation key");
			String info_key = (String) request.getParameter("info_key");
			LOGGER.debug("Retrieving platform");
			String platform = (String) request.getParameter("platform");
			if(platform.equals("text message")) {
				LOGGER.debug("Calling retrieveInfoOverMail method in RetrieveInformationService passing email id & info key "+email+" ,"+info_key);
				shareInfoResponse = "Work is in progress";
				LOGGER.debug("Received response from service after retrieving information over mail process ");
			}
			else if(platform.equals("whatsapp")) {
				LOGGER.debug("Calling retrieveInfoOverMail method in RetrieveInformationService passing email id & info key "+email+" ,"+info_key);
				shareInfoResponse = "Work is in progress";
				LOGGER.debug("Received response from service after retrieving information over mail process ");
			}
			else {
				
				String receivermail = linkedUserDetails.getElementFromParameterString(request.getParameter("receiver_contact").toString(), "email_id");
				LOGGER.debug("Calling retrieveInfoOverMail method in RetrieveInformationService passing email id & info key "+email+" ,"+info_key);
				shareInfoResponse = shareInformationService.shareInformationOverMail(email, info_key, receivermail, username);
				LOGGER.debug("Received response from service after retrieving information over mail process ");
			}
			
		}
		
		LOGGER.debug("Building response to return the Google Assistant "+shareInfoResponse);
		responseBuilder.add(shareInfoResponse);
		return responseBuilder.build();
	}
}
