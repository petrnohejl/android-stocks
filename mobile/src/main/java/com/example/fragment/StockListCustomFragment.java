package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.activity.StockDetailActivity;
import com.example.adapter.StockListCustomAdapter;
import com.example.databinding.FragmentStockListBinding;
import com.example.entity.LookupEntity;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;

public class StockListCustomFragment extends BaseFragment<StockListViewModel, FragmentStockListBinding> implements StockListView {
	private StockListCustomAdapter mAdapter;

	@Override
	public StockListViewModel setupViewModel() {
		StockListViewModel viewModel = new ViewModelProvider(this).get(StockListViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}

	@Override
	public FragmentStockListBinding inflateBindingLayout(@NonNull LayoutInflater inflater) {
		return FragmentStockListBinding.inflate(inflater);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getBinding().executePendingBindings(); // helps to reload recycler scroll position after orientation change
		setupAdapter();
	}

	@Override
	public void onItemClick(View view, int position, long id, int viewType) {
		int lookupPosition = mAdapter.getLookupPosition(position);
		startStockDetailActivity(getViewModel().lookups.get(lookupPosition).getSymbol());
//		getViewModel().addItem();
//		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemLongClick(View view, int position, long id, int viewType) {
		int lookupPosition = mAdapter.getLookupPosition(position);
		getViewModel().updateItem(lookupPosition);
		mAdapter.notifyItemChanged(position);
	}

	@Override
	public void onItemClick(LookupEntity lookup) {
	}

	@Override
	public boolean onItemLongClick(LookupEntity lookup) {
		return true;
	}

	private void setupAdapter() {
		if (mAdapter == null) {
			mAdapter = new StockListCustomAdapter(this, getViewModel());
			getBinding().stockListRecycler.setAdapter(mAdapter);
		}
	}

	private void startStockDetailActivity(String symbol) {
		Intent intent = StockDetailActivity.newIntent(getActivity(), symbol);
		getActivity().startActivity(intent);
	}
}
