package com.example.utility;

import com.example.rest.RetrofitHttpException;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public final class RxUtility
{
	private static final Observable.Transformer<?, ?> sSchedulersTransformer =
			observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


	private RxUtility() {}


	@SuppressWarnings("unchecked")
	public static <T> Observable.Transformer<T, T> applySchedulers()
	{
		return (Observable.Transformer<T, T>) sSchedulersTransformer;
	}


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
