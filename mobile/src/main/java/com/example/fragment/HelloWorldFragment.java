package com.example.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

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
	public FragmentHelloWorldBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
	{
		return FragmentHelloWorldBinding.inflate(inflater);
	}


	@Override
	public void onClick()
	{
		getViewModel().updateName();
	}
}
