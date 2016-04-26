package com.example.viewmodel;

import android.databinding.ObservableField;

import com.example.StocksApplication;
import com.example.entity.QuoteEntity;
import com.example.rest.BaseCallback;
import com.example.rest.CallManager;
import com.example.rest.provider.StocksProvider;
import com.example.ui.StockDetailView;
import com.example.utility.NetworkUtility;
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
	public void onModelRemoved()
	{
		super.onModelRemoved();

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
			setState();
		}


		@Override
		public void onError(Call<QuoteEntity> call, Response<QuoteEntity> response)
		{
			handleError(getErrorMessage(response));
			setState();
		}


		@Override
		public void onFail(Call<QuoteEntity> call, Throwable throwable)
		{
			handleFail(getFailMessage(throwable));
			setState();
		}


		private void setState()
		{
			if(quote.get() != null)
			{
				state.set(StatefulLayout.State.CONTENT);
			}
			else
			{
				state.set(StatefulLayout.State.EMPTY);
			}
		}
	}
}
