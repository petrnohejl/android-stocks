package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.example.adapter.StockPagerAdapter;
import com.example.databinding.FragmentStockPagerBinding;
import com.example.ui.StockPagerView;
import com.example.viewmodel.StockPagerViewModel;


public class StockPagerFragment extends BaseFragment<StockPagerView, StockPagerViewModel, FragmentStockPagerBinding> implements StockPagerView
{
	private StockPagerAdapter mAdapter;


	@Nullable
	@Override
	public Class<StockPagerViewModel> getViewModelClass()
	{
		return StockPagerViewModel.class;
	}


	@Override
	public FragmentStockPagerBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentStockPagerBinding.inflate(inflater);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setupAdapter();
	}


	private void setupAdapter()
	{
		if(mAdapter == null)
		{
			mAdapter = new StockPagerAdapter(this, getViewModel());
			getBinding().fragmentStockPagerPager.setAdapter(mAdapter);
		}
	}
}
