package com.example.rx;

import com.example.utility.Logcat;
import com.example.utility.RxUtility;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class RxManager
{
	private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
	private Map<String, Short> mRunningCalls = new HashMap<>();


	public void registerSubscription(Subscription subscription)
	{
		mCompositeSubscription.add(subscription);
	}


	public void unregisterSubscription(Subscription subscription)
	{
		mCompositeSubscription.remove(subscription);
	}


	public void unsubscribeAll()
	{
		mCompositeSubscription.clear();
		mRunningCalls.clear();
	}


	public boolean isRunning(String callType)
	{
		return mRunningCalls.containsKey(callType);
	}


	public void printAll()
	{
		if(mRunningCalls.isEmpty())
		{
			Logcat.d("empty");
			return;
		}

		for(Map.Entry<String, Short> entry : mRunningCalls.entrySet())
		{
			Logcat.d(entry.getKey() + ": " + entry.getValue());
		}
	}


	@RxLogObservable
	public <T> Observable<T> setupObservable(Observable<T> observable, String callType)
	{
		return observable
				.doOnSubscribe(() -> addRunningCall(callType))
				.doAfterTerminate(() -> removeRunningCall(callType));
	}


	@RxLogObservable
	public <T> Observable<T> setupObservableWithSchedulers(Observable<T> observable, String callType)
	{
		return setupObservable(observable, callType).compose(RxUtility.applySchedulers());
	}


	private synchronized void addRunningCall(String callType)
	{
		short count = 0;
		if(mRunningCalls.containsKey(callType))
		{
			count = mRunningCalls.get(callType);
		}
		mRunningCalls.put(callType, ++count);
	}


	private synchronized void removeRunningCall(String callType)
	{
		Short count = mRunningCalls.get(callType);
		if(count == null) return;

		if(count > 1)
		{
			mRunningCalls.put(callType, --count);
		}
		else
		{
			mRunningCalls.remove(callType);
		}
	}
}
