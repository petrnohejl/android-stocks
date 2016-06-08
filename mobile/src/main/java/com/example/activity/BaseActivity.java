package com.example.activity;

import android.os.Bundle;

import eu.inloop.viewmodel.base.ViewModelBaseEmptyActivity;


public abstract class BaseActivity extends ViewModelBaseEmptyActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}


	@Override
	public void onStart()
	{
		super.onStart();
	}


	@Override
	public void onResume()
	{
		super.onResume();
	}


	@Override
	public void onPause()
	{
		super.onPause();
	}


	@Override
	public void onStop()
	{
		super.onStop();
	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
}
