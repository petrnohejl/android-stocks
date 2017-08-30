package com.example.architecture;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.Bundle;

import com.example.StocksConfig;
import com.example.activity.StockDetailActivity;
import com.example.architecture.event.LiveBus;
import com.example.architecture.event.ToastEvent;
import com.example.entity.QuoteEntity;
import com.example.rest.RestHttpLogger;
import com.example.rest.RestResponseHandler;
import com.example.rest.provider.StocksRxServiceProvider;

import org.alfonz.rest.rx.RestRxManager;
import org.alfonz.rx.AlfonzDisposableSingleObserver;
import org.alfonz.utility.Logcat;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;


public class StockDetailArchViewModel extends AndroidViewModel implements LifecycleObserver
{
	public final ObservableField<Integer> state = new ObservableField<>();
	public final ObservableField<QuoteEntity> quote = new ObservableField<>();
	public final LiveBus liveBus = new LiveBus();

	private Context mApplicationContext;
	private String mSymbol;
	private RestRxManager mRestRxManager = new RestRxManager(new RestResponseHandler(), new RestHttpLogger());


	public StockDetailArchViewModel(Application application, Bundle extras)
	{
		super(application);
		mApplicationContext = application;

		// handle intent extras
		handleExtras(extras);
	}


	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart()
	{
		Logcat.d("");

		// load data
		if(quote.get() == null) loadData();
	}


	@Override
	public void onCleared()
	{
		Logcat.d("");
		super.onCleared();

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
			String callType = StocksRxServiceProvider.QUOTE_CALL_TYPE;
			if(!mRestRxManager.isRunning(callType))
			{
				// show progress
				state.set(StatefulLayout.PROGRESS);

				// subscribe
				Single<Response<QuoteEntity>> rawSingle = StocksRxServiceProvider.getService().quote("json", symbol);
				Single<Response<QuoteEntity>> single = mRestRxManager.setupRestSingleWithSchedulers(rawSingle, callType);
				single.subscribeWith(createQuoteObserver());
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.OFFLINE);
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
			state.set(StatefulLayout.CONTENT);
		}
		else
		{
			state.set(StatefulLayout.EMPTY);
		}
	}


	private void handleExtras(Bundle extras)
	{
		if(extras != null)
		{
			mSymbol = extras.getString(StockDetailActivity.EXTRA_SYMBOL);
		}
	}


	private void handleError(String message)
	{
		liveBus.send(new ToastEvent(message));
	}


	private Context getApplicationContext()
	{
		return mApplicationContext;
	}
}
