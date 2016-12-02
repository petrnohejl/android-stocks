package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.databinding.FragmentStockDetailBinding;
import com.example.ui.StockDetailView;
import com.example.viewmodel.StockDetailRxViewModel;


public class StockDetailFragment extends BaseBindingFragment<StockDetailView, StockDetailRxViewModel, FragmentStockDetailBinding> implements StockDetailView
{
	@Nullable
	@Override
	public Class<StockDetailRxViewModel> getViewModelClass()
	{
		return StockDetailRxViewModel.class;
	}


	@Override
	public FragmentStockDetailBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentStockDetailBinding.inflate(inflater);
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setModelView(this);
	}


	@Override
	public void onClick(View view)
	{
		getViewModel().refreshData();
	}
}
