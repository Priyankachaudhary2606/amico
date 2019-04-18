package com.voiceapp.amico.dto;

import com.voiceapp.amico.common.BaseDto;

/**
 * 
 * @author priyankachoudhary
 * This is POJO class to get all the details against an info_key for a respective user
 *
 */
public class InformationDetailsDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int info_id;
	private String info_key;
	private String info_content;
	private String type_of_info;
	private String category_of_info;
	private String user_email;
	private int passcode;
	private int lock_flag;

	public int getInfo_id() {
		return info_id;
	}

	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}

	public String getInfo_key() {
		return info_key;
	}

	public void setInfo_key(String info_key) {
		this.info_key = info_key;
	}

	public String getInfo_content() {
		return info_content;
	}

	public void setInfo_content(String info_content) {
		this.info_content = info_content;
	}

	public String getType_of_info() {
		return type_of_info;
	}

	public void setType_of_info(String type_of_info) {
		this.type_of_info = type_of_info;
	}

	public String getCategory_of_info() {
		return category_of_info;
	}

	public void setCategory_of_info(String category_of_info) {
		this.category_of_info = category_of_info;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public int getPasscode() {
		return passcode;
	}

	public void setPasscode(int passcode) {
		this.passcode = passcode;
	}

	public int getLock_flag() {
		return lock_flag;
	}

	public void setLock_flag(int lock_flag) {
		this.lock_flag = lock_flag;
	}
	
	/**
	 * Constructor using fields
	 * @param info_id
	 * @param info_key
	 * @param info_content
	 * @param type_of_info
	 * @param category_of_info
	 * @param user_email
	 * @param passcode
	 * @param lock_flag
	 */

	public InformationDetailsDto(int info_id, String info_key, String info_content, String type_of_info,
			String category_of_info, String user_email, int passcode, int lock_flag) {
		super();
		this.info_id = info_id;
		this.info_key = info_key;
		this.info_content = info_content;
		this.type_of_info = type_of_info;
		this.category_of_info = category_of_info;
		this.user_email = user_email;
		this.passcode = passcode;
		this.lock_flag = lock_flag;
	}

	/**
	 * Super class constructor
	 */
	public InformationDetailsDto() {
		super();
	}

	

}
