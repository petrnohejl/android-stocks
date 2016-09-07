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
	private Queue<ViewCallback<T>> mViewCallbackQueue;


	public interface ViewCallback<T>
	{
		void run(@NonNull T view);
	}


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
		processPendingViewCallbacks(view);
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


	public void runViewCallback(ViewCallback<T> viewCallback)
	{
		if(getView() != null)
		{
			viewCallback.run(getView());
		}
		else
		{
			addPendingViewCallback(viewCallback);
		}
	}


	private synchronized void addPendingViewCallback(ViewCallback<T> viewCallback)
	{
		if(mViewCallbackQueue == null)
		{
			mViewCallbackQueue = new ConcurrentLinkedQueue<>();
		}
		mViewCallbackQueue.add(viewCallback);
	}


	private void processPendingViewCallbacks(@NonNull T view)
	{
		while(mViewCallbackQueue != null && !mViewCallbackQueue.isEmpty())
		{
			ViewCallback<T> viewCallback = mViewCallbackQueue.remove();
			viewCallback.run(view);
		}
	}
}
