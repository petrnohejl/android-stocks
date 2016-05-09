package com.example.viewmodel;

import android.databinding.ObservableField;

import com.example.StocksApplication;
import com.example.entity.QuoteEntity;
import com.example.rest.provider.StocksRxProvider;
import com.example.rest.rx.RestSubscriber;
import com.example.rest.rx.SubscriberManager;
import com.example.ui.StockDetailView;
import com.example.utility.NetworkUtility;
import com.example.utility.RxUtility;
import com.example.view.StatefulLayout;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import retrofit2.Response;
import rx.Observable;


public class StockDetailRxViewModel extends BaseViewModel<StockDetailView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableField<QuoteEntity> quote = new ObservableField<>();

	private SubscriberManager mSubscriberManager = new SubscriberManager();


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

		// cancel async tasks
		if(mSubscriberManager != null) mSubscriberManager.unsubscribeAll();
	}


	public void loadData()
	{
		sendQuote("INTC");
	}


	public void refreshData()
	{
		sendQuote("INTC");
	}


	private void sendQuote(String symbol)
	{
		if(NetworkUtility.isOnline(StocksApplication.getContext()))
		{
			if(!mSubscriberManager.isRegistered(StocksRxProvider.QUOTE_CALL_TYPE))
			{
				// show progress
				state.set(StatefulLayout.State.PROGRESS);

				// subscribe
				Observable<Response<QuoteEntity>> observable = createQuoteObservable(symbol);
				observable.subscribe(createQuoteSubscriber());
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.State.OFFLINE);
		}
	}


	@RxLogObservable
	private Observable<Response<QuoteEntity>> createQuoteObservable(String symbol)
	{
		return StocksRxProvider.getService()
				.quote("json", symbol)
				.flatMap(RxUtility::catchHttpError)
				.compose(RxUtility.applySchedulers());
	}


	private RestSubscriber<Response<QuoteEntity>> createQuoteSubscriber()
	{
		return new RestSubscriber<>(mSubscriberManager, StocksRxProvider.QUOTE_CALL_TYPE,
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
				});
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
}
