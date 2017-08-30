package com.example.architecture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.R;


public class StockDetailArchActivity extends AppCompatActivity
{
	public static final String EXTRA_SYMBOL = "symbol";


	public static Intent newIntent(Context context, String symbol)
	{
		Intent intent = new Intent(context, StockDetailArchActivity.class);
		intent.putExtra(EXTRA_SYMBOL, symbol);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_detail_arch);
		setupActionBar();
	}


	private void setupActionBar()
	{
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar bar = getSupportActionBar();
		bar.setDisplayUseLogoEnabled(false);
		bar.setDisplayShowTitleEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setHomeButtonEnabled(true);
	}
}
