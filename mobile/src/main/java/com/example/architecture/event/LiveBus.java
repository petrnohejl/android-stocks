package com.example.architecture.event;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import java.util.HashMap;
import java.util.Map;


public class LiveBus
{
	private final Map<Class<? extends Event>, LiveEvent<? extends Event>> mEventMap;


	public LiveBus()
	{
		mEventMap = new HashMap<>();
	}


	@SuppressWarnings("unchecked")
	public <T extends Event> void observe(LifecycleOwner lifecycleOwner, Class<T> eventClass, Observer<T> observer)
	{
		LiveEvent<T> liveEvent = (LiveEvent<T>) mEventMap.get(eventClass);
		if(liveEvent == null)
		{
			liveEvent = initLiveEvent(eventClass);
		}
		liveEvent.observe(lifecycleOwner, observer);
	}


	@SuppressWarnings("unchecked")
	public <T extends Event> void send(T event)
	{
		LiveEvent<T> liveEvent = (LiveEvent<T>) mEventMap.get(event.getClass());
		if(liveEvent == null)
		{
			liveEvent = initLiveEvent((Class<T>) event.getClass());
		}
		liveEvent.setValue(event);
	}


	private <T extends Event> LiveEvent<T> initLiveEvent(Class<T> eventClass)
	{
		LiveEvent<T> liveEvent = new LiveEvent<>();
		mEventMap.put(eventClass, liveEvent);
		return liveEvent;
	}
}
