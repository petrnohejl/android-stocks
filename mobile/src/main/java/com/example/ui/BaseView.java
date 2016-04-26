package com.example.ui;

import android.support.annotation.StringRes;

import eu.inloop.viewmodel.IView;


public interface BaseView extends IView
{
	void showToast(@StringRes int stringRes);
	void showToast(String message);
	void showSnackbar(@StringRes int stringRes);
	void showSnackbar(String message);
}
