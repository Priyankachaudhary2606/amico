package com.voiceapp.amico.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value="classpath:sql/forgotPasscode.yml")
@ConfigurationProperties(prefix="forgotpasscode")
public class ForgotPasscodeConfig {
	
	@Value("${replacePasscode}")
    private String replacePasscode;

	public String getReplacePasscode() {
		return replacePasscode;
	}

	public void setReplacePasscode(String replacePasscode) {
		this.replacePasscode = replacePasscode;
	}

}
