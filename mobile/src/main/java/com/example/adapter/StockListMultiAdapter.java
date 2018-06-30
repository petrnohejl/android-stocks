package com.example.adapter;

import com.example.R;
import com.example.entity.LookupEntity;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;

import org.alfonz.adapter.MultiDataBoundRecyclerAdapter;

public class StockListMultiAdapter extends MultiDataBoundRecyclerAdapter {
	public StockListMultiAdapter(StockListView view, StockListViewModel viewModel) {
		super(
				view,
				viewModel.headers,
				viewModel.lookups,
				viewModel.footers
		);
	}

	@Override
	public int getItemLayoutId(int position) {
		Object item = getItem(position);
		if (item instanceof String) {
			return R.layout.fragment_stock_list_header;
		} else if (item instanceof LookupEntity) {
			return R.layout.fragment_stock_list_item_clickable;
		} else if (item instanceof Object) {
			return R.layout.fragment_stock_list_footer;
		}
		throw new IllegalArgumentException("Unknown item type " + item);
	}
}
