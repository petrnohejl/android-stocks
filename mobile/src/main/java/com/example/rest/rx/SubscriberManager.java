package com.example.rest.rx;

import com.example.utility.Logcat;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;


@Deprecated
public class SubscriberManager
{
	private Map<Subscriber, String> mSubscribers = new HashMap<>();


	public void registerSubscriber(Subscriber subscriber, String callType)
	{
		mSubscribers.put(subscriber, callType);
	}


	public void unregisterSubscriber(Subscriber subscriber)
	{
		mSubscribers.remove(subscriber);
	}


	public String getCallType(Subscriber subscriber)
	{
		return mSubscribers.get(subscriber);
	}


	public int getCount()
	{
		return mSubscribers.size();
	}


	public boolean isRegistered(String callType)
	{
		return mSubscribers.containsValue(callType);
	}


	public void unsubscribeAll()
	{
		for(Subscriber subscriber : mSubscribers.keySet())
		{
			if(subscriber != null)
			{
				subscriber.unsubscribe();
			}
		}
		mSubscribers.clear();
	}


	public void printAll()
	{
		for(String callType : mSubscribers.values())
		{
			Logcat.d(callType);
		}

		if(mSubscribers.isEmpty())
		{
			Logcat.d("empty");
		}
	}
}
