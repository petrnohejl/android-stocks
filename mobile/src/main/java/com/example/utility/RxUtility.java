package com.example.utility;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public final class RxUtility
{
	private RxUtility() {}


	public static <T> Observable.Transformer<T, T> applySchedulers()
	{
		return observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}


	public static <T extends Response<?>> Observable<T> catchHttpError(T response)
	{
		if(RestUtility.isSuccess(response))
		{
			return Observable.just(response);
		}
		else
		{
			return Observable.error(new HttpException(response));
		}
	}


	public static String getHttpErrorMessage(Throwable throwable)
	{
		if(throwable instanceof HttpException)
		{
			return RestUtility.getErrorMessage(((HttpException) throwable).response());
		}
		else
		{
			return RestUtility.getFailMessage(throwable);
		}
	}
}
