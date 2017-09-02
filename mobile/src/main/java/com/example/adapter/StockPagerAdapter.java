package com.example.adapter;

import com.example.R;
import com.example.ui.StockPagerView;
import com.example.viewmodel.StockPagerViewModel;

import org.alfonz.adapter.SimpleDataBoundPagerAdapter;


public class StockPagerAdapter extends SimpleDataBoundPagerAdapter
{
	public StockPagerAdapter(StockPagerView view, StockPagerViewModel viewModel)
	{
		super(
				R.layout.fragment_stock_pager_item,
				view,
				viewModel.lookups
		);
	}
}
