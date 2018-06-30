package com.example.ui;

import android.view.View;

import com.example.entity.LookupEntity;

import org.alfonz.adapter.AdapterView;
import org.alfonz.arch.AlfonzView;

public interface StockListView extends AlfonzView, AdapterView {
	void onItemClick(View view, int position, long id, int viewType); // used by custom adapter
	void onItemLongClick(View view, int position, long id, int viewType); // used by custom adapter
	void onItemClick(LookupEntity lookup); // used by generic adapter (simple or multi)
	boolean onItemLongClick(LookupEntity lookup); // used by generic adapter (simple or multi)
}
