package com.example.adapter;

import android.databinding.ObservableArrayList;

import com.example.R;
import com.example.adapter.base.MultiDataBoundRecyclerAdapter;
import com.example.entity.LookupEntity;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;


public class StockListMultiAdapter extends MultiDataBoundRecyclerAdapter
{
	public StockListMultiAdapter(StockListView view, StockListViewModel viewModel)
	{
		super(
				view,
				(ObservableArrayList<Object>) (ObservableArrayList<?>) viewModel.headers,
				(ObservableArrayList<Object>) (ObservableArrayList<?>) viewModel.lookups,
				(ObservableArrayList<Object>) (ObservableArrayList<?>) viewModel.footers
		);
	}


	@Override
	public int getItemLayoutId(int position)
	{
		Object item = getItem(position);
		if(item instanceof String)
		{
			return R.layout.fragment_stock_list_header;
		}
		else if(item instanceof LookupEntity)
		{
			return R.layout.fragment_stock_list_item_clickable;
		}
		else if(item instanceof Object)
		{
			return R.layout.fragment_stock_list_footer;
		}
		throw new IllegalArgumentException("Unknown item type " + item);
	}
}
