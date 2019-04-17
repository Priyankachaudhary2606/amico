package com.voiceapp.amico.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:applicationResponseMessages.properties")
@ConfigurationProperties(prefix="response")
public class ReadResponseMessages {

	private String informationStoredSuccessfully1;
	private String informationStoredSuccessfully2;
	private String passcodeIsEmpty;
	private String errorStoreInformation;
	private String welcomeMessage;
	private String errorWelcomeMessage;	
	
	public String getErrorWelcomeMessage() {
		return errorWelcomeMessage;
	}
	public void setErrorWelcomeMessage(String errorWelcomeMessage) {
		this.errorWelcomeMessage = errorWelcomeMessage;
	}
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
	public String getInformationStoredSuccessfully1() {
		return informationStoredSuccessfully1;
	}
	public void setInformationStoredSuccessfully1(String informationStoredSuccessfully1) {
		this.informationStoredSuccessfully1 = informationStoredSuccessfully1;
	}
	public String getInformationStoredSuccessfully2() {
		return informationStoredSuccessfully2;
	}
	public void setInformationStoredSuccessfully2(String informationStoredSuccessfully2) {
		this.informationStoredSuccessfully2 = informationStoredSuccessfully2;
	}
	public String getPasscodeIsEmpty() {
		return passcodeIsEmpty;
	}
	public void setPasscodeIsEmpty(String passcodeIsEmpty) {
		this.passcodeIsEmpty = passcodeIsEmpty;
	}
	public String getErrorStoreInformation() {
		return errorStoreInformation;
	}
	public void setErrorStoreInformation(String errorStoreInformation) {
		this.errorStoreInformation = errorStoreInformation;
	}
	
}
