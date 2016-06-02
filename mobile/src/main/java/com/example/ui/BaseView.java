package com.example.ui;

import android.os.Bundle;
import android.support.annotation.StringRes;

import eu.inloop.viewmodel.IView;


public interface BaseView extends IView
{
	Bundle getExtras();
	void showToast(@StringRes int stringRes);
	void showToast(String message);
	void showSnackbar(@StringRes int stringRes);
	void showSnackbar(String message);
}
