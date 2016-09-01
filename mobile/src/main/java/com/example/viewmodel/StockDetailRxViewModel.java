package com.example.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.StocksConfig;
import com.example.activity.StockDetailActivity;
import com.example.entity.QuoteEntity;
import com.example.rest.provider.StocksRxProvider;
import com.example.ui.StockDetailView;
import com.example.utility.NetworkManager;
import com.example.utility.RxUtility;
import com.example.utility.SubscriberManager;
import com.example.view.StatefulLayout;

import retrofit2.Response;
import rx.Observable;


public class StockDetailRxViewModel extends BaseViewModel<StockDetailView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableField<QuoteEntity> quote = new ObservableField<>();

	private String mSymbol;


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


	public void loadData()
	{
		sendQuoteNew(mSymbol);
	}


	public void refreshData()
	{
		sendQuoteNew(mSymbol);
	}


	public String getChartUrl()
	{
		return String.format(StocksConfig.CHART_BASE_URL, mSymbol);
	}


	private void sendQuoteNew(String symbol)
	{
		NetworkManager.executeWithOfflineStateHandle(state, () ->
		{
			if(!SubscriberManager.isCallRegistered(this.getClass(), StocksRxProvider.QUOTE_CALL_TYPE))
			{
				state.set(StatefulLayout.State.PROGRESS);

				Observable<Response<QuoteEntity>> restCall = StocksRxProvider.getService().quote("json", symbol);
				SubscriberManager.createSubscribedObservable(restCall, StocksRxProvider.QUOTE_CALL_TYPE, this.getClass())
						.subscribe(quoteEntityResponse -> quote.set(quoteEntityResponse.body()),
								throwable ->
								{
									handleError(RxUtility.getHttpErrorMessage(throwable));
									setState(quote);
								},
								() -> setState(quote));
			}
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


	private void handleExtras(Bundle extras)
	{
		if(extras != null)
		{
			mSymbol = extras.getString(StockDetailActivity.EXTRA_SYMBOL);
		}
	}
}
