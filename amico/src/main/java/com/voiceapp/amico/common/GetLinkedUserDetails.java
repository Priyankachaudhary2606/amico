package com.voiceapp.amico.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voiceapp.amico.service.StoreInformationService;

/**
 * 
 * @author priyankachoudhary
 *This is a utility class to get details of user linked
 *And Adding new user to the system
 *@reviewed_by 
 */


@Component
public class GetLinkedUserDetails {
	
	@Autowired
	private ReadApplicationConstants readApplicationConstants;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GetLinkedUserDetails.class);

	public String getLinkedUserDetail(String accessToken) throws IOException{
		
		LOGGER.debug("Request received in method - getLinkedUserDetail");
		
		URL url = new URL(readApplicationConstants.getAuthUserInfoUrl());
		LOGGER.debug("Initialized URL to get user details "+ url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        LOGGER.debug("Adding authorization header in the HTTP request");
        conn.setRequestProperty("Authorization","Bearer "+ accessToken);
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output;

        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        LOGGER.debug("Got userdetails using access Token "+ response.toString());
        return response.toString();
	}
	
	public String getUserEmail(String accessToken) throws JSONException, IOException {
		LOGGER.debug("Received request to get email id using access token");
		LOGGER.debug("Calling getLinkedUserDetail method");
		String userDetails = getLinkedUserDetail(accessToken);
		LOGGER.debug("Received all the details of user linked");
		JSONObject bodyObject = new JSONObject(userDetails);
		LOGGER.debug("Retrieving email from all the user details");
	    String email = bodyObject.getString("email");
	    LOGGER.debug("Returning email id to controller"+email);
		return email;
	}
	public String getUsername(String accessToken) throws JSONException, IOException {
		LOGGER.debug("Received request to get name of user using access token");
		LOGGER.debug("Calling getLinkedUserDetail method");
		String userDetails = getLinkedUserDetail(accessToken);
		LOGGER.debug("Received all the details of user linked");
		JSONObject bodyObject = new JSONObject(userDetails);
		LOGGER.debug("Retrieving nickname from all the user details");
	    String name = bodyObject.getString("nickname");
	    LOGGER.debug("Returning name id to controller"+name); 
		return name;
	}
	
	public String getElementFromParameterString(String parameterString, String element) {
		parameterString =  parameterString.replaceAll("=", "\":\"");
		LOGGER.debug("Parameter String to be parsed is "+parameterString);
		parameterString = parameterString.replaceAll("\\{", "{\"");
		parameterString = parameterString.replaceAll("\\}", "\"}");
		parameterString = parameterString.replaceAll(",", "\",\"");
		LOGGER.debug("Parsed parameter String is "+parameterString +" for element "+element);
		String retreivedElementValue=null;
		try {
			JSONObject bodyObject = new JSONObject(parameterString);
			LOGGER.debug("Retrieving "+element+" of receiver from all the user details");
			retreivedElementValue = bodyObject.getString(element);
		} catch (JSONException e) {
			return null;
		}
	    LOGGER.debug("Returning "+element+" of receiver to controller"+retreivedElementValue); 
		return retreivedElementValue;
		
	}
}
