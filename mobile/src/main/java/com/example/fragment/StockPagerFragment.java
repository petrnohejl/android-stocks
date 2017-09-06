package com.example.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.example.adapter.StockPagerAdapter;
import com.example.databinding.FragmentStockPagerBinding;
import com.example.ui.StockPagerView;
import com.example.viewmodel.StockPagerViewModel;


public class StockPagerFragment extends BaseFragment<StockPagerViewModel, FragmentStockPagerBinding> implements StockPagerView
{
	private StockPagerAdapter mAdapter;


	@Override
	public StockPagerViewModel setupViewModel()
	{
		StockPagerViewModel viewModel = ViewModelProviders.of(this).get(StockPagerViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}


	@Override
	public FragmentStockPagerBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
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
