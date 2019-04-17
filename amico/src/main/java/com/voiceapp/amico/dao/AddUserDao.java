package com.voiceapp.amico.dao;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.voiceapp.amico.common.AbstractTransactionalDao;
import com.voiceapp.amico.config.AddNewUserConfig;

/**
 * 
 * @author priyankachaudhary
 * @date 10/4/2019
 * LinkUserDao will be used to complete the linking process of User by getting User in our System
 * It will have two methods:
 * 1. checkUserExistence
 * 2. saveNewUser
 * 
 * @reviewed_by
 *
 */

@Repository
public class AddUserDao extends AbstractTransactionalDao{
	
	@Autowired
	private AddNewUserConfig addNewUserConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AddUserDao.class);
	
	public int checkUserExistence(String user_email) {
		LOGGER.debug("Received request in checkUserExistence method in AddUserDao from service" +user_email);
		LOGGER.debug("Initializing Hashmap");
		Map<String,Object> email = new HashMap<>();
		LOGGER.debug("Adding email into hashmap");
		email.put("user_email", user_email);
		LOGGER.debug("Executing query to check if user exists in database table user");
		int userExistenceStatus= getJdbcTemplate().queryForObject(addNewUserConfig.getCheckUserExistenceSql(), email, Integer.class);
		LOGGER.debug("Returning status of userExistence "+userExistenceStatus);
		return userExistenceStatus;
	}
	
	public int saveNewUser(String email) {
		LOGGER.debug("Received request in saveNewUser method in AddUserDao from service " +email);
		LOGGER.debug("Initializing Hashmap");
		Map<String,Object> user_email = new HashMap<>();
		LOGGER.debug("Adding email into hashmap");
		user_email.put("user_email", email);
		int addNewUser;
		try {
			LOGGER.debug("Executing query to save new user details in database");
			addNewUser = getJdbcTemplate().update(addNewUserConfig.getSaveNewUserSql(), user_email);
		} catch (DataAccessException e) {
			LOGGER.error("Error occured while saving new user in the system"+e);
			addNewUser=-1;
		}
		LOGGER.debug("Returning, Status of adding new user is "+addNewUser);
		return addNewUser;
	}

}
