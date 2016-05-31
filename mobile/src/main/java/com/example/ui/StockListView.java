package com.example.ui;

import android.view.View;

import com.example.entity.LookupEntity;


public interface StockListView extends BaseView
{
	void onItemClick(View view, int position, long id, int viewType);
	void onItemLongClick(View view, int position, long id, int viewType);
	void onItemClick(LookupEntity lookup);
	boolean onItemLongClick(LookupEntity lookup);
}
