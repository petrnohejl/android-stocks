package com.example.rx;

import android.support.annotation.Nullable;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;


@RxLogSubscriber
public abstract class LoggedSubscriber<T> extends Subscriber<T>
{
	public static <T> LoggedSubscriber<T> create()
	{
		return create(null, null, null);
	}


	public static <T> LoggedSubscriber<T> create(Action1<T> onNextAction)
	{
		return create(onNextAction, null, null);
	}


	public static <T> LoggedSubscriber<T> create(Action1<T> onNextAction, Action1<Throwable> onErrorAction)
	{
		return create(onNextAction, onErrorAction, null);
	}


	public static <T> LoggedSubscriber<T> create(@Nullable Action1<T> onNextAction, @Nullable Action1<Throwable> onErrorAction, @Nullable Action0 onCompletedAction)
	{
		return new LoggedSubscriber<T>()
		{
			@Override
			public void onNext(T t)
			{
				if(onNextAction == null) return;
				onNextAction.call(t);
			}


			@Override
			public void onError(Throwable e)
			{
				if(onErrorAction == null) return;
				onErrorAction.call(e);
			}


			@Override
			public void onCompleted()
			{
				if(onCompletedAction == null) return;
				onCompletedAction.call();
			}
		};
	}
}
