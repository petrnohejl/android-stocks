package com.example.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;

import com.example.viewmodel.StockDetailViewModel;


public class StockDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
	private final Bundle mExtras;


	public StockDetailViewModelFactory(Bundle extras)
	{
		mExtras = extras;
	}


	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(Class<T> modelClass)
	{
		return (T) new StockDetailViewModel(mExtras);
	}
}
