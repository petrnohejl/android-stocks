package com.example.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.example.entity.LookupEntity;
import com.example.rest.provider.StocksRxProvider;
import com.example.ui.StockListView;
import com.example.utility.NetworkManager;
import com.example.utility.RxUtility;
import com.example.utility.SubscriberManager;
import com.example.view.StatefulLayout;

import java.util.List;

import retrofit2.Response;
import rx.Observable;


public class StockListViewModel extends BaseViewModel<StockListView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableArrayList<String> headers = new ObservableArrayList<>();
	public final ObservableArrayList<LookupEntity> lookups = new ObservableArrayList<>();
	public final ObservableArrayList<Object> footers = new ObservableArrayList<>();


	@Override
	public void onStart()
	{
		super.onStart();

		// load data
		if(lookups.isEmpty()) loadData();
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


	public void updateItem(LookupEntity lookup)
	{
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
		NetworkManager.executeWithOfflineStateHandle(state, () ->
		{
			if(!SubscriberManager.isCallRegistered(this.getClass(), StocksRxProvider.LOOKUP_CALL_TYPE))
			{
				state.set(StatefulLayout.State.PROGRESS);

				// subscribe
				Observable<Response<List<LookupEntity>>> restCall = StocksRxProvider.getService().lookup("json", input);
				SubscriberManager.createSubscribedObservable(restCall, StocksRxProvider.QUOTE_CALL_TYPE, this.getClass())
						.subscribe(response ->
								{
									lookups.clear();
									lookups.addAll(response.body());

									headers.clear();
									headers.add("one");
									headers.add("two");
									headers.add("three");

									footers.clear();
									footers.add(new Object());
								},
								throwable ->
								{
									handleError(RxUtility.getHttpErrorMessage(throwable));
									setState(lookups);
								},
								() -> setState(lookups));
			}
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
