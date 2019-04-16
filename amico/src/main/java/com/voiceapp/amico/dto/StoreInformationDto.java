package com.voiceapp.amico.dto;

import org.springframework.stereotype.Component;

import com.voiceapp.amico.common.BaseDto;

@Component
public class StoreInformationDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
	private String userEmail;
	private String infoKey;
	private String infoContent;
	private String categoryOfInfo;
	private String typeOfInfo;
	
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getInfoKey() {
		return infoKey;
	}
	public void setInfoKey(String infoKey) {
		this.infoKey = infoKey;
	}
	public String getInfoContent() {
		return infoContent;
	}
	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}
	public String getCategoryOfInfo() {
		return categoryOfInfo;
	}
	public void setCategoryOfInfo(String categoryOfInfo) {
		this.categoryOfInfo = categoryOfInfo.toLowerCase();
	}
	public String getTypeOfInfo() {
		return typeOfInfo;
	}
	public void setTypeOfInfo(String typeOfInfo) {
		this.typeOfInfo = typeOfInfo;
	}
	
	
	/**
	 * Default Constructor
	 */
	public StoreInformationDto() {
		super();
		
	}
	
	/**
	 * Parameterized Constructor
	 * @param userEmail
	 * @param infoKey
	 * @param infoContent
	 * @param categoryOfInfo
	 * @param typeOfInfo
	 */
	public StoreInformationDto(String userEmail, String infoKey, String infoContent, String categoryOfInfo,
			String typeOfInfo) {
		super();
		this.userEmail = userEmail;
		this.infoKey = infoKey;
		this.infoContent = infoContent;
		this.categoryOfInfo = categoryOfInfo.toLowerCase();
		this.typeOfInfo = typeOfInfo;
	}

}
