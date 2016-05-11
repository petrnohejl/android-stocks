package com.example.utility;

import android.net.ParseException;

import com.example.R;
import com.example.StocksApplication;
import com.example.entity.ErrorEntity;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;


public final class RestUtility
{
	private RestUtility() {}


	public static boolean isSuccess(Response<?> response)
	{
		// TODO
		return response.isSuccessful();
//		return response.isSuccessful() && ((ErrorEntity) response.body()).getMessage() == null;
	}


	public static String getErrorMessage(Response<?> response)
	{
		// TODO
//		ErrorEntity error = parseError(response, RetrofitClient.getRetrofit());
//		return error.getMessage();
		return ((ErrorEntity) response.body()).getMessage();
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


	public static ErrorEntity parseError(Response<?> response, Retrofit retrofit)
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
}
