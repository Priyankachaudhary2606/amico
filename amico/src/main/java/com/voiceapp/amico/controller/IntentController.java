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
import com.voiceapp.amico.dto.StoreInformationDto;
import com.voiceapp.amico.service.AddNewUserService;
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
 *	@Reviewed By -
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
	
	
	String categoryInfo;
	String email;
	String firstName;
	String lastName;
	String accessToken;
	String welcomeResponse;
	String storeInfoResponse;
	
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
	@ForIntent("Welcome Intent")
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

}
