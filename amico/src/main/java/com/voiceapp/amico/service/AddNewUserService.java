package com.voiceapp.amico.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voiceapp.amico.common.ReadResponseMessages;
import com.voiceapp.amico.dao.AddUserDao;

/**
 * 
 * @author priyankachaudhary
 * @date 10/04/2019
 * LinkUserService will be used to check if UserLinked to application exists in our system
 * Or if it is a new User then add it to the System
 * It will have two methods:
 * 1. checkUserExistence
 * 2. saveNewUser
 * which will be invoked by getSignInStatus method from IntentController
 * 
 * @reviewed_by -
 *
 */

@Service
public class AddNewUserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AddNewUserService.class);
	
	@Autowired
	private AddUserDao addUserDao;
	
	@Autowired
	private ReadResponseMessages readResponseMessages;
	
	String addUserResponse;
	int checkUserExistenceStatus;
	int saveUserStatus;
	
	/**
	 * This method will be invoked by getSignInStatus method, to perform following tasks:
	 * 1. Check if linked user exists in our system or not
	 * 2. If user does not exist then save the new User by calling saveNewUser method
	 * 3. Return response to controller
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @return response to reply to user
	 */
	public String addUser(String email) {
		LOGGER.debug("Request received from Welcome Intent Controller to add"+ email +"in database ");
		LOGGER.debug("Calling method to check if user already exists");
		checkUserExistenceStatus = this.checkUserExistence(email);
		LOGGER.debug("Status of user existence "+checkUserExistenceStatus);
		if(checkUserExistenceStatus==0) {
			LOGGER.debug("User doesn't exists in the system");
			LOGGER.debug("Calling method to add new user in the system");
			saveUserStatus = this.saveNewUser(email);
			if(saveUserStatus==-1) {
				LOGGER.error("Could not add user to the system");
				addUserResponse = readResponseMessages.getErrorWelcomeMessage();
				return addUserResponse;
			}
			else {
				LOGGER.debug("User saved successfully in the system");
				addUserResponse = readResponseMessages.getWelcomeMessage();
				return addUserResponse;
			}
		}
		else {
			LOGGER.debug("User already exists in the system");
			addUserResponse=readResponseMessages.getWelcomeMessage();
			LOGGER.debug("Returning welcome response to user "+addUserResponse);
			return addUserResponse;
		}
		
	}
	
	/**
	 * This method will be invoked by checkUserExistence method, to save new user in the database by calling DAO method which will do that
	 * @param email
	 * @return saveUserStatus
	 */
	public int saveNewUser(String email) {
		LOGGER.debug("In saveNewUser method - AddNewUser Service");
		LOGGER.debug("Calling DAO method to save new user in the system");
		saveUserStatus = addUserDao.saveNewUser(email);
		return saveUserStatus;
		
	}
	
	/**
	 * This method will check if user Exists in the database or not by calling respection method in DAO
	 * @param email
	 * @return checkUserExistenceStatus
	 */
	public int checkUserExistence(String email) {
		LOGGER.debug("In checkUserExistence method - AddNewUser Service");
		LOGGER.debug("Calling DAO method to check if user already exists or not");
		checkUserExistenceStatus = addUserDao.checkUserExistence(email);
		return checkUserExistenceStatus;
	}
	
	
	
}
