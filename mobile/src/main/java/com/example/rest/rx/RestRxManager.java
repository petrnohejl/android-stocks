package com.example.rest.rx;

import com.example.rest.RetrofitHttpException;
import com.example.rx.RxManager;
import com.example.utility.Logcat;
import com.example.utility.RestUtility;
import com.example.utility.RxUtility;

import retrofit2.Response;
import rx.Observable;


public class RestRxManager extends RxManager
{
	public <T extends Response<?>> Observable<T> setupRestObservable(Observable<T> restObservable, String callType)
	{
		return setupObservable(restObservable, callType)
				.flatMap(RxUtility::catchHttpError)
				.doOnNext(response -> logSuccess(response, callType))
				.doOnError(throwable ->
				{
					if(throwable instanceof RetrofitHttpException)
					{
						logError((RetrofitHttpException) throwable, callType);
					}
					else
					{
						logFail(throwable, callType);
					}
				});
	}


	public <T extends Response<?>> Observable<T> setupRestObservableWithSchedulers(Observable<T> restObservable, String callType)
	{
		return setupRestObservable(restObservable, callType).compose(RxUtility.applySchedulers());
	}


	private void logSuccess(Response<?> response, String callType)
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
