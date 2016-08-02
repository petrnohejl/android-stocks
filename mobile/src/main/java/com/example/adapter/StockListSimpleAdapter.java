package com.example.adapter;

import android.databinding.ObservableArrayList;

import com.example.R;
import com.example.adapter.base.SimpleDataBoundRecyclerAdapter;
import com.example.databinding.FragmentStockListItemClickableBinding;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;


public class StockListSimpleAdapter extends SimpleDataBoundRecyclerAdapter<FragmentStockListItemClickableBinding>
{
	public StockListSimpleAdapter(StockListView view, StockListViewModel viewModel)
	{
		super(
				R.layout.fragment_stock_list_item_clickable,
				view,
				(ObservableArrayList<Object>) (ObservableArrayList<?>) viewModel.lookups
		);
	}
}
