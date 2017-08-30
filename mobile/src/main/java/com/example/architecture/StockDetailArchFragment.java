package com.example.architecture;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.BR;
import com.example.StocksApplication;
import com.example.architecture.event.SnackbarEvent;
import com.example.architecture.event.ToastEvent;
import com.example.databinding.FragmentStockDetailArchBinding;


public class StockDetailArchFragment extends LifecycleFragment implements StockDetailArchView
{
	private StockDetailArchViewModel mViewModel;
	private FragmentStockDetailArchBinding mBinding;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setupViewModel();
		setupObservers();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		mBinding = setupBinding(inflater);
		return mBinding.getRoot();
	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();

		// leak canary watcher
		StocksApplication.getRefWatcher().watch(this);
		if(getActivity().isFinishing()) StocksApplication.getRefWatcher().watch(getViewModel());
	}


	@Override
	public void onClick(View view)
	{
		getViewModel().refreshData();
	}


	public void showToast(ToastEvent event)
	{
		Toast.makeText(getActivity(), event.message, Toast.LENGTH_LONG).show();
	}


	public void showSnackbar(SnackbarEvent event)
	{
		if(getView() != null)
		{
			Snackbar.make(getView(), event.message, Snackbar.LENGTH_LONG).show();
		}
	}


	private void setupViewModel()
	{
		StockDetailArchViewModelFactory factory = new StockDetailArchViewModelFactory(getActivity().getApplication(), getActivity().getIntent().getExtras());
		mViewModel = ViewModelProviders.of(this, factory).get(StockDetailArchViewModel.class);
		getLifecycle().addObserver(mViewModel);
	}


	private void setupObservers()
	{
		getViewModel().liveBus.observe(this, ToastEvent.class, this::showToast);
		getViewModel().liveBus.observe(this, SnackbarEvent.class, this::showSnackbar);
	}


	private FragmentStockDetailArchBinding setupBinding(LayoutInflater inflater)
	{
		FragmentStockDetailArchBinding binding = FragmentStockDetailArchBinding.inflate(inflater);
		binding.setVariable(BR.view, this);
		binding.setVariable(BR.viewModel, getViewModel());
		return binding;
	}


	private StockDetailArchViewModel getViewModel()
	{
		return mViewModel;
	}


	private ViewDataBinding getBinding()
	{
		return mBinding;
	}
}
