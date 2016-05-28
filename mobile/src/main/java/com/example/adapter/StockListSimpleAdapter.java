package com.example.adapter;

import com.example.R;
import com.example.adapter.base.DataBoundViewHolder;
import com.example.adapter.base.SimpleDataBoundAdapter;
import com.example.databinding.FragmentStockListSimpleItemBinding;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;

import java.util.List;


public class StockListSimpleAdapter extends SimpleDataBoundAdapter<FragmentStockListSimpleItemBinding>
{
	private StockListView mView;
	private StockListViewModel mViewModel;


	public StockListSimpleAdapter(StockListView view, StockListViewModel viewModel)
	{
		super(R.layout.fragment_stock_list_simple_item);
		mView = view;
		mViewModel = viewModel;
	}


	@Override
	protected void bindItem(DataBoundViewHolder<FragmentStockListSimpleItemBinding> holder, int position, List<Object> payloads)
	{
		holder.binding.setView(mView);
		holder.binding.setData(mViewModel.lookups.get(position));
	}


	@Override
	public int getItemCount()
	{
		return mViewModel.lookups.size();
	}
}
