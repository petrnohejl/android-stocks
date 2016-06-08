package com.example.adapter;

import android.databinding.ObservableArrayList;

import com.example.R;
import com.example.adapter.base.SimpleDataBoundAdapter;
import com.example.databinding.FragmentStockListItemClickableBinding;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;


public class StockListSimpleAdapter extends SimpleDataBoundAdapter<FragmentStockListItemClickableBinding>
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
