package com.example.viewmodel.factory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.viewmodel.StockDetailViewModel;

public class StockDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
	private final Bundle mExtras;

	public StockDetailViewModelFactory(Bundle extras) {
		mExtras = extras;
	}

	@NonNull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new StockDetailViewModel(mExtras);
	}
}
