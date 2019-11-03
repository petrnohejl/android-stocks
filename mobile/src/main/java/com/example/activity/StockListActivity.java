package com.example.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.fragment.app.DialogFragment;

import com.example.R;
import com.example.dialog.AboutDialogFragment;

import org.alfonz.arch.widget.ToolbarIndicator;

public class StockListActivity extends BaseActivity {
	public static Intent newIntent(Context context) {
		Intent intent = new Intent(context, StockListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_list);
		setupActionBar(ToolbarIndicator.NONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// action bar menu
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.activity_stock_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// action bar menu behavior
		switch (item.getItemId()) {
			case R.id.menu_stock_list_hello:
				startHelloWorldActivity();
				return true;

			case R.id.menu_stock_list_pager:
				startStockPagerActivity();
				return true;

			case R.id.menu_stock_list_about:
				showAboutDialogFragment();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void startHelloWorldActivity() {
		Intent intent = HelloWorldActivity.newIntent(this);
		startActivity(intent);
	}

	private void startStockPagerActivity() {
		Intent intent = StockPagerActivity.newIntent(this);
		startActivity(intent);
	}

	private void showAboutDialogFragment() {
		DialogFragment fragment = AboutDialogFragment.newInstance();
		fragment.show(getSupportFragmentManager(), AboutDialogFragment.class.getName());
	}
}
