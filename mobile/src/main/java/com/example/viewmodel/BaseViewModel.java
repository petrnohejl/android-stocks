package com.example.viewmodel;

import android.content.Context;

import com.example.StocksApplication;
import com.example.event.ToastEvent;

import org.alfonz.arch.AlfonzViewModel;
import org.alfonz.utility.Logcat;

public abstract class BaseViewModel extends AlfonzViewModel {
	@Override
	public void onCleared() {
		Logcat.v("");
		super.onCleared();
	}

	public Context getApplicationContext() {
		return StocksApplication.getContext();
	}

	public void handleError(String message) {
		sendEvent(new ToastEvent(message));
	}
}
