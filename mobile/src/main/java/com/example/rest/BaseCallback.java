package com.example.rest;

import android.net.ParseException;
import android.support.annotation.StringRes;

import com.example.R;
import com.example.entity.ErrorEntity;
import com.example.utility.Logcat;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;


public abstract class BaseCallback<T> implements Callback<T>
{
	private CallManager mCallManager;

	public abstract void onSuccess(Call<T> call, Response<T> response);
	public abstract void onError(Call<T> call, Response<T> response);
	public abstract void onFail(Call<T> call, Throwable throwable);


	public BaseCallback(CallManager callManager)
	{
		mCallManager = callManager;
	}


	@Override
	public void onResponse(Call<T> call, Response<T> response)
	{
		if(isSuccess(response))
		{
			// log
			logSuccess(response, mCallManager.getCallType(this));

			// callback
			onSuccess(call, response);
		}
		else
		{
			// log
			logError(response, mCallManager.getCallType(this));

			// callback
			onError(call, response);
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


	public boolean isSuccess(Response<T> response)
	{
//		return response.isSuccessful();
		return response.isSuccessful() && ((ErrorEntity) response.body()).getMessage() == null;
	}


	public String getErrorMessage(Response<T> response)
	{
//		ErrorEntity error = parseError(response, RetrofitClient.getRetrofit());
//		return error.getMessage();
		return ((ErrorEntity) response.body()).getMessage();
	}


	@StringRes
	public int getFailMessage(Throwable throwable)
	{
		if(throwable instanceof UnknownHostException)
			return R.string.global_network_unknown_host;
		else if(throwable instanceof FileNotFoundException)
			return R.string.global_network_not_found;
		else if(throwable instanceof SocketTimeoutException)
			return R.string.global_network_timeout;
		else if(throwable instanceof JsonParseException)
			return R.string.global_network_parse_fail;
		else if(throwable instanceof MalformedJsonException)
			return R.string.global_network_parse_fail;
		else if(throwable instanceof ParseException)
			return R.string.global_network_parse_fail;
		else if(throwable instanceof NumberFormatException)
			return R.string.global_network_parse_fail;
		else if(throwable instanceof ClassCastException)
			return R.string.global_network_parse_fail;
		else
			return R.string.global_network_fail;
	}


	public ErrorEntity parseError(Response<T> response, Retrofit retrofit)
	{
		Converter<ResponseBody, ErrorEntity> converter = retrofit.responseBodyConverter(ErrorEntity.class, new Annotation[0]);
		ErrorEntity error;

		try
		{
			error = converter.convert(response.errorBody());
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return new ErrorEntity();
		}

		return error;
	}


	private void logSuccess(Response<T> response, String callType)
	{
		String status = response.code() + " " + response.message();
		String result = response.body().getClass().getSimpleName();
		Logcat.d("%s call succeed with %s: %s", callType, status, result);
	}


	private void logError(Response<T> response, String callType)
	{
		String status = response.code() + " " + response.message();
		String result = getErrorMessage(response);
		Logcat.d("%s call err with %s: %s", callType, status, result);
	}


	private void logFail(Throwable throwable, String callType)
	{
		String exceptionName = throwable.getClass().getSimpleName();
		String exceptionMessage = throwable.getMessage();
		Logcat.d("%s call fail with %s exception: %s", callType, exceptionName, exceptionMessage);
	}
}
