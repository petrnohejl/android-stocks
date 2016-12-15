package com.example.rest.call;

import com.example.rest.RetrofitHttpException;
import com.example.utility.RestUtility;

import org.alfonz.utility.Logcat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseCallback<T> implements Callback<T>
{
	private CallManager mCallManager;


	public BaseCallback(CallManager callManager)
	{
		mCallManager = callManager;
	}


	public abstract void onSuccess(Call<T> call, Response<T> response);


	public abstract void onError(Call<T> call, RetrofitHttpException exception);


	public abstract void onFail(Call<T> call, Throwable throwable);


	@Override
	public void onResponse(Call<T> call, Response<T> response)
	{
		if(RestUtility.isSuccess(response))
		{
			// log
			logSuccess(response, mCallManager.getCallType(this));

			// callback
			onSuccess(call, response);
		}
		else
		{
			// exception
			RetrofitHttpException exception = new RetrofitHttpException(response);

			// log
			logError(exception, mCallManager.getCallType(this));

			// callback
			onError(call, exception);
		}

		// finish call
		mCallManager.finishCall(this);
	}


	@Override
	public void onFailure(Call<T> call, Throwable throwable)
	{
		// log
		logFail(throwable, mCallManager.getCallType(this));

		// callback
		onFail(call, throwable);

		// finish call
		mCallManager.finishCall(this);
	}


	private void logSuccess(Response<T> response, String callType)
	{
		String status = response.code() + " " + response.message();
		String result = response.body() != null ? response.body().getClass().getSimpleName() : "empty body";
		Logcat.d("%s call succeed with %s: %s", callType, status, result);
	}


	private void logError(RetrofitHttpException exception, String callType)
	{
		String status = exception.code() + " " + exception.message();
		String result = RestUtility.getErrorMessage(exception);
		Logcat.d("%s call err with %s: %s", callType, status, result);
	}


	private void logFail(Throwable throwable, String callType)
	{
		String exceptionName = throwable.getClass().getSimpleName();
		String exceptionMessage = throwable.getMessage();
		Logcat.d("%s call fail with %s exception: %s", callType, exceptionName, exceptionMessage);
	}
}
