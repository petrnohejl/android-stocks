package com.example.utility;

import com.example.rest.RetrofitHttpException;

import org.alfonz.utility.Logcat;

import io.reactivex.Observable;
import retrofit2.Response;


public final class RxUtility
{
	private RxUtility() {}


	public static <T extends Response<?>> Observable<T> catchHttpError(T response)
	{
		if(RestUtility.isSuccess(response))
		{
			return Observable.just(response);
		}
		else
		{
			return Observable.error(new RetrofitHttpException(response));
		}
	}


	public static String getHttpErrorMessage(Throwable throwable)
	{
		if(throwable instanceof RetrofitHttpException)
		{
			return RestUtility.getErrorMessage((RetrofitHttpException) throwable);
		}
		else
		{
			Logcat.printStackTrace(throwable);
			return RestUtility.getFailMessage(throwable);
		}
	}
}
