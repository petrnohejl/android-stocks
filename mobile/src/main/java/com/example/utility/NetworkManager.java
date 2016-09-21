package com.example.utility;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableField;
import android.net.ConnectivityManager;

import com.example.StocksApplication;
import com.example.view.StatefulLayout;


// requires android.permission.ACCESS_NETWORK_STATE


@SuppressWarnings({"WeakerAccess", "unused"})
public class NetworkManager
{
	private static NetworkManager sInstance;
	@SuppressWarnings("WeakerAccess")
	public ObservableField<Boolean> connected = new ObservableField<>(false);
	@SuppressWarnings("FieldCanBeLocal")
	private BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();

			if(!action.equals(ConnectivityManager.CONNECTIVITY_ACTION))
				return;

			if(connected.get() == intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false))
				connected.set(!intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false));
		}
	};


	@SuppressWarnings("WeakerAccess")
	public NetworkManager()
	{
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		StocksApplication.getContext().registerReceiver(mReceiver, filter);
	}


	@SuppressWarnings("WeakerAccess")
	public static NetworkManager getInstance()
	{
		if(sInstance == null)
			sInstance = new NetworkManager();
		return sInstance;
	}


	@SuppressWarnings("unused")
	public static void executeWithOfflineStateHandle(ObservableField<StatefulLayout.State> state, Runnable whenOnline)
	{
		executeWithOfflineStateHandle(state, whenOnline, whenOnline);
	}


	@SuppressWarnings("unused")
	public static void executeWithOfflineStateHandle(ObservableField<StatefulLayout.State> state, Runnable whenOnline, Runnable whenBackOnline)
	{
		if(NetworkManager.getInstance().connected.get())
		{
			whenOnline.run();
		}
		else
		{
			// show offline
			state.set(StatefulLayout.State.OFFLINE);
			NetworkManager.getInstance().connected.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback()
			{
				@Override
				public void onPropertyChanged(android.databinding.Observable observable, int i)
				{
					whenBackOnline.run();
					NetworkManager.getInstance().connected.removeOnPropertyChangedCallback(this);
				}
			});
		}
	}


	@SuppressWarnings("unused")
	public static void executeWhenOnline(Runnable whenOnline)
	{
		if(NetworkManager.getInstance().connected.get())
		{
			whenOnline.run();
		}
		else
		{
			NetworkManager.getInstance().connected.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback()
			{
				@Override
				public void onPropertyChanged(android.databinding.Observable observable, int i)
				{
					whenOnline.run();
					NetworkManager.getInstance().connected.removeOnPropertyChangedCallback(this);
				}
			});
		}
	}
}
