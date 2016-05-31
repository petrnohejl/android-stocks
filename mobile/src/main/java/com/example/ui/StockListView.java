package com.example.ui;

import android.view.View;

import com.example.entity.LookupEntity;


public interface StockListView extends BaseView
{
	void onItemClick(View view, int position, long id, int viewType); // used by custom adapter
	void onItemLongClick(View view, int position, long id, int viewType); // used by custom adapter
	void onItemClick(LookupEntity lookup); // used by generic adapters
	boolean onItemLongClick(LookupEntity lookup); // used by generic adapters
}
