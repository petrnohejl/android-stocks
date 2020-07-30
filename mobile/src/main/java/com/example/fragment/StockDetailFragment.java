package com.example.fragment;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.databinding.FragmentStockDetailBinding;
import com.example.ui.StockDetailView;
import com.example.viewmodel.StockDetailRxViewModel;
import com.example.viewmodel.factory.StockDetailRxViewModelFactory;

public class StockDetailFragment extends BaseFragment<StockDetailRxViewModel, FragmentStockDetailBinding> implements StockDetailView {
	@Override
	public StockDetailRxViewModel setupViewModel() {
		StockDetailRxViewModelFactory factory = new StockDetailRxViewModelFactory(getActivity().getIntent().getExtras());
		StockDetailRxViewModel viewModel = new ViewModelProvider(this, factory).get(StockDetailRxViewModel.class);
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
