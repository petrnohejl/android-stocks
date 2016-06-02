package com.example.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.databinding.FragmentHelloWorldBinding;
import com.example.ui.HelloWorldView;
import com.example.viewmodel.HelloWorldViewModel;


public class HelloWorldFragment extends BaseFragment<HelloWorldView, HelloWorldViewModel> implements HelloWorldView
{
	private FragmentHelloWorldBinding mBinding;


	@Nullable
	@Override
	public Class<HelloWorldViewModel> getViewModelClass()
	{
		return HelloWorldViewModel.class;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hello_world, container, false);
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
	}
}
