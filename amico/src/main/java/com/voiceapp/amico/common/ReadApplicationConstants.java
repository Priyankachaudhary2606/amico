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

