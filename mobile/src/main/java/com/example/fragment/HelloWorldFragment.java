package com.example.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.example.databinding.FragmentHelloWorldBinding;
import com.example.ui.HelloWorldView;
import com.example.viewmodel.HelloWorldViewModel;

public class HelloWorldFragment extends BaseFragment<HelloWorldViewModel, FragmentHelloWorldBinding> implements HelloWorldView {
	@Override
	public HelloWorldViewModel setupViewModel() {
		HelloWorldViewModel viewModel = ViewModelProviders.of(this).get(HelloWorldViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}

	@Override
	public FragmentHelloWorldBinding inflateBindingLayout(@NonNull LayoutInflater inflater) {
		return FragmentHelloWorldBinding.inflate(inflater);
	}

	@Override
	public void onClick() {
		getViewModel().updateName();
	}
}
