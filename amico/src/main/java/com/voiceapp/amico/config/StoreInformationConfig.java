package com.voiceapp.amico.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.voiceapp.amico.dao.StoreInformationDao;

/**
 * 
 * @author priyankachoudhary
 * This is the configuration class to save data in database
 * @LastUpdated - 16/4/2019
 */

@Component
@PropertySource(value="classpath:sql/storeInformation.yml")
@ConfigurationProperties(prefix="storeinfo")
public class StoreInformationConfig {
	
	  @Value("${storeinformation}")
     private String storeinformation;
	  
	  @Value("${updateinfo}")
     private String updateinfo;
	  
	  @Value("${checkpasscodeexists}")
     private String checkpasscodeexists;
	  
	  @Value("${checkrecordexists}")
     private String checkrecordexists;
     
     private static final Logger LOGGER = LoggerFactory.getLogger(StoreInformationConfig.class);
   
	public String getStoreinformation() {
		return storeinformation;
	}
	public void setStoreinformation(String storeinformation) {
		this.storeinformation = storeinformation;
	}
	public String getUpdateinfo() {
		return updateinfo;
	}
	public void setUpdateinfo(String updateinfo) {
		this.updateinfo = updateinfo;
	}
	public String getCheckpasscodeexists() {
		LOGGER.debug("Entered in Config SQL");
		return checkpasscodeexists;
	}
	public void setCheckpasscodeexists(String checkpasscodeexists) {
		this.checkpasscodeexists = checkpasscodeexists;
	}
	public String getCheckrecordexists() {
		return checkrecordexists;
	}
	public void setCheckrecordexists(String checkrecordexists) {
		this.checkrecordexists = checkrecordexists;
	}

}
