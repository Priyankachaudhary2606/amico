package com.voiceapp.amico.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 
 * @author priyankachoudhary
 *
 */

@Component
@PropertySource(value="classpath:sql/storeIndividualInfo.yml")
@ConfigurationProperties(prefix="storeIndividualinfo")
public class StoreIndividualInfoConfig {
	
	  @Value("${insertIndividualinformation}")
	     private String insertIndividualinformation;
	  
	  @Value("${updateIndividualinfo}")
	     private String updateIndividualinfo;

	public String getInsertIndividualinformation() {
		return insertIndividualinformation;
	}

	public void setInsertIndividualinformation(String insertIndividualinformation) {
		this.insertIndividualinformation = insertIndividualinformation;
	}

	public String getUpdateIndividualinfo() {
		return updateIndividualinfo;
	}

	public void setUpdateIndividualinfo(String updateIndividualinfo) {
		this.updateIndividualinfo = updateIndividualinfo;
	}
	  
	  

}
