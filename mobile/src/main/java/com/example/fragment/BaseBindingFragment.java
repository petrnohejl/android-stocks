package com.example.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.BR;
import com.example.ui.BaseView;
import com.example.viewmodel.BaseViewModel;


public abstract class BaseBindingFragment<T extends BaseView, R extends BaseViewModel<T>, B extends ViewDataBinding> extends BaseFragment<T, R> implements BaseView
{
	private B mBinding;


	public abstract B inflateBindingLayout(LayoutInflater inflater);


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		mBinding = setupBinding(inflater);
		return mBinding.getRoot();
	}


	public B getBinding()
	{
		return mBinding;
	}


	private B setupBinding(LayoutInflater inflater)
	{
		B binding = inflateBindingLayout(inflater);
		binding.setVariable(BR.view, this);
		binding.setVariable(BR.viewModel, getViewModel());
		return binding;
	}
}
