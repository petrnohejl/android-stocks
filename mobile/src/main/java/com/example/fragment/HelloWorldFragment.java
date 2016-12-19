package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.databinding.FragmentHelloWorldBinding;
import com.example.ui.HelloWorldView;
import com.example.viewmodel.HelloWorldViewModel;


public class HelloWorldFragment extends BaseFragment<HelloWorldView, HelloWorldViewModel, FragmentHelloWorldBinding> implements HelloWorldView
{
	@Nullable
	@Override
	public Class<HelloWorldViewModel> getViewModelClass()
	{
		return HelloWorldViewModel.class;
	}


	@Override
	public FragmentHelloWorldBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentHelloWorldBinding.inflate(inflater);
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setModelView(this);
	}


	@Override
	public void onClick()
	{
		getViewModel().updateName();
	}
}
