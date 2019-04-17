package com.voiceapp.amico.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:sql/linkUser.yml")
@ConfigurationProperties(prefix="link")
public class AddNewUserConfig {

	 @Value("${checkUserExistenceSql}")
	private String checkUserExistenceSql;
	 
	 @Value("${saveNewUserSql}")
	private String saveNewUserSql;
	
	public String getCheckUserExistenceSql() {
		return checkUserExistenceSql;
	}
	public void setCheckUserExistenceSql(String checkUserExistenceSql) {
		this.checkUserExistenceSql = checkUserExistenceSql;
	}
	public String getSaveNewUserSql() {
		return saveNewUserSql;
	}
	public void setSaveNewUserSql(String saveNewUserSql) {
		this.saveNewUserSql = saveNewUserSql;
	}
		
	
}
