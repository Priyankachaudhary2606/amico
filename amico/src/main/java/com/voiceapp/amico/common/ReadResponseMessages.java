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
	private String lockAppSuccessMessage;
	private String lockAppFailedMessage;
	private String saveCodeLockAppSuccessMessage1;
	private String saveCodeLockAppSuccessMessage2;
	private String saveCodeLockFailedMessage1;
	private String saveCodeLockFailedMessage2;
	private String passcodeMismatch;
	private String unlockPasscodeEmpty;
	private String unlockFailedMessage;
	private String unlockSuccessFulMessage;
	private String noInfoFound;
	private String mailedInfo;
	private String mailedUnsuccessful;
	private String voiceOutInfo1;
	private String voiceOutInfo2;
	private String voiceOutInfo3;
	private String infoLocked1;
	private String infoLocked2;
	private String mailSent1;
	private String mailSent2;
	private String invalidEmail;
			
	public String getInvalidEmail() {
		return invalidEmail;
	}
	public void setInvalidEmail(String invalidEmail) {
		this.invalidEmail = invalidEmail;
	}
	public String getMailSent1() {
		return mailSent1;
	}
	public void setMailSent1(String mailSent1) {
		this.mailSent1 = mailSent1;
	}
	public String getMailSent2() {
		return mailSent2;
	}
	public void setMailSent2(String mailSent2) {
		this.mailSent2 = mailSent2;
	}
	public String getNoInfoFound() {
		return noInfoFound;
	}
	public void setNoInfoFound(String noInfoFound) {
		this.noInfoFound = noInfoFound;
	}
	public String getMailedInfo() {
		return mailedInfo;
	}
	public void setMailedInfo(String mailedInfo) {
		this.mailedInfo = mailedInfo;
	}
	
	public String getMailedUnsuccessful() {
		return mailedUnsuccessful;
	}
	public void setMailedUnsuccessful(String mailedUnsuccessful) {
		this.mailedUnsuccessful = mailedUnsuccessful;
	}
	public String getVoiceOutInfo1() {
		return voiceOutInfo1;
	}
	public void setVoiceOutInfo1(String voiceOutInfo1) {
		this.voiceOutInfo1 = voiceOutInfo1;
	}
	public String getVoiceOutInfo2() {
		return voiceOutInfo2;
	}
	public void setVoiceOutInfo2(String voiceOutInfo2) {
		this.voiceOutInfo2 = voiceOutInfo2;
	}
	public String getVoiceOutInfo3() {
		return voiceOutInfo3;
	}
	public void setVoiceOutInfo3(String voiceOutInfo3) {
		this.voiceOutInfo3 = voiceOutInfo3;
	}
	public String getInfoLocked1() {
		return infoLocked1;
	}
	public void setInfoLocked1(String infoLocked1) {
		this.infoLocked1 = infoLocked1;
	}
	public String getInfoLocked2() {
		return infoLocked2;
	}
	public void setInfoLocked2(String infoLocked2) {
		this.infoLocked2 = infoLocked2;
	}
	public String getUnlockPasscodeEmpty() {
		return unlockPasscodeEmpty;
	}
	public void setUnlockPasscodeEmpty(String unlockPasscodeEmpty) {
		this.unlockPasscodeEmpty = unlockPasscodeEmpty;
	}
	public String getUnlockFailedMessage() {
		return unlockFailedMessage;
	}
	public void setUnlockFailedMessage(String unlockFailedMessage) {
		this.unlockFailedMessage = unlockFailedMessage;
	}
	public String getUnlockSuccessFulMessage() {
		return unlockSuccessFulMessage;
	}
	public void setUnlockSuccessFulMessage(String unlockSuccessFulMessage) {
		this.unlockSuccessFulMessage = unlockSuccessFulMessage;
	}
	public String getPasscodeMismatch() {
		return passcodeMismatch;
	}
	public void setPasscodeMismatch(String passcodeMismatch) {
		this.passcodeMismatch = passcodeMismatch;
	}
	public String getLockAppSuccessMessage() {
		return lockAppSuccessMessage;
	}
	public void setLockAppSuccessMessage(String lockAppSuccessMessage) {
		this.lockAppSuccessMessage = lockAppSuccessMessage;
	}
	public String getLockAppFailedMessage() {
		return lockAppFailedMessage;
	}
	public void setLockAppFailedMessage(String lockAppFailedMessage) {
		this.lockAppFailedMessage = lockAppFailedMessage;
	}
	public String getSaveCodeLockAppSuccessMessage1() {
		return saveCodeLockAppSuccessMessage1;
	}
	public void setSaveCodeLockAppSuccessMessage1(String saveCodeLockAppSuccessMessage1) {
		this.saveCodeLockAppSuccessMessage1 = saveCodeLockAppSuccessMessage1;
	}
	public String getSaveCodeLockAppSuccessMessage2() {
		return saveCodeLockAppSuccessMessage2;
	}
	public void setSaveCodeLockAppSuccessMessage2(String saveCodeLockAppSuccessMessage2) {
		this.saveCodeLockAppSuccessMessage2 = saveCodeLockAppSuccessMessage2;
	}
	public String getSaveCodeLockFailedMessage1() {
		return saveCodeLockFailedMessage1;
	}
	public void setSaveCodeLockFailedMessage1(String saveCodeLockFailedMessage1) {
		this.saveCodeLockFailedMessage1 = saveCodeLockFailedMessage1;
	}
	public String getSaveCodeLockFailedMessage2() {
		return saveCodeLockFailedMessage2;
	}
	public void setSaveCodeLockFailedMessage2(String saveCodeLockFailedMessage2) {
		this.saveCodeLockFailedMessage2 = saveCodeLockFailedMessage2;
	}
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
