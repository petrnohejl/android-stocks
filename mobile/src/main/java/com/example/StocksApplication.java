package com.example;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.alfonz.utility.Logcat;

public class StocksApplication extends Application {
	private static StocksApplication sInstance;

	private RefWatcher mRefWatcher;

	public StocksApplication() {
		sInstance = this;
	}

	public static Context getContext() {
		return sInstance;
	}

	public static RefWatcher getRefWatcher() {
		StocksApplication application = (StocksApplication) getContext();
		return application.mRefWatcher;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// this process is dedicated to leak canary for heap analysis
		if (LeakCanary.isInAnalyzerProcess(this)) return;

		// init leak canary
		mRefWatcher = LeakCanary.install(this);

		// init logcat
		Logcat.init(StocksConfig.LOGS, "STOCKS");
	}
}
