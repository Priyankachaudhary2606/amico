package com.voiceapp.amico.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;

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
import com.voiceapp.amico.common.ReadApplicationConstants;
import com.voiceapp.amico.dto.StoreInformationDto;
import com.voiceapp.amico.service.GetDataForUser;
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
	private GetDataForUser getDataForUser;
	
	@Autowired
	private StoreInformationService storeInformationService;
	
	@Autowired
	private StoreInformationDto storeInformationDto;
	
	public String categoryInfo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IntentController.class);
	
	String email;
	String firstName;
	String lastName;


	private String clientId = "471811019353-o3bdo4g9ruoflde3uk09upr8api132jr.apps.googleusercontent.com";
	
	/**
	 * This method will be invoked by main controller when Welcome Intent will be called
	 * It performs following tasks -
	 * 1. Sends request to the Sign In Intent method to get User linked to the app
	 * 2. Returns the response received from Sign In Intent method
	 * @param request
	 * @return ActionResponse Welcome Message
	 */

	
	@ForIntent("Welcome Intent")
	public ActionResponse welcome(ActionRequest request) {
		LOGGER.debug("Request received from Main Controller in Wecome Intent");
		ResponseBuilder rb = getResponseBuilder(request);
		LOGGER.debug("Sending Request to getSignInStatus method to check if user has signed In or not");
	  return rb.add(new SignIn().setContext("To get your account details")).build();
	}
	
	/**
	 * This method will be invoked by main controller will get request from sign_in Intent
	 * Or from welcome Intent method to check if user is signed in & to get user profile
	 * It performs following tasks - 
	 * 1. Check if User has signed In
	 * 2. Send requests to Link User Service to check User Existence & save the new User
	 * 3. Returns the response message on the basis of Sign In status of User 
	 * @param request
	 * @return ActionResponse welcome message
	 */
	
	@ForIntent("sign_in")
	public ActionResponse getSignInStatus(ActionRequest request) {
		LOGGER.debug("Request received from welcome method in getSignInStatus");
		
	  ResponseBuilder responseBuilder = getResponseBuilder(request);
	  LOGGER.debug("Check if user has signed In or not");
	  
	  if (request.isSignInGranted()) {
		LOGGER.debug("The user has signed in");
		LOGGER.debug("Getting User Profile");
	    GoogleIdToken.Payload profile = getUserProfile(request.getUser().getIdToken());
	    
	    LOGGER.debug("User has linked from email id "+ profile.getEmail());
	    LOGGER.debug("Sending request to GetDataForUser-getUserData method passing profile details");
	    email = profile.getEmail();
	    firstName =(String) profile.get("given_name");
	    lastName = (String) profile.get("family_name");
	    String responseFromService = getDataForUser.getUserData(email, firstName, lastName);
	    responseBuilder.add(
	        "Hey "
	            + profile.get("given_name")
	            + " How can I help you with my remembering skills");
	    
	  } else {
		  
		  LOGGER.debug("The user has not signed in");
		  LOGGER.debug("Returning response -- User can come again when he/she wants to sign in & continue");
	    responseBuilder.add("Ohh! I won't be able to help you, but I can keep your things safe & accessible anytime anywhere. Hope to see you soon.").endConversation();
	  }
	  return responseBuilder.build();
	}

	/**
	 * This method will be invoked by getSignInStatus Method, to perform following tasks:
	 * 1. Get the profile of User from idToken sent in the request
	 * @param idToken
	 * @return profile of User having email, name, locale & data in userStorage
	 */
	private GoogleIdToken.Payload getUserProfile(String idToken) {
	  GoogleIdToken.Payload profile = null;
	  try {
	    profile = decodeIdToken(idToken);
	  } catch (Exception e) {
	    LOGGER.error("error decoding idtoken");
	    LOGGER.error(e.toString());
	  }
	  return profile;
	}
	
	
	/**
	 * This method will be invoked by getUserProfile method of SignIn process, to perform following tasks:
	 * 1. Decode the idToken received in the request for SignIn
	 * 2. Verified the user
	 * 3. Returns the decoded idToken having information about User
	 * @param idTokenString
	 * @return decoded idToken
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	private GoogleIdToken.Payload decodeIdToken(String idTokenString)
	    throws GeneralSecurityException, IOException {
	  HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
	  JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
	  GoogleIdTokenVerifier verifier =
	      new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
	          .setAudience(Collections.singletonList(clientId))
	          .build();
	  GoogleIdToken idToken = verifier.verify(idTokenString);
	  return idToken.getPayload();
	}

	
	/**
	 * This method will be invoked when bye intent will be said by user
	 * @param request
	 * @return response to end the conversation
	 */
	
	@ForIntent("bye")
	 public ActionResponse make_name(ActionRequest request) {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
	
		GoogleIdToken.Payload profile = getUserProfile(request.getUser().getIdToken());
	    responseBuilder.add(
	        "Hey "
	            + profile.get("given_name")
	            + " How can I help you with my remembering skills").endConversation();
		 
	    return responseBuilder.build();
	}

	/**
	 * This method will be invoked when intent to store information will be asked by the user
	 * @param request
	 * @response response to tell user if information has been saved successfully
	 */
	@ForIntent("StoreInfo")
	public ActionResponse storeInformation(ActionRequest request){
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		LOGGER.debug("Received request in StoreInfo intent - Intent Controller ");
		LOGGER.debug("Adding Parameters received in StoreInformation DTO ");
		storeInformationDto = new StoreInformationDto("priyanka.chaudhary266@gmail.com", (String) request.getParameter("info_key"), (String) request.getParameter("info_content"),(String) request.getParameter("category_of_info"), "text");
		LOGGER.debug("Calling storeInformation method in StoreInformation Service passing storeInformation DTO ");
		String responsefromService = storeInformationService.storeInformation(storeInformationDto);
		LOGGER.debug("Received response from service after Store Information process -- "+responsefromService);
		LOGGER.debug("Building response to return the Google Assistant ");
		responseBuilder.add(responsefromService);
		return responseBuilder.build();
	}

}
