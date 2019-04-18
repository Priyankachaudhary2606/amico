package com.voiceapp.amico.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:sql/lockUnlockApp.yml")
@ConfigurationProperties(prefix="applock")
public class LockUnlockAppConfig {

	 @Value("${lockapp}")
	 	private String lockapp;
	 
	 @Value("${unlockapp}")
		private String unlockapp;
	 
	 @Value("${savepasscode}")
		private String savepasscode;
	 
	 @Value("${matchpasscode}")
		private String matchpasscode;

	public String getLockapp() {
		return lockapp;
	}

	public void setLockapp(String lockapp) {
		this.lockapp = lockapp;
	}

	public String getUnlockapp() {
		return unlockapp;
	}

	public void setUnlockapp(String unlockapp) {
		this.unlockapp = unlockapp;
	}

	public String getSavepasscode() {
		return savepasscode;
	}

	public void setSavepasscode(String savepasscode) {
		this.savepasscode = savepasscode;
	}

	public String getMatchpasscode() {
		return matchpasscode;
	}

	public void setMatchpasscode(String matchpasscode) {
		this.matchpasscode = matchpasscode;
	}
	 
	 
}
