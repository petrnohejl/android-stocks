package com.example.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ui.BaseView;

import org.alfonz.mvvm.AlfonzViewModel;
import org.alfonz.utility.Logcat;


public abstract class BaseViewModel<T extends BaseView> extends AlfonzViewModel<T>
{
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


	public void handleError(String message)
	{
		if(getView() != null)
		{
			getView().showToast(message);
		}
	}
}
