package com.example.architecture;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;


public class StockDetailArchViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
	private final Application mApplication;
	private final Bundle mExtras;


	public StockDetailArchViewModelFactory(Application application, Bundle extras)
	{
		mApplication = application;
		mExtras = extras;
	}


	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(Class<T> modelClass)
	{
		return (T) new StockDetailArchViewModel(mApplication, mExtras);
	}
}
