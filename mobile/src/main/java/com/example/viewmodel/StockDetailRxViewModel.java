package com.example.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.StocksApplication;
import com.example.StocksConfig;
import com.example.activity.StockDetailActivity;
import com.example.entity.QuoteEntity;
import com.example.rest.provider.StocksRxProvider;
import com.example.rest.rx.RestRxManager;
import com.example.rx.LoggedSubscriber;
import com.example.ui.StockDetailView;
import com.example.utility.NetworkUtility;
import com.example.utility.RxUtility;
import com.example.view.StatefulLayout;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;


public class StockDetailRxViewModel extends BaseViewModel<StockDetailView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableField<QuoteEntity> quote = new ObservableField<>();

	private String mSymbol;
	private RestRxManager mRestRxManager = new RestRxManager();


	@Override
	public void onBindView(@NonNull StockDetailView view)
	{
		super.onBindView(view);

		// handle intent extras
		handleExtras(view.getExtras());
	}


	@Override
	public void onStart()
	{
		super.onStart();

		// load data
		if(quote.get() == null) loadData();
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
		sendQuote(mSymbol);
	}


	public void refreshData()
	{
		sendQuote(mSymbol);
	}


	public String getChartUrl()
	{
		return String.format(StocksConfig.CHART_BASE_URL, mSymbol);
	}


	private void sendQuote(String symbol)
	{
		if(NetworkUtility.isOnline(StocksApplication.getContext()))
		{
			if(!mRestRxManager.isRunning(StocksRxProvider.QUOTE_CALL_TYPE))
			{
				// show progress
				state.set(StatefulLayout.State.PROGRESS);

				// subscribe
				Observable<Response<QuoteEntity>> rawObservable = StocksRxProvider.getService().quote("json", symbol);
				Observable<Response<QuoteEntity>> observable = mRestRxManager.setupRestObservableWithSchedulers(rawObservable, StocksRxProvider.QUOTE_CALL_TYPE);
				Subscription subscription = observable.subscribe(createQuoteSubscriber());
				mRestRxManager.registerSubscription(subscription);
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.State.OFFLINE);
		}
	}


	private Subscriber<Response<QuoteEntity>> createQuoteSubscriber()
	{
		return LoggedSubscriber.newInstance(
				response ->
				{
					quote.set(response.body());
				},
				throwable ->
				{
					handleError(RxUtility.getHttpErrorMessage(throwable));
					setState(quote);
				},
				() ->
				{
					setState(quote);
				}
		);
	}


	private void setState(ObservableField<QuoteEntity> data)
	{
		if(data.get() != null)
		{
			state.set(StatefulLayout.State.CONTENT);
		}
		else
		{
			state.set(StatefulLayout.State.EMPTY);
		}
	}


	private void handleExtras(Bundle extras)
	{
		if(extras != null)
		{
			mSymbol = extras.getString(StockDetailActivity.EXTRA_SYMBOL);
		}
	}
}
