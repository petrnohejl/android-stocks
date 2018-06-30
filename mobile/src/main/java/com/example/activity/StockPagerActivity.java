package com.example.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.R;

import org.alfonz.arch.widget.ToolbarIndicator;

public class StockPagerActivity extends BaseActivity {
	public static Intent newIntent(Context context) {
		Intent intent = new Intent(context, StockPagerActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_pager);
		setupActionBar(ToolbarIndicator.BACK);
	}
}
