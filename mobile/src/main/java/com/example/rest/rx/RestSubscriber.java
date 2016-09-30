package com.example.rest.rx;

import com.example.rest.RetrofitHttpException;
import com.example.utility.Logcat;
import com.example.utility.RestUtility;
import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import retrofit2.Response;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;


@Deprecated
@RxLogSubscriber
public class RestSubscriber<T extends Response<?>> extends Subscriber<T>
{
	private SubscriberManager mSubscriberManager;
	private String mCallType;
	private Action1<? super T> mOnNextAction;
	private Action1<Throwable> mOnErrorAction;
	private Action0 mOnCompletedAction;


	public RestSubscriber(SubscriberManager subscriberManager, String callType, Action1<? super T> onNextAction)
	{
		this(subscriberManager, callType, onNextAction, null, null);
	}


	public RestSubscriber(SubscriberManager subscriberManager, String callType, Action1<? super T> onNextAction, Action1<Throwable> onErrorAction)
	{
		this(subscriberManager, callType, onNextAction, onErrorAction, null);
	}


	public RestSubscriber(SubscriberManager subscriberManager, String callType, Action1<? super T> onNextAction, Action1<Throwable> onErrorAction, Action0 onCompletedAction)
	{
		mSubscriberManager = subscriberManager;
		mCallType = callType;
		mOnNextAction = onNextAction;
		mOnErrorAction = onErrorAction;
		mOnCompletedAction = onCompletedAction;
	}


	@Override
	public void onStart()
	{
		mSubscriberManager.registerSubscriber(this, mCallType);
	}


	@Override
	public void onNext(T response)
	{
		// log
		logSuccess(response, mSubscriberManager.getCallType(this));

		// callback
		if(mOnNextAction != null) mOnNextAction.call(response);
	}


	@Override
	public void onError(Throwable throwable)
	{
		// log
		if(throwable instanceof RetrofitHttpException)
		{
			logError((RetrofitHttpException) throwable, mSubscriberManager.getCallType(this));
		}
		else
		{
			logFail(throwable, mSubscriberManager.getCallType(this));
		}

		// callback
		if(mOnErrorAction != null) mOnErrorAction.call(throwable);

		// unregister subscriber
		mSubscriberManager.unregisterSubscriber(this);
	}


	@Override
	public void onCompleted()
	{
		// callback
		if(mOnCompletedAction != null) mOnCompletedAction.call();

		// unregister subscriber
		mSubscriberManager.unregisterSubscriber(this);
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
