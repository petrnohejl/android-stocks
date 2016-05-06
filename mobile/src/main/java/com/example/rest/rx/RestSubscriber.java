package com.example.rest.rx;

import com.example.utility.Logcat;
import com.example.utility.RestUtility;
import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;


@RxLogSubscriber
public class RestSubscriber<T extends Response<?>> extends Subscriber<T>
{
	private SubscriberManager mSubscriberManager;
	private String mCallType;
	private Action1<? super T> mOnNextAction;
	private Action1<Throwable> mOnErrorAction;
	private Action0 mOnCompletedAction;


	public RestSubscriber(SubscriberManager subscriberManager, String callType, Action1<? super T> onNextAction, Action1<Throwable> onErrorAction, Action0 onCompletedAction)
	{
		checkArguments(onNextAction, onErrorAction, onCompletedAction);
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
		mOnNextAction.call(response);
	}


	@Override
	public void onError(Throwable throwable)
	{
		// log
		if(throwable instanceof HttpException)
		{
			logError((HttpException) throwable, mSubscriberManager.getCallType(this));
		}
		else
		{
			logFail(throwable, mSubscriberManager.getCallType(this));
		}

		// callback
		mOnErrorAction.call(throwable);

		// unregister subscriber
		mSubscriberManager.unregisterSubscriber(this);
	}


	@Override
	public void onCompleted()
	{
		// callback
		mOnCompletedAction.call();

		// unregister subscriber
		mSubscriberManager.unregisterSubscriber(this);
	}


	private void checkArguments(Action1<? super T> onNextAction, Action1<Throwable> onErrorAction, Action0 onCompletedAction)
	{
		if(onNextAction == null)
		{
			throw new IllegalArgumentException("onNextAction cannot be null");
		}
		else if(onErrorAction == null)
		{
			throw new IllegalArgumentException("onErrorAction cannot be null");
		}
		else if(onCompletedAction == null)
		{
			throw new IllegalArgumentException("onCompletedAction cannot be null");
		}
	}


	private void logSuccess(Response<?> response, String callType)
	{
		String status = response.code() + " " + response.message();
		String result = response.body().getClass().getSimpleName();
		Logcat.d("%s call succeed with %s: %s", callType, status, result);
	}


	private void logError(HttpException exception, String callType)
	{
		String status = exception.code() + " " + exception.message();
		String result = RestUtility.getErrorMessage(exception.response());
		Logcat.d("%s call err with %s: %s", callType, status, result);
	}


	private void logFail(Throwable throwable, String callType)
	{
		String exceptionName = throwable.getClass().getSimpleName();
		String exceptionMessage = throwable.getMessage();
		Logcat.d("%s call fail with %s exception: %s", callType, exceptionName, exceptionMessage);
	}
}
