package com.example.adapter;

import android.databinding.ObservableArrayList;

import com.example.BR;
import com.example.R;
import com.example.adapter.base.DataBoundViewHolder;
import com.example.adapter.base.MultiDataBoundAdapter;
import com.example.entity.LookupEntity;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;

import java.util.List;


public class StockListMultiAdapter extends MultiDataBoundAdapter
{
	private StockListView mView;


	public StockListMultiAdapter(StockListView view, StockListViewModel viewModel)
	{
		super((ObservableArrayList<Object>)(ObservableArrayList<?>) viewModel.headers,
				(ObservableArrayList<Object>)(ObservableArrayList<?>) viewModel.lookups,
				(ObservableArrayList<Object>)(ObservableArrayList<?>) viewModel.footers);
		mView = view;
	}


	@Override
	protected void bindItem(DataBoundViewHolder holder, int position, List payloads)
	{
		super.bindItem(holder, position, payloads);
		holder.binding.setVariable(BR.view, mView);
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
