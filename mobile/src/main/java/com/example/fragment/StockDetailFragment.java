package com.example.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.example.databinding.FragmentStockDetailBinding;
import com.example.ui.StockDetailView;
import com.example.viewmodel.StockDetailRxViewModel;
import com.example.viewmodel.factory.StockDetailRxViewModelFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

public class StockDetailFragment extends BaseFragment<StockDetailRxViewModel, FragmentStockDetailBinding> implements StockDetailView {
	@Override
	public StockDetailRxViewModel setupViewModel() {
		StockDetailRxViewModelFactory factory = new StockDetailRxViewModelFactory(getActivity().getIntent().getExtras());
		StockDetailRxViewModel viewModel = ViewModelProviders.of(this, factory).get(StockDetailRxViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}

	@Override
	public FragmentStockDetailBinding inflateBindingLayout(@NonNull LayoutInflater inflater) {
		return FragmentStockDetailBinding.inflate(inflater);
	}

	@Override
	public void onClick(View view) {
		getViewModel().refreshData();
	}
}
