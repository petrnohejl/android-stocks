package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.StocksApplication;
import com.example.ui.BaseView;
import com.example.utility.Logcat;
import com.example.viewmodel.BaseViewModel;

import eu.inloop.viewmodel.base.ViewModelBaseFragment;


public abstract class BaseFragment<T extends BaseView, R extends BaseViewModel<T>> extends ViewModelBaseFragment<T, R> implements BaseView
{
	@Override
	public void onAttach(Context context)
	{
		Logcat.v("");
		super.onAttach(context);
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Logcat.v("");
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Logcat.v("");
		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		Logcat.v("");
		super.onViewCreated(view, savedInstanceState);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		Logcat.v("");
		super.onActivityCreated(savedInstanceState);
	}


	@Override
	public void onStart()
	{
		Logcat.v("");
		super.onStart();
	}


	@Override
	public void onResume()
	{
		Logcat.v("");
		super.onResume();
	}


	@Override
	public void onPause()
	{
		Logcat.v("");
		super.onPause();
	}


	@Override
	public void onStop()
	{
		Logcat.v("");
		super.onStop();
	}


	@Override
	public void onDestroyView()
	{
		Logcat.v("");
		super.onDestroyView();
	}


	@Override
	public void onDestroy()
	{
		Logcat.v("");
		super.onDestroy();

		// leak canary watcher
		StocksApplication.getRefWatcher().watch(this);
		if(getActivity().isFinishing()) StocksApplication.getRefWatcher().watch(getViewModel());
	}


	@Override
	public void onDetach()
	{
		Logcat.v("");
		super.onDetach();
	}


	@Override
	public Bundle getExtras()
	{
		return getActivity().getIntent().getExtras();
	}


	@Override
	public void showToast(@StringRes int stringRes)
	{
		Toast.makeText(getActivity(), stringRes, Toast.LENGTH_LONG).show();
	}


	@Override
	public void showToast(String message)
	{
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}


	@Override
	public void showSnackbar(@StringRes int stringRes)
	{
		if(getView() != null)
		{
			Snackbar.make(getView(), stringRes, Snackbar.LENGTH_LONG).show();
		}
	}


	@Override
	public void showSnackbar(String message)
	{
		if(getView() != null)
		{
			Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
		}
	}
}
