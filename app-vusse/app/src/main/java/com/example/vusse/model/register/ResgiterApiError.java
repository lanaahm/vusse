package com.example.vusse.model.register;

import java.util.List;

public class ResgiterApiError {
	private String message;
	private Boolean success;
	private List<String> password, phone, name, email, confirm_password;

	public String getMessage() {
		return message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public List<String> getPassword(){
		return password;
	}


	public List<String> getPhone(){
		return phone;
	}

	public List<String> getName(){
		return name;
	}


	public List<String> getEmail(){
		return email;
	}


	public List<String> getConfirmPassword(){
		return confirm_password;
	}
}