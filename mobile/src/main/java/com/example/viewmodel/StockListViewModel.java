package com.example.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.example.StocksApplication;
import com.example.entity.LookupEntity;
import com.example.rest.provider.StocksRxProvider;
import com.example.rest.rx.RestSubscriber;
import com.example.rest.rx.SubscriberManager;
import com.example.ui.StockListView;
import com.example.utility.NetworkUtility;
import com.example.utility.RxUtility;
import com.example.view.StatefulLayout;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import retrofit2.Response;
import rx.Observable;


public class StockListViewModel extends BaseViewModel<StockListView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableArrayList<String> headers = new ObservableArrayList<>();
	public final ObservableArrayList<LookupEntity> lookups = new ObservableArrayList<>();
	public final ObservableArrayList<Object> footers = new ObservableArrayList<>();

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


	public void refreshData()
	{
		sendLookup("oil");
	}


	public void addItem()
	{
		LookupEntity lookup = new LookupEntity();
		lookup.setSymbol("YOYO");
		lookup.setName("Yoyo " + System.currentTimeMillis());
		lookup.setExchange("NYSE");
		lookups.add(0, lookup);
	}


	public void updateItem(int lookupPosition)
	{
		LookupEntity lookup = lookups.get(lookupPosition);
		lookup.setSymbol("YOYO");
		lookup.setName("Yoyo " + System.currentTimeMillis());
		lookup.setExchange("NYSE");
	}


	public void removeItem()
	{
		lookups.remove(0);
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
					lookups.addAll(response.body());

					headers.clear();
					headers.add("one");
					headers.add("two");
					headers.add("three");
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


	private void setState(ObservableArrayList<LookupEntity> data)
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
