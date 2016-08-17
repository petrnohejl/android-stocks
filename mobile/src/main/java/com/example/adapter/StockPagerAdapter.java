package com.example.adapter;

import com.example.R;
import com.example.adapter.base.SimpleDataBoundPagerAdapter;
import com.example.databinding.FragmentStockPagerItemBinding;
import com.example.ui.StockPagerView;
import com.example.viewmodel.StockPagerViewModel;


public class StockPagerAdapter extends SimpleDataBoundPagerAdapter<FragmentStockPagerItemBinding>
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
