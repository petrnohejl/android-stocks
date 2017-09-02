package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.adapter.StockListMultiAdapter;
import com.example.architecture.StockDetailArchActivity;
import com.example.databinding.FragmentStockListBinding;
import com.example.entity.LookupEntity;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;


public class StockListMultiFragment extends BaseFragment<StockListView, StockListViewModel, FragmentStockListBinding> implements StockListView
{
	private StockListMultiAdapter mAdapter;


	@Nullable
	@Override
	public Class<StockListViewModel> getViewModelClass()
	{
		return StockListViewModel.class;
	}


	@Override
	public FragmentStockListBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
	{
		return FragmentStockListBinding.inflate(inflater);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		getBinding().executePendingBindings(); // set layout manager in recycler via binding adapter
		setupAdapter();
	}


	@Override
	public void onItemClick(View view, int position, long id, int viewType)
	{
	}


	@Override
	public void onItemLongClick(View view, int position, long id, int viewType)
	{
	}


	@Override
	public void onItemClick(LookupEntity lookup)
	{
		startStockDetailActivity(lookup.getSymbol());
//		getViewModel().addItem();
//		mAdapter.notifyDataSetChanged();
	}


	@Override
	public boolean onItemLongClick(LookupEntity lookup)
	{
		getViewModel().updateItem(lookup);
		return true;
	}


	private void setupAdapter()
	{
		if(mAdapter == null)
		{
			mAdapter = new StockListMultiAdapter(this, getViewModel());
			getBinding().fragmentStockListRecycler.setAdapter(mAdapter);
		}
	}


	private void startStockDetailActivity(String symbol)
	{
//		Intent intent = StockDetailActivity.newIntent(getActivity(), symbol);
		Intent intent = StockDetailArchActivity.newIntent(getActivity(), symbol);
		getActivity().startActivity(intent);
	}
}
