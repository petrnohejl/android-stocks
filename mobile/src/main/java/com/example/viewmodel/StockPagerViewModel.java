package com.example.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.example.entity.LookupEntity;
import com.example.rest.provider.StocksRxProvider;
import com.example.ui.StockPagerView;
import com.example.utility.NetworkManager;
import com.example.utility.RxUtility;
import com.example.utility.SubscriberManager;
import com.example.view.StatefulLayout;

import java.util.List;

import retrofit2.Response;
import rx.Observable;


public class StockPagerViewModel extends BaseViewModel<StockPagerView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableArrayList<StockPagerItemViewModel> lookups = new ObservableArrayList<>();


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
								() -> setState(lookups));
			}
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
