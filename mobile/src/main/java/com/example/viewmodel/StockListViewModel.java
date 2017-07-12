package com.example.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.example.entity.LookupEntity;
import com.example.rest.RestHttpLogger;
import com.example.rest.RestResponseHandler;
import com.example.rest.provider.StocksRxServiceProvider;
import com.example.ui.StockListView;

import org.alfonz.rest.rx.RestRxManager;
import org.alfonz.rx.AlfonzDisposableSingleObserver;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;


public class StockListViewModel extends BaseViewModel<StockListView>
{
	public final ObservableField<Integer> state = new ObservableField<>();
	public final ObservableArrayList<String> headers = new ObservableArrayList<>();
	public final ObservableArrayList<LookupEntity> lookups = new ObservableArrayList<>();
	public final ObservableArrayList<Object> footers = new ObservableArrayList<>();

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


	public void refreshData()
	{
		sendLookup("bank");
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
					lookups.clear();
					lookups.addAll(response.body());

					headers.clear();
					headers.add("one");
					headers.add("two");
					headers.add("three");

					footers.clear();
					footers.add(new Object());

					setState(lookups);
				},
				throwable ->
				{
					handleError(mRestRxManager.getHttpErrorMessage(throwable));
					setState(lookups);
				}
		);
	}


	private void setState(ObservableArrayList<LookupEntity> data)
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
