package com.example.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.databinding.FragmentStockDetailBinding;
import com.example.ui.StockDetailView;
import com.example.viewmodel.StockDetailRxViewModel;


public class StockDetailFragment extends BaseFragment<StockDetailView, StockDetailRxViewModel, FragmentStockDetailBinding> implements StockDetailView
{
	@Nullable
	@Override
	public Class<StockDetailRxViewModel> getViewModelClass()
	{
		return StockDetailRxViewModel.class;
	}


	@Override
	public FragmentStockDetailBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
	{
		return FragmentStockDetailBinding.inflate(inflater);
	}


	@Override
	public void onClick(View view)
	{
		getViewModel().refreshData();
	}
}
