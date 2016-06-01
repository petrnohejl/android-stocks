package com.example.utility;

import android.net.ParseException;

import com.example.R;
import com.example.StocksApplication;
import com.example.entity.ErrorEntity;
import com.example.entity.QuoteEntity;
import com.example.rest.RetrofitHttpException;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Response;


public final class RestUtility
{
	private RestUtility() {}


	public static boolean isSuccess(Response<?> response)
	{
//		return response.isSuccessful();

		// TODO: little hack because this REST API does not handle errors properly and always returns 200
		if(response.body() instanceof List)
		{
			return response.isSuccessful();
		}
		else if(response.body() instanceof QuoteEntity)
		{
			return response.isSuccessful() && ((ErrorEntity) response.body()).getMessage() == null;
		}
		else
		{
			return response.isSuccessful();
		}
	}


	public static String getErrorMessage(RetrofitHttpException exception)
	{
//		ErrorEntity error = exception.error();
//		return error.getMessage();

		// TODO: little hack because this REST API does not handle errors properly and always returns 200
		if(exception.response().body() instanceof List)
		{
			return exception.error().getMessage();
		}
		else if(exception.response().body() instanceof QuoteEntity)
		{
			return ((ErrorEntity) exception.response().body()).getMessage();
		}
		else
		{
			return exception.error().getMessage();
		}
	}


	public static String getFailMessage(Throwable throwable)
	{
		int resId;
		if(throwable instanceof UnknownHostException)
			resId = R.string.global_network_unknown_host;
		else if(throwable instanceof FileNotFoundException)
			resId = R.string.global_network_not_found;
		else if(throwable instanceof SocketTimeoutException)
			resId = R.string.global_network_timeout;
		else if(throwable instanceof JsonParseException)
			resId = R.string.global_network_parse_fail;
		else if(throwable instanceof MalformedJsonException)
			resId = R.string.global_network_parse_fail;
		else if(throwable instanceof ParseException)
			resId = R.string.global_network_parse_fail;
		else if(throwable instanceof NumberFormatException)
			resId = R.string.global_network_parse_fail;
		else if(throwable instanceof ClassCastException)
			resId = R.string.global_network_parse_fail;
		else
			resId = R.string.global_network_fail;
		return StocksApplication.getContext().getString(resId);
	}
}
