package com.example.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.utility.Logcat;

import eu.inloop.viewmodel.AbstractViewModel;
import eu.inloop.viewmodel.IView;


public abstract class BaseViewModel<T extends IView> extends AbstractViewModel<T>
{
	@Override
	public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState)
	{
		Logcat.d("");
		super.onCreate(arguments, savedInstanceState);
	}


	@Override
	public void bindView(@NonNull T view)
	{
		Logcat.d("");
		super.bindView(view);
	}


	@Override
	public void onStart()
	{
		Logcat.d("");
		super.onStart();
	}


	@Override
	public void saveState(@NonNull Bundle bundle)
	{
		Logcat.d("");
		super.saveState(bundle);
	}


	@Override
	public void onStop()
	{
		Logcat.d("");
		super.onStop();
	}


	@Override
	public void onModelRemoved()
	{
		Logcat.d("");
		super.onModelRemoved();
	}
}
