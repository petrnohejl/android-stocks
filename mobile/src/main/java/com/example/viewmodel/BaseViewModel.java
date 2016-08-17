package com.example.viewmodel;

import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ui.BaseView;
import com.example.utility.Logcat;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import eu.inloop.viewmodel.AbstractViewModel;


public abstract class BaseViewModel<T extends BaseView> extends AbstractViewModel<T> implements Observable
{
	private transient PropertyChangeRegistry mObservableCallbacks;
	private Queue<Runnable> mRunnableQueue;


	@Override
	public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState)
	{
		Logcat.v("");
		super.onCreate(arguments, savedInstanceState);
	}


	@Override
	public void onBindView(@NonNull T view)
	{
		Logcat.v("");
		super.onBindView(view);
		processPendingRunnables();
	}


	@Override
	public void onStart()
	{
		Logcat.v("");
		super.onStart();
	}


	@Override
	public void onSaveInstanceState(@NonNull Bundle bundle)
	{
		Logcat.v("");
		super.onSaveInstanceState(bundle);
	}


	@Override
	public void onStop()
	{
		Logcat.v("");
		super.onStop();
	}


	@Override
	public void onDestroy()
	{
		Logcat.v("");
		super.onDestroy();
	}


	@Override
	public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback)
	{
		if(mObservableCallbacks == null)
		{
			mObservableCallbacks = new PropertyChangeRegistry();
		}
		mObservableCallbacks.add(callback);
	}


	@Override
	public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback)
	{
		if(mObservableCallbacks != null)
		{
			mObservableCallbacks.remove(callback);
		}
	}


	public synchronized void notifyChange()
	{
		if(mObservableCallbacks != null)
		{
			mObservableCallbacks.notifyCallbacks(this, 0, null);
		}
	}


	public void notifyPropertyChanged(int fieldId)
	{
		if(mObservableCallbacks != null)
		{
			mObservableCallbacks.notifyCallbacks(this, fieldId, null);
		}
	}


	public void handleError(String message)
	{
		if(getView() != null)
		{
			getView().showToast(message);
		}
	}


	public void runCallback(Runnable runnable)
	{
		if(getView() != null)
		{
			runnable.run();
		}
		else
		{
			addPendingRunnable(runnable);
		}
	}


	private synchronized void addPendingRunnable(Runnable runnable)
	{
		if(mRunnableQueue == null)
		{
			mRunnableQueue = new ConcurrentLinkedQueue<>();
		}
		mRunnableQueue.add(runnable);
	}


	private void processPendingRunnables()
	{
		while(mRunnableQueue != null && !mRunnableQueue.isEmpty())
		{
			Runnable runnable = mRunnableQueue.remove();
			runnable.run();
		}
	}
}
