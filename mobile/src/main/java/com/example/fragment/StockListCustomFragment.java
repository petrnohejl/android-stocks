package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.activity.StockDetailActivity;
import com.example.adapter.StockListCustomAdapter;
import com.example.databinding.FragmentStockListBinding;
import com.example.entity.LookupEntity;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;


public class StockListCustomFragment extends BaseFragment<StockListView, StockListViewModel> implements StockListView
{
	private FragmentStockListBinding mBinding;
	private StockListCustomAdapter mAdapter;


	@Nullable
	@Override
	public Class<StockListViewModel> getViewModelClass()
	{
		return StockListViewModel.class;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mBinding = FragmentStockListBinding.inflate(inflater);
		mBinding.setView(this);
		mBinding.setViewModel(getViewModel());
		return mBinding.getRoot();
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setModelView(this);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		mBinding.executePendingBindings(); // set layout manager in recycler via binding adapter
		setupAdapter();
	}


	@Override
	public void onItemClick(View view, int position, long id, int viewType)
	{
		int lookupPosition = mAdapter.getLookupPosition(position);
		startStockDetailActivity(getViewModel().lookups.get(lookupPosition).getSymbol());
//		getViewModel().addItem();
//		mAdapter.notifyDataSetChanged();
	}


	@Override
	public void onItemLongClick(View view, int position, long id, int viewType)
	{
		int lookupPosition = mAdapter.getLookupPosition(position);
		getViewModel().updateItem(lookupPosition);
		mAdapter.notifyItemChanged(position);
	}


	@Override
	public void onItemClick(LookupEntity lookup)
	{
	}


	@Override
	public boolean onItemLongClick(LookupEntity lookup)
	{
		return true;
	}


	private void setupAdapter()
	{
		if(mAdapter == null)
		{
			mAdapter = new StockListCustomAdapter(this, getViewModel());
			mBinding.fragmentStockListRecycler.setAdapter(mAdapter);
		}
	}


	private void startStockDetailActivity(String symbol)
	{
		Intent intent = StockDetailActivity.newIntent(getActivity(), symbol);
		getActivity().startActivity(intent);
	}
}
