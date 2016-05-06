package com.example.rest.call;

import com.example.utility.Logcat;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class CallManager
{
	private Map<Integer, Call> mCalls = new HashMap<>();
	private Map<Integer, String> mTypes = new HashMap<>();


	public <T> void enqueueCall(Call<T> call, Callback<T> callback, String callType)
	{
		mCalls.put(callback.hashCode(), call);
		mTypes.put(callback.hashCode(), callType);
		call.enqueue(callback);
	}


	public void finishCall(Callback callback)
	{
		mCalls.remove(callback.hashCode());
		mTypes.remove(callback.hashCode());
	}


	public <T> Call<T> getCall(Callback<T> callback)
	{
		return mCalls.get(callback.hashCode());
	}


	public String getCallType(Callback callback)
	{
		return mTypes.get(callback.hashCode());
	}


	public int getCallsCount()
	{
		return mCalls.size();
	}


	public boolean hasRunningCall(String callType)
	{
		return mTypes.containsValue(callType);
	}


	public void cancelRunningCalls()
	{
		for(Call call : mCalls.values())
		{
			if(call != null)
			{
				call.cancel();
			}
		}
		mCalls.clear();
		mTypes.clear();
	}


	public void printRunningCalls()
	{
		for(String callType : mTypes.values())
		{
			Logcat.d(callType);
		}

		if(mCalls.isEmpty())
		{
			Logcat.d("empty");
		}
	}
}
