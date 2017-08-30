package com.example.architecture.event;


public class SnackbarEvent extends Event
{
	public final String message;


	public SnackbarEvent(String message)
	{
		this.message = message;
	}
}
