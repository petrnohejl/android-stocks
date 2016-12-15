package com.example.utility;

import com.example.rest.RetrofitHttpException;

import org.alfonz.utility.Logcat;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public final class RxUtility
{
	private static final ObservableTransformer<?, ?> sSchedulersTransformer =
			observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


	private RxUtility() {}


	@SuppressWarnings("unchecked")
	public static <T> ObservableTransformer<T, T> applySchedulers()
	{
		return (ObservableTransformer<T, T>) sSchedulersTransformer;
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
