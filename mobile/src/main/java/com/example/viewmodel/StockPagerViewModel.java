package com.example.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.example.entity.LookupEntity;
import com.example.rest.RestHttpLogger;
import com.example.rest.RestResponseHandler;
import com.example.rest.provider.StocksRxServiceProvider;
import com.example.ui.StockPagerView;

import org.alfonz.rest.rx.RestRxManager;
import org.alfonz.rx.AlfonzDisposableSingleObserver;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;


public class StockPagerViewModel extends BaseViewModel<StockPagerView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableArrayList<StockPagerItemViewModel> lookups = new ObservableArrayList<>();

	private RestRxManager mRestRxManager = new RestRxManager(new RestResponseHandler(), new RestHttpLogger());


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
			if(!mRestRxManager.isRunning(StocksRxServiceProvider.LOOKUP_CALL_TYPE))
			{
				// show progress
				state.set(StatefulLayout.State.PROGRESS);

				// subscribe
				Single<Response<List<LookupEntity>>> rawSingle = StocksRxServiceProvider.getService().lookup("json", input);
				Single<Response<List<LookupEntity>>> single = mRestRxManager.setupRestSingleWithSchedulers(rawSingle, StocksRxServiceProvider.LOOKUP_CALL_TYPE);
				single.subscribeWith(createLookupObserver());
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.State.OFFLINE);
		}
	}


	private DisposableSingleObserver<Response<List<LookupEntity>>> createLookupObserver()
	{
		return AlfonzDisposableSingleObserver.newInstance(
				response ->
				{
					lookups.clear();
					for(LookupEntity e : response.body())
					{
						lookups.add(new StockPagerItemViewModel(e));
					}
					setState(lookups);
				},
				throwable ->
				{
					handleError(mRestRxManager.getHttpErrorMessage(throwable));
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
