package com.example.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.databinding.FragmentStockListFooterBinding;
import com.example.databinding.FragmentStockListHeaderBinding;
import com.example.databinding.FragmentStockListItemBinding;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;


public class StockListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private static final int VIEW_TYPE_HEADER = 0;
	private static final int VIEW_TYPE_ITEM = 1;
	private static final int VIEW_TYPE_FOOTER = 2;

	private StockListView mView;
	private StockListViewModel mViewModel;


	public StockListAdapter(StockListView view, StockListViewModel viewModel)
	{
		mView = view;
		mViewModel = viewModel;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		// inflate view and create view holder
		if(viewType == VIEW_TYPE_HEADER)
		{
			FragmentStockListHeaderBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_list_header, parent, false);
			return new HeaderViewHolder(binding);
		}
		else if(viewType == VIEW_TYPE_ITEM)
		{
			FragmentStockListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_list_item, parent, false);
			return new LookupViewHolder(binding, mView);
		}
		else if(viewType == VIEW_TYPE_FOOTER)
		{
			FragmentStockListFooterBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_list_footer, parent, false);
			return new FooterViewHolder(binding);
		}
		else
		{
			throw new RuntimeException("There is no view type that matches the type " + viewType);
		}
	}


	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
	{
		if(viewHolder instanceof HeaderViewHolder)
		{
			((HeaderViewHolder) viewHolder).bindData(mViewModel, getHeaderPosition(position));
		}
		else if(viewHolder instanceof LookupViewHolder)
		{
			((LookupViewHolder) viewHolder).bindData(mViewModel, getLookupPosition(position));
		}
		else if(viewHolder instanceof FooterViewHolder)
		{
			((FooterViewHolder) viewHolder).bindData(mViewModel, getFooterPosition(position));
		}
	}


	@Override
	public int getItemCount()
	{
		int size = 0;
		size += mViewModel.headers.size();
		size += mViewModel.lookups.size();
		size += mViewModel.footers.size();
		return size;
	}


	@Override
	public int getItemViewType(int position)
	{
		int headers = mViewModel.headers.size();
		int lookups = mViewModel.lookups.size();
		int footers = mViewModel.footers.size();

		if(position < headers) return VIEW_TYPE_HEADER;
		else if(position < headers + lookups) return VIEW_TYPE_ITEM;
		else if(position < headers + lookups + footers) return VIEW_TYPE_FOOTER;
		else return -1;
	}


	public int getHeaderCount()
	{
		return mViewModel.headers.size();
	}


	public int getLookupCount()
	{
		return mViewModel.lookups.size();
	}


	public int getFooterCount()
	{
		return mViewModel.footers.size();
	}


	public int getHeaderPosition(int recyclerPosition)
	{
		return recyclerPosition;
	}


	public int getLookupPosition(int recyclerPosition)
	{
		return recyclerPosition - getHeaderCount();
	}


	public int getFooterPosition(int recyclerPosition)
	{
		return recyclerPosition - getHeaderCount() - getLookupCount();
	}


	public int getRecyclerPositionByHeader(int headerPosition)
	{
		return headerPosition;
	}


	public int getRecyclerPositionByLookup(int lookupPosition)
	{
		return lookupPosition + getHeaderCount();
	}


	public int getRecyclerPositionByFooter(int footerPosition)
	{
		return footerPosition + getLookupCount() + getHeaderCount();
	}


	public void stop()
	{
		// TODO: stop image loader
	}


	public static final class HeaderViewHolder extends RecyclerView.ViewHolder
	{
		private FragmentStockListHeaderBinding mBinding;


		public HeaderViewHolder(FragmentStockListHeaderBinding binding)
		{
			super(binding.getRoot());
			mBinding = binding;
		}


		public void bindData(StockListViewModel viewModel, int headerPosition)
		{
			mBinding.setViewModel(viewModel);
			mBinding.setHeaderPosition(headerPosition);
			mBinding.executePendingBindings();
		}
	}


	public static final class LookupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
	{
		private FragmentStockListItemBinding mBinding;
		private StockListView mView;


		public LookupViewHolder(FragmentStockListItemBinding binding, StockListView view)
		{
			super(binding.getRoot());
			mBinding = binding;
			mView = view;

			// set listener
			itemView.setOnClickListener(this);
			itemView.setOnLongClickListener(this);
		}


		@Override
		public void onClick(View view)
		{
			int position = getAdapterPosition();
			if(position != RecyclerView.NO_POSITION)
			{
				mView.onItemClick(view, position, getItemId(), getItemViewType());
			}
		}


		@Override
		public boolean onLongClick(View view)
		{
			int position = getAdapterPosition();
			if(position != RecyclerView.NO_POSITION)
			{
				mView.onItemLongClick(view, position, getItemId(), getItemViewType());
			}
			return true;
		}


		public void bindData(StockListViewModel viewModel, int lookupPosition)
		{
			mBinding.setViewModel(viewModel);
			mBinding.setLookupPosition(lookupPosition);
			mBinding.executePendingBindings();
		}
	}


	public static final class FooterViewHolder extends RecyclerView.ViewHolder
	{
		FragmentStockListFooterBinding mBinding;


		public FooterViewHolder(FragmentStockListFooterBinding binding)
		{
			super(binding.getRoot());
			mBinding = binding;
		}


		public void bindData(StockListViewModel viewModel, int footerPosition)
		{
			mBinding.setViewModel(viewModel);
			mBinding.setFooterPosition(footerPosition);
			mBinding.executePendingBindings();
		}
	}
}
