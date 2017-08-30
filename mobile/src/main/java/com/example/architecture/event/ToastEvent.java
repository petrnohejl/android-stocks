package com.example.architecture.event;


public class ToastEvent extends Event
{
	public final String message;


	public ToastEvent(String message)
	{
		this.message = message;
	}
}
