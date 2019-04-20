package com.voiceapp.amico.dto;

import com.voiceapp.amico.common.BaseDto;

public class StoreIndividualInfoDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user_email;
	private String contact_name;
	private String contact_detail;
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_detail() {
		return contact_detail;
	}
	public void setContact_detail(String contact_detail) {
		this.contact_detail = contact_detail;
	}
	public StoreIndividualInfoDto(String user_email, String contact_name, String contact_detail) {
		super();
		this.user_email = user_email;
		this.contact_name = contact_name;
		this.contact_detail = contact_detail;
	}
	
	public StoreIndividualInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
