package com.example.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.StocksConfig;
import com.example.activity.StockDetailActivity;
import com.example.entity.QuoteEntity;
import com.example.rest.RestHttpLogger;
import com.example.rest.RestResponseHandler;
import com.example.rest.provider.StocksRxServiceProvider;
import com.example.ui.StockDetailView;

import org.alfonz.rest.rx.RestRxManager;
import org.alfonz.rx.AlfonzDisposableSingleObserver;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;


public class StockDetailRxViewModel extends BaseViewModel<StockDetailView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableField<QuoteEntity> quote = new ObservableField<>();

	private String mSymbol;
	private RestRxManager mRestRxManager = new RestRxManager(new RestResponseHandler(), new RestHttpLogger());


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
		mRestRxManager.disposeAll();
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
		if(NetworkUtility.isOnline(getApplicationContext()))
		{
			if(!mRestRxManager.isRunning(StocksRxServiceProvider.QUOTE_CALL_TYPE))
			{
				// show progress
				state.set(StatefulLayout.State.PROGRESS);

				// subscribe
				Single<Response<QuoteEntity>> rawSingle = StocksRxServiceProvider.getService().quote("json", symbol);
				Single<Response<QuoteEntity>> single = mRestRxManager.setupRestSingleWithSchedulers(rawSingle, StocksRxServiceProvider.QUOTE_CALL_TYPE);
				single.subscribeWith(createQuoteObserver());
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.State.OFFLINE);
		}
	}


	private DisposableSingleObserver<Response<QuoteEntity>> createQuoteObserver()
	{
		return AlfonzDisposableSingleObserver.newInstance(
				response ->
				{
					quote.set(response.body());
					setState(quote);
				},
				throwable ->
				{
					handleError(mRestRxManager.getHttpErrorMessage(throwable));
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
