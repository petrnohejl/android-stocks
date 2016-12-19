package com.example.adapter;

import com.example.R;
import com.example.databinding.FragmentStockListItemClickableBinding;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;

import org.alfonz.adapter.SimpleDataBoundRecyclerAdapter;


public class StockListSimpleAdapter extends SimpleDataBoundRecyclerAdapter<FragmentStockListItemClickableBinding>
{
	public StockListSimpleAdapter(StockListView view, StockListViewModel viewModel)
	{
		super(
				R.layout.fragment_stock_list_item_clickable,
				view,
				viewModel.lookups
		);
	}
}
