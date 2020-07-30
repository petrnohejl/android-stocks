package com.example.viewmodel;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.entity.LookupEntity;
import com.example.rest.RestHttpLogger;
import com.example.rest.RestResponseHandler;
import com.example.rest.router.StocksRxRouter;

import org.alfonz.rest.rx.RestRxManager;
import org.alfonz.rx.AlfonzDisposableSingleObserver;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import retrofit2.Response;

public class StockListViewModel extends BaseViewModel implements LifecycleObserver {
	public final ObservableField<Integer> state = new ObservableField<>();
	public final ObservableList<String> headers = new ObservableArrayList<>();
	public final ObservableList<LookupEntity> lookups = new ObservableArrayList<>();
	public final ObservableList<Object> footers = new ObservableArrayList<>();

	private RestRxManager mRestRxManager = new RestRxManager(new RestResponseHandler(), new RestHttpLogger());

	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart() {
		// load data
		if (lookups.isEmpty()) loadData();
	}

	@Override
	public void onCleared() {
		super.onCleared();

		// unsubscribe
		mRestRxManager.disposeAll();
	}

	public void loadData() {
		sendLookup("bank");
	}

	public void refreshData() {
		sendLookup("bank");
	}

	public void addItem() {
		LookupEntity lookup = new LookupEntity();
		lookup.setSymbol("YOYO");
		lookup.setName("Yoyo " + System.currentTimeMillis());
		lookup.setExchange("NYSE");
		lookups.add(0, lookup);
	}

	public void updateItem(int lookupPosition) {
		LookupEntity lookup = lookups.get(lookupPosition);
		lookup.setSymbol("YOYO");
		lookup.setName("Yoyo " + System.currentTimeMillis());
		lookup.setExchange("NYSE");
	}

	public void updateItem(LookupEntity lookup) {
		lookup.setSymbol("YOYO");
		lookup.setName("Yoyo " + System.currentTimeMillis());
		lookup.setExchange("NYSE");
	}

	public void removeItem() {
		lookups.remove(0);
	}

	private void sendLookup(String input) {
		if (NetworkUtility.isOnline(getApplicationContext())) {
			String callType = StocksRxRouter.LOOKUP_CALL_TYPE;
			if (!mRestRxManager.isRunning(callType)) {
				// show progress
				state.set(StatefulLayout.PROGRESS);

				// subscribe
				Single<Response<List<LookupEntity>>> rawSingle = StocksRxRouter.getService().lookup("json", input);
				Single<Response<List<LookupEntity>>> single = mRestRxManager.setupRestSingleWithSchedulers(rawSingle, callType);
				single.subscribeWith(createLookupObserver());
			}
		} else {
			// show offline
			state.set(StatefulLayout.OFFLINE);
		}
	}

	private DisposableSingleObserver<Response<List<LookupEntity>>> createLookupObserver() {
		return AlfonzDisposableSingleObserver.newInstance(
				response ->
				{
					lookups.clear();
					lookups.addAll(response.body());

					headers.clear();
					headers.add("one");
					headers.add("two");
					headers.add("three");

					footers.clear();
					footers.add(new Object());

					setState(lookups);
				},
				throwable ->
				{
					handleError(mRestRxManager.getHttpErrorMessage(throwable));
					setState(lookups);
				}
		);
	}

	private void setState(ObservableList<LookupEntity> data) {
		if (!data.isEmpty()) {
			state.set(StatefulLayout.CONTENT);
		} else {
			state.set(StatefulLayout.EMPTY);
		}
	}
}
