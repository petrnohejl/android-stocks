package com.example.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorEntity {
	@SerializedName("Message")
	@Expose
	private String message;

	public ErrorEntity() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
