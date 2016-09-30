package com.example.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.example.StocksApplication;
import com.example.entity.LookupEntity;
import com.example.rest.provider.StocksRxProvider;
import com.example.rest.rx.RestRxManager;
import com.example.rx.LoggedSubscriber;
import com.example.ui.StockPagerView;
import com.example.utility.NetworkUtility;
import com.example.utility.RxUtility;
import com.example.view.StatefulLayout;

import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;


public class StockPagerViewModel extends BaseViewModel<StockPagerView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableArrayList<StockPagerItemViewModel> lookups = new ObservableArrayList<>();

	private RestRxManager mRestRxManager = new RestRxManager();


	@Override
	public void onStart()
	{
		super.onStart();

		// load data
		if(lookups.isEmpty()) loadData();
	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();

		// unsubscribe
		mRestRxManager.unsubscribeAll();
	}


	public void loadData()
	{
		sendLookup("oil");
	}


	private void sendLookup(String input)
	{
		if(NetworkUtility.isOnline(StocksApplication.getContext()))
		{
			if(!mRestRxManager.isRunning(StocksRxProvider.LOOKUP_CALL_TYPE))
			{
				// show progress
				state.set(StatefulLayout.State.PROGRESS);

				// subscribe
				Observable<Response<List<LookupEntity>>> rawObservable = StocksRxProvider.getService().lookup("json", input);
				Observable<Response<List<LookupEntity>>> observable = mRestRxManager.setupRestObservableWithSchedulers(rawObservable, StocksRxProvider.LOOKUP_CALL_TYPE);
				Subscription subscription = observable.subscribe(createLookupSubscriber());
				mRestRxManager.registerSubscription(subscription);
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.State.OFFLINE);
		}
	}


	private Subscriber<Response<List<LookupEntity>>> createLookupSubscriber()
	{
		return LoggedSubscriber.create(
				response ->
				{
					lookups.clear();
					for(LookupEntity e : response.body())
					{
						lookups.add(new StockPagerItemViewModel(e));
					}
				},
				throwable ->
				{
					handleError(RxUtility.getHttpErrorMessage(throwable));
					setState(lookups);
				},
				() ->
				{
					setState(lookups);
				}
		);
	}


	private void setState(ObservableArrayList<StockPagerItemViewModel> data)
	{
		if(!data.isEmpty())
		{
			state.set(StatefulLayout.State.CONTENT);
		}
		else
		{
			state.set(StatefulLayout.State.EMPTY);
		}
	}
}
