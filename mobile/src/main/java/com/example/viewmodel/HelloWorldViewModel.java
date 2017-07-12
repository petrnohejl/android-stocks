package com.example.viewmodel;

import android.databinding.ObservableField;
import android.os.AsyncTask;

import com.example.entity.QuoteEntity;
import com.example.task.LoadDataTask;
import com.example.ui.HelloWorldView;

import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;


public class HelloWorldViewModel extends BaseViewModel<HelloWorldView> implements LoadDataTask.OnLoadDataListener
{
	public final ObservableField<Integer> state = new ObservableField<>();
	public final ObservableField<QuoteEntity> quote = new ObservableField<>();

	private LoadDataTask mLoadDataTask;


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
		if(mLoadDataTask != null) mLoadDataTask.cancel(true);
	}


	@Override
	public void onLoadData()
	{
		// get data
		QuoteEntity q = new QuoteEntity();
		q.setName("Test Quote");
		quote.set(q);

		// show content
		if(quote.get() != null)
		{
			state.set(StatefulLayout.CONTENT);
		}
		else
		{
			state.set(StatefulLayout.EMPTY);
		}
	}


	public void updateName()
	{
		QuoteEntity q = quote.get();
		q.setName("Test " + System.currentTimeMillis());
		quote.notifyChange();
	}


	private void loadData()
	{
		if(NetworkUtility.isOnline(getApplicationContext()))
		{
			// show progress
			state.set(StatefulLayout.PROGRESS);

			// run async task
			mLoadDataTask = new LoadDataTask(this);
			mLoadDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else
		{
			state.set(StatefulLayout.OFFLINE);
		}
	}
}
