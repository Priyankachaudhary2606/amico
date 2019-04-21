package com.voiceapp.amico.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:applicationConstants.properties")
@ConfigurationProperties(prefix="constant")
public class ReadApplicationConstants {

    private String personalCategoryOfInfo;
    private String generalCategoryOfInfo;
    private String authUserInfoUrl;
    private String textTypeOfInfo;
    private String fileTypeOfInfo;
    private String emailSender;
    private String host;
    private String port;
    private String senderPassword;
    private String smskey;
    private String senderId;
    private String smsUrl;
    
    
    
	public String getSmskey() {
		return smskey;
	}

	public void setSmskey(String smskey) {
		this.smskey = smskey;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public String getSenderPassword() {
		return senderPassword;
	}

	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}

	public String getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(String emailSender) {
		this.emailSender = emailSender;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTextTypeOfInfo() {
		return textTypeOfInfo;
	}

	public void setTextTypeOfInfo(String textTypeOfInfo) {
		this.textTypeOfInfo = textTypeOfInfo;
	}

	public String getFileTypeOfInfo() {
		return fileTypeOfInfo;
	}

	public void setFileTypeOfInfo(String fileTypeOfInfo) {
		this.fileTypeOfInfo = fileTypeOfInfo;
	}

	public String getAuthUserInfoUrl() {
		return authUserInfoUrl;
	}

	public void setAuthUserInfoUrl(String authUserInfoUrl) {
		this.authUserInfoUrl = authUserInfoUrl;
	}

	public String getPersonalCategoryOfInfo() {
		return personalCategoryOfInfo;
	}

	public void setPersonalCategoryOfInfo(String personalCategoryOfInfo) {
		this.personalCategoryOfInfo = personalCategoryOfInfo;
	}

	public String getGeneralCategoryOfInfo() {
		return generalCategoryOfInfo;
	}

	public void setGeneralCategoryOfInfo(String generalCategoryOfInfo) {
		this.generalCategoryOfInfo = generalCategoryOfInfo;
	}

    
}

