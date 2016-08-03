package com.example.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.example.StocksApplication;
import com.example.entity.LookupEntity;
import com.example.rest.provider.StocksRxProvider;
import com.example.rest.rx.RestSubscriber;
import com.example.rest.rx.SubscriberManager;
import com.example.ui.StockPagerView;
import com.example.utility.NetworkUtility;
import com.example.utility.RxUtility;
import com.example.view.StatefulLayout;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import retrofit2.Response;
import rx.Observable;


public class StockPagerViewModel extends BaseViewModel<StockPagerView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableArrayList<StockPagerItemViewModel> lookups = new ObservableArrayList<>();

	private SubscriberManager mSubscriberManager = new SubscriberManager();


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
		if(mSubscriberManager != null) mSubscriberManager.unsubscribeAll();
	}


	public void loadData()
	{
		sendLookup("oil");
	}


	private void sendLookup(String input)
	{
		if(NetworkUtility.isOnline(StocksApplication.getContext()))
		{
			if(!mSubscriberManager.isRegistered(StocksRxProvider.LOOKUP_CALL_TYPE))
			{
				// show progress
				state.set(StatefulLayout.State.PROGRESS);

				// subscribe
				Observable<Response<List<LookupEntity>>> observable = createLookupObservable(input);
				observable.subscribe(createLookupSubscriber());
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.State.OFFLINE);
		}
	}


	@RxLogObservable
	private Observable<Response<List<LookupEntity>>> createLookupObservable(String input)
	{
		return StocksRxProvider.getService()
				.lookup("json", input)
				.flatMap(RxUtility::catchHttpError)
				.compose(RxUtility.applySchedulers());
	}


	private RestSubscriber<Response<List<LookupEntity>>> createLookupSubscriber()
	{
		return new RestSubscriber<>(mSubscriberManager, StocksRxProvider.LOOKUP_CALL_TYPE,
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
				});
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
