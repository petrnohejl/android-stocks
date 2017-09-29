package com.example.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.example.entity.LookupEntity;
import com.example.rest.RestHttpLogger;
import com.example.rest.RestResponseHandler;
import com.example.rest.provider.StocksRxServiceProvider;

import org.alfonz.rest.rx.RestRxManager;
import org.alfonz.rx.AlfonzDisposableSingleObserver;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;


public class StockPagerViewModel extends BaseViewModel implements LifecycleObserver
{
	public final ObservableField<Integer> state = new ObservableField<>();
	public final ObservableList<StockPagerItemViewModel> lookups = new ObservableArrayList<>();

	private RestRxManager mRestRxManager = new RestRxManager(new RestResponseHandler(), new RestHttpLogger());


	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart()
	{
		// load data
		if(lookups.isEmpty()) loadData();
	}


	@Override
	public void onCleared()
	{
		super.onCleared();

		// unsubscribe
		mRestRxManager.disposeAll();
	}


	public void loadData()
	{
		sendLookup("bank");
	}


	private void sendLookup(String input)
	{
		if(NetworkUtility.isOnline(getApplicationContext()))
		{
			String callType = StocksRxServiceProvider.LOOKUP_CALL_TYPE;
			if(!mRestRxManager.isRunning(callType))
			{
				// show progress
				state.set(StatefulLayout.PROGRESS);

				// subscribe
				Single<Response<List<LookupEntity>>> rawSingle = StocksRxServiceProvider.getService().lookup("json", input);
				Single<Response<List<LookupEntity>>> single = mRestRxManager.setupRestSingleWithSchedulers(rawSingle, callType);
				single.subscribeWith(createLookupObserver());
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.OFFLINE);
		}
	}


	private DisposableSingleObserver<Response<List<LookupEntity>>> createLookupObserver()
	{
		return AlfonzDisposableSingleObserver.newInstance(
				response ->
				{
					List<StockPagerItemViewModel> list = new ArrayList<>();
					for(LookupEntity e : response.body())
					{
						list.add(new StockPagerItemViewModel(e));
					}

					lookups.clear();
					lookups.addAll(list);
					setState(lookups);
				},
				throwable ->
				{
					handleError(mRestRxManager.getHttpErrorMessage(throwable));
					setState(lookups);
				}
		);
	}


	private void setState(ObservableList<StockPagerItemViewModel> data)
	{
		if(!data.isEmpty())
		{
			state.set(StatefulLayout.CONTENT);
		}
		else
		{
			state.set(StatefulLayout.EMPTY);
		}
	}
}
