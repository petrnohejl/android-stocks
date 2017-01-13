package com.example;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.alfonz.utility.Logcat;


public class StocksApplication extends Application
{
	private static StocksApplication sInstance;

	private RefWatcher mRefWatcher;


	public StocksApplication()
	{
		sInstance = this;
	}


	public static Context getContext()
	{
		return sInstance;
	}


	public static RefWatcher getRefWatcher()
	{
		StocksApplication application = (StocksApplication) getContext();
		return application.mRefWatcher;
	}


	@Override
	public void onCreate()
	{
		super.onCreate();

		// force AsyncTask to be initialized in the main thread due to the bug:
		// http://stackoverflow.com/questions/4280330/onpostexecute-not-being-called-in-asynctask-handler-runtime-exception
		try
		{
			Class.forName("android.os.AsyncTask");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		// init logcat
		Logcat.init(StocksConfig.LOGS, "STOCKS");

		// init leak canary
		mRefWatcher = LeakCanary.install(this);
	}
}
