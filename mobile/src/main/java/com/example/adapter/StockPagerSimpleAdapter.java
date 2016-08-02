package com.example.adapter;

import android.databinding.ObservableArrayList;

import com.example.R;
import com.example.adapter.base.SimpleDataBoundPagerAdapter;
import com.example.databinding.FragmentStockPagerBinding;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;


public class StockPagerSimpleAdapter extends SimpleDataBoundPagerAdapter<FragmentStockPagerBinding>
{
	public StockPagerSimpleAdapter(StockListView view, StockListViewModel viewModel)
	{
		super(
				R.layout.fragment_stock_pager_item,
				view,
				(ObservableArrayList<Object>) (ObservableArrayList<?>) viewModel.lookups
		);
	}
}
