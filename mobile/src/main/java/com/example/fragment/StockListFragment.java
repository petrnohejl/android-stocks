package com.example.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.adapter.StockListAdapter;
import com.example.databinding.FragmentStockListBinding;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;


public class StockListFragment extends BaseFragment<StockListView, StockListViewModel> implements StockListView
{
	private FragmentStockListBinding mBinding;
	private StockListAdapter mAdapter;


	@Nullable
	@Override
	public Class<StockListViewModel> getViewModelClass()
	{
		return StockListViewModel.class;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_list, container, false);
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
		getViewModel().addItem();
	}


	@Override
	public void onItemLongClick(View view, int position, long id, int viewType)
	{
		int lookupPosition = mAdapter.getLookupPosition(position);
		getViewModel().updateItem(lookupPosition);
		mAdapter.notifyItemChanged(position);
	}


	private void setupAdapter()
	{
		if(mAdapter == null)
		{
			mAdapter = new StockListAdapter(this, getViewModel());
			mBinding.stockListRecycler.setAdapter(mAdapter);
		}
	}
}
