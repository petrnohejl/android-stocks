package com.example.rx;

import android.support.annotation.Nullable;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;


@RxLogSubscriber
public class LoggedSubscriber<T> extends Subscriber<T>
{
	@Nullable private Action1<T> mOnNextAction;
	@Nullable private Action1<Throwable> mOnErrorAction;
	@Nullable private Action0 mOnCompletedAction;


	private LoggedSubscriber(@Nullable Action1<T> onNextAction, @Nullable Action1<Throwable> onErrorAction, @Nullable Action0 onCompletedAction)
	{
		mOnNextAction = onNextAction;
		mOnErrorAction = onErrorAction;
		mOnCompletedAction = onCompletedAction;
	}


	public static <T> LoggedSubscriber<T> newInstance()
	{
		return newInstance(null, null, null);
	}


	public static <T> LoggedSubscriber<T> newInstance(Action1<T> onNextAction)
	{
		return newInstance(onNextAction, null, null);
	}


	public static <T> LoggedSubscriber<T> newInstance(Action1<T> onNextAction, Action1<Throwable> onErrorAction)
	{
		return newInstance(onNextAction, onErrorAction, null);
	}


	public static <T> LoggedSubscriber<T> newInstance(@Nullable Action1<T> onNextAction, @Nullable Action1<Throwable> onErrorAction, @Nullable Action0 onCompletedAction)
	{
		return new LoggedSubscriber<T>(onNextAction, onErrorAction, onCompletedAction);
	}


	@Override
	public void onNext(T t)
	{
		if(mOnNextAction == null) return;
		mOnNextAction.call(t);
	}


	@Override
	public void onError(Throwable e)
	{
		if(mOnErrorAction == null) return;
		mOnErrorAction.call(e);
	}


	@Override
	public void onCompleted()
	{
		if(mOnCompletedAction == null) return;
		mOnCompletedAction.call();
	}
}
