package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.utility.Logcat;

import eu.inloop.viewmodel.AbstractViewModel;
import eu.inloop.viewmodel.IView;
import eu.inloop.viewmodel.base.ViewModelBaseFragment;


public abstract class BaseFragment<T extends IView, R extends AbstractViewModel<T>> extends ViewModelBaseFragment<T, R>
{
	@Override
	public void onAttach(Context context)
	{
		Logcat.d("");
		super.onAttach(context);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Logcat.d("");
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Logcat.d("");
		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		Logcat.d("");
		super.onViewCreated(view, savedInstanceState);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		Logcat.d("");
		super.onActivityCreated(savedInstanceState);
	}
	
	
	@Override
	public void onStart()
	{
		Logcat.d("");
		super.onStart();
	}
	
	
	@Override
	public void onResume()
	{
		Logcat.d("");
		super.onResume();
	}
	
	
	@Override
	public void onPause()
	{
		Logcat.d("");
		super.onPause();
	}
	
	
	@Override
	public void onStop()
	{
		Logcat.d("");
		super.onStop();
	}
	
	
	@Override
	public void onDestroyView()
	{
		Logcat.d("");
		super.onDestroyView();
	}
	
	
	@Override
	public void onDestroy()
	{
		Logcat.d("");
		super.onDestroy();
	}
	
	
	@Override
	public void onDetach()
	{
		Logcat.d("");
		super.onDetach();
	}
}
