package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.R;
import com.example.activity.StockDetailActivity;
import com.example.databinding.FragmentStockListBinding;
import com.example.entity.LookupEntity;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;

import org.alfonz.adapter.SimpleDataBoundRecyclerAdapter;

public class StockListSimpleFragment extends BaseFragment<StockListViewModel, FragmentStockListBinding> implements StockListView {
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
	}

	@Override
	public void onItemLongClick(View view, int position, long id, int viewType) {
	}

	@Override
	public void onItemClick(LookupEntity lookup) {
		startStockDetailActivity(lookup.getSymbol());
//		getViewModel().addItem();
	}

	@Override
	public boolean onItemLongClick(LookupEntity lookup) {
		getViewModel().updateItem(lookup);
		return true;
	}

	private void setupAdapter() {
		SimpleDataBoundRecyclerAdapter adapter = new SimpleDataBoundRecyclerAdapter(
				R.layout.fragment_stock_list_item_clickable,
				this,
				getViewModel().lookups);
		getBinding().stockListRecycler.setAdapter(adapter);
	}

	private void startStockDetailActivity(String symbol) {
		Intent intent = StockDetailActivity.newIntent(getActivity(), symbol);
		getActivity().startActivity(intent);
	}
}
