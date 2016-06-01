package com.example.rest;

import com.example.entity.ErrorEntity;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;


// inspired by: retrofit2.adapter.rxjava.HttpException
public final class RetrofitHttpException extends Exception
{
	private final int code;
	private final String message;
	private final transient Response<?> response;
	private final ErrorEntity error;


	public RetrofitHttpException(Response<?> response)
	{
		super("HTTP " + response.code() + " " + response.message());
		this.code = response.code();
		this.message = response.message();
		this.response = response;
		this.error = parseError(response, RetrofitClient.getRetrofit());
	}


	public int code()
	{
		return code;
	}


	public String message()
	{
		return message;
	}


	public Response<?> response()
	{
		return response;
	}


	public ErrorEntity error()
	{
		return error;
	}


	// this method can be called just once because it reads data from stream
	private ErrorEntity parseError(Response<?> response, Retrofit retrofit)
	{
		try
		{
			Converter<ResponseBody, ErrorEntity> converter = retrofit.responseBodyConverter(ErrorEntity.class, new Annotation[0]);
			return converter.convert(response.errorBody());
		}
		catch(IOException|NullPointerException e)
		{
			e.printStackTrace();
			ErrorEntity error = new ErrorEntity();
			error.setMessage("Unknown error");
			return error;
		}
	}
}
