package com.example.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.viewmodel.StockDetailRxViewModel;


public class StockDetailRxViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
	private final Bundle mExtras;


	public StockDetailRxViewModelFactory(Bundle extras)
	{
		mExtras = extras;
	}


	@NonNull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
	{
		return (T) new StockDetailRxViewModel(mExtras);
	}
}
