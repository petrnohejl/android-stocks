package com.example.ui;

import android.view.View;


public interface StockListView extends BaseView
{
	void onItemClick(View view, int position, long id, int viewType);
	void onItemLongClick(View view, int position, long id, int viewType);
}
