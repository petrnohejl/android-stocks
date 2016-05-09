package com.example.viewmodel;

import android.databinding.ObservableField;
import android.os.AsyncTask;

import com.example.StocksApplication;
import com.example.entity.StockEntity;
import com.example.listener.OnLoadDataListener;
import com.example.task.LoadDataTask;
import com.example.ui.StockDetailView;
import com.example.utility.NetworkUtility;
import com.example.view.StatefulLayout;


public class HelloWorldViewModel extends BaseViewModel<StockDetailView> implements OnLoadDataListener
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableField<StockEntity> stock = new ObservableField<>();

	private LoadDataTask mLoadDataTask;


	@Override
	public void onStart()
	{
		super.onStart();

		// load data
		if(stock.get()==null) loadData();
	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();

		// cancel async tasks
		if(mLoadDataTask!=null) mLoadDataTask.cancel(true);
	}


	@Override
	public void onLoadData()
	{
		// get data
		StockEntity s = new StockEntity();
		s.setName("Test Product");
		stock.set(s);

		// show content
		if(stock.get()!=null)
		{
			state.set(StatefulLayout.State.CONTENT);
		}
		else
		{
			state.set(StatefulLayout.State.EMPTY);
		}
	}


	private void loadData()
	{
		if(NetworkUtility.isOnline(StocksApplication.getContext()))
		{
			// show progress
			state.set(StatefulLayout.State.PROGRESS);

			// run async task
			mLoadDataTask = new LoadDataTask(this);
			mLoadDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else
		{
			state.set(StatefulLayout.State.OFFLINE);
		}
	}


	public void updateName()
	{
		StockEntity s = stock.get();
		s.setName("Test " + System.currentTimeMillis());
		stock.notifyChange();
	}
}
