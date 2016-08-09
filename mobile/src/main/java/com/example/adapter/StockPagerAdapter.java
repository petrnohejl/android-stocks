package com.example.adapter;

import android.databinding.ObservableArrayList;

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
				(ObservableArrayList<Object>) (ObservableArrayList<?>) viewModel.lookups
		);
	}
}
