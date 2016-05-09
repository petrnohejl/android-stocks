package com.example.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ui.BaseView;
import com.example.utility.Logcat;

import eu.inloop.viewmodel.AbstractViewModel;


public abstract class BaseViewModel<T extends BaseView> extends AbstractViewModel<T>
{
	@Override
	public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState)
	{
		Logcat.d("");
		super.onCreate(arguments, savedInstanceState);
	}


	@Override
	public void onBindView(@NonNull T view)
	{
		Logcat.d("");
		super.onBindView(view);
	}


	@Override
	public void onStart()
	{
		Logcat.d("");
		super.onStart();
	}


	@Override
	public void onSaveInstanceState(@NonNull Bundle bundle)
	{
		Logcat.d("");
		super.onSaveInstanceState(bundle);
	}


	@Override
	public void onStop()
	{
		Logcat.d("");
		super.onStop();
	}


	@Override
	public void onDestroy()
	{
		Logcat.d("");
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
