package com.example.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.R;

import org.alfonz.arch.widget.ToolbarIndicator;

public class StockDetailActivity extends BaseActivity {
	public static final String EXTRA_SYMBOL = "symbol";

	public static Intent newIntent(Context context, String symbol) {
		Intent intent = new Intent(context, StockDetailActivity.class);
		intent.putExtra(EXTRA_SYMBOL, symbol);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_detail);
		setupActionBar(ToolbarIndicator.BACK);
	}
}
