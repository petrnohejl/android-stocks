package com.example.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.example.StocksApplication;
import com.example.entity.LookupEntity;
import com.example.rest.provider.StocksRxProvider;
import com.example.rest.rx.RestRxManager;
import com.example.rx.LoggedObserver;
import com.example.ui.StockPagerView;
import com.example.utility.NetworkUtility;
import com.example.utility.RxUtility;
import com.example.view.StatefulLayout;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;


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
		mRestRxManager.disposeAll();
	}


	public void loadData()
	{
		sendLookup("bank");
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
				Disposable disposable = observable.subscribeWith(createLookupObserver());
				mRestRxManager.registerDisposable(disposable);
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.State.OFFLINE);
		}
	}


	private DisposableObserver<Response<List<LookupEntity>>> createLookupObserver()
	{
		return LoggedObserver.newInstance(
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
