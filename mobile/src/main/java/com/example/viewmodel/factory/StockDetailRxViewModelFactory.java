package com.example.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;

import com.example.viewmodel.StockDetailRxViewModel;


public class StockDetailRxViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
	private final Bundle mExtras;


	public StockDetailRxViewModelFactory(Bundle extras)
	{
		mExtras = extras;
	}


	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(Class<T> modelClass)
	{
		return (T) new StockDetailRxViewModel(mExtras);
	}
}
