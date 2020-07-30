package com.example;

import android.app.Application;
import android.content.Context;

import org.alfonz.utility.Logcat;

public class StocksApplication extends Application {
	private static StocksApplication sInstance;

	public StocksApplication() {
		sInstance = this;
	}

	public static Context getContext() {
		return sInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// init logcat
		Logcat.init(StocksConfig.LOGS, "STOCKS");
	}
}
