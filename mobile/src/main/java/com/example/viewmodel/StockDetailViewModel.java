package com.example.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.StocksConfig;
import com.example.activity.StockDetailActivity;
import com.example.entity.QuoteEntity;
import com.example.rest.RestHttpLogger;
import com.example.rest.RestResponseHandler;
import com.example.rest.provider.StocksServiceProvider;
import com.example.ui.StockDetailView;

import org.alfonz.rest.HttpException;
import org.alfonz.rest.call.CallManager;
import org.alfonz.rest.call.Callback;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import retrofit2.Call;
import retrofit2.Response;


public class StockDetailViewModel extends BaseViewModel<StockDetailView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableField<QuoteEntity> quote = new ObservableField<>();

	private String mSymbol;
	private CallManager mCallManager = new CallManager(new RestResponseHandler(), new RestHttpLogger());


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

		// cancel async tasks
		if(mCallManager != null) mCallManager.cancelRunningCalls();
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
			String callType = StocksServiceProvider.QUOTE_CALL_TYPE;
			if(!mCallManager.hasRunningCall(callType))
			{
				// show progress
				state.set(StatefulLayout.State.PROGRESS);

				// enqueue call
				Call<QuoteEntity> call = StocksServiceProvider.getService().quote("json", symbol);
				QuoteCallback callback = new QuoteCallback(mCallManager);
				mCallManager.enqueueCall(call, callback, callType);
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.State.OFFLINE);
		}
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


	private class QuoteCallback extends Callback<QuoteEntity>
	{
		public QuoteCallback(CallManager callManager)
		{
			super(callManager);
		}


		@Override
		public void onSuccess(Call<QuoteEntity> call, Response<QuoteEntity> response)
		{
			quote.set(response.body());
			setState(quote);
		}


		@Override
		public void onError(Call<QuoteEntity> call, HttpException exception)
		{
			handleError(mCallManager.getHttpErrorMessage(exception));
			setState(quote);
		}


		@Override
		public void onFail(Call<QuoteEntity> call, Throwable throwable)
		{
			handleError(mCallManager.getHttpErrorMessage(throwable));
			setState(quote);
		}
	}
}
