package com.example.rest;

import android.net.ParseException;

import androidx.annotation.NonNull;

import com.example.R;
import com.example.StocksApplication;
import com.example.entity.ErrorEntity;
import com.example.entity.QuoteEntity;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.alfonz.rest.HttpException;
import org.alfonz.rest.ResponseHandler;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Response;

public class RestResponseHandler implements ResponseHandler {
	@Override
	public boolean isSuccess(@NonNull Response<?> response) {
//		return response.isSuccessful();

		// TODO: little hack because this REST API does not handle errors properly and always returns 200
		if (response.body() instanceof List) {
			return response.isSuccessful();
		} else if (response.body() instanceof QuoteEntity) {
			return response.isSuccessful() && ((ErrorEntity) response.body()).getMessage() == null;
		} else {
			return response.isSuccessful();
		}
	}

	@Override
	public String getErrorMessage(@NonNull HttpException exception) {
//		ErrorEntity error = exception.error();
//		return error.getMessage();

		// TODO: little hack because this REST API does not handle errors properly and always returns 200
		if (exception.response().body() instanceof List) {
			return ((ErrorEntity) exception.error()).getMessage();
		} else if (exception.response().body() instanceof QuoteEntity) {
			return ((ErrorEntity) exception.response().body()).getMessage();
		} else {
			return ((ErrorEntity) exception.error()).getMessage();
		}
	}

	@Override
	public String getFailMessage(@NonNull Throwable throwable) {
		int resId;

		if (throwable instanceof UnknownHostException)
			resId = R.string.global_network_unknown_host;
		else if (throwable instanceof FileNotFoundException)
			resId = R.string.global_network_not_found;
		else if (throwable instanceof SocketTimeoutException)
			resId = R.string.global_network_timeout;
		else if (throwable instanceof JsonParseException)
			resId = R.string.global_network_parse_fail;
		else if (throwable instanceof MalformedJsonException)
			resId = R.string.global_network_parse_fail;
		else if (throwable instanceof ParseException)
			resId = R.string.global_network_parse_fail;
		else if (throwable instanceof NumberFormatException)
			resId = R.string.global_network_parse_fail;
		else if (throwable instanceof ClassCastException)
			resId = R.string.global_network_parse_fail;
		else
			resId = R.string.global_network_fail;

		return StocksApplication.getContext().getString(resId);
	}

	@Override
	public HttpException createHttpException(@NonNull Response<?> response) {
		return new RestHttpException(response);
	}
}
