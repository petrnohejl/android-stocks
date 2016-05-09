package com.example.viewmodel;

import android.databinding.ObservableField;

import com.example.StocksApplication;
import com.example.entity.QuoteEntity;
import com.example.rest.call.BaseCallback;
import com.example.rest.call.CallManager;
import com.example.rest.provider.StocksProvider;
import com.example.ui.StockDetailView;
import com.example.utility.NetworkUtility;
import com.example.utility.RestUtility;
import com.example.view.StatefulLayout;

import retrofit2.Call;
import retrofit2.Response;


public class StockDetailViewModel extends BaseViewModel<StockDetailView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableField<QuoteEntity> quote = new ObservableField<>();

	private CallManager mCallManager = new CallManager();


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
			String callType = StocksProvider.QUOTE_CALL_TYPE;
			if(!mCallManager.hasRunningCall(callType))
			{
				// show progress
				state.set(StatefulLayout.State.PROGRESS);

				// enqueue call
				Call<QuoteEntity> call = StocksProvider.getService().quote("json", symbol);
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


	private class QuoteCallback extends BaseCallback<QuoteEntity>
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
		public void onError(Call<QuoteEntity> call, Response<QuoteEntity> response)
		{
			handleError(RestUtility.getErrorMessage(response));
			setState(quote);
		}


		@Override
		public void onFail(Call<QuoteEntity> call, Throwable throwable)
		{
			handleError(RestUtility.getFailMessage(throwable));
			setState(quote);
		}
	}
}
