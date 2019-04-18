package com.voiceapp.amico.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value="classpath:sql/appUtility.yml")
@ConfigurationProperties(prefix="utility")
public class AppUtilityConfig {

	@Value("${checkpasscodeexists}")
    private String checkpasscodeexists;
	
	 @Value("${checkrecordexists}")
     private String checkrecordexists;
	 
	 @Value("${infodetails}")
	 private String infodetails;

	public String getInfodetails() {
		return infodetails;
	}

	public void setInfodetails(String infodetails) {
		this.infodetails = infodetails;
	}

	public String getCheckrecordexists() {
		return checkrecordexists;
	}

	public void setCheckrecordexists(String checkrecordexists) {
		this.checkrecordexists = checkrecordexists;
	}

	public String getCheckpasscodeexists() {
		return checkpasscodeexists;
	}

	public void setCheckpasscodeexists(String checkpasscodeexists) {
		this.checkpasscodeexists = checkpasscodeexists;
	}
	
}
