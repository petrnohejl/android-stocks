package com.example.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.R;
import com.example.databinding.FragmentStockDetailBinding;
import com.example.ui.StockDetailView;
import com.example.viewmodel.StockDetailViewModel;


public class StockDetailFragment extends BaseFragment<StockDetailView, StockDetailViewModel> implements StockDetailView
{
	private FragmentStockDetailBinding mBinding;


	@Nullable
	@Override
	public Class<StockDetailViewModel> getViewModelClass()
	{
		return StockDetailViewModel.class;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_detail, container, false);
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
	public void onClick(View view)
	{
		getViewModel().updateName();
		Toast.makeText(getActivity(), "Yo!", Toast.LENGTH_SHORT).show();
	}
}
