package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.databinding.FragmentStockListFooterBinding;
import com.example.databinding.FragmentStockListHeaderBinding;
import com.example.databinding.FragmentStockListItemBinding;
import com.example.entity.LookupEntity;
import com.example.ui.StockListView;
import com.example.viewmodel.StockListViewModel;

public class StockListCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private static final int VIEW_TYPE_HEADER = R.layout.fragment_stock_list_header;
	private static final int VIEW_TYPE_ITEM = R.layout.fragment_stock_list_item;
	private static final int VIEW_TYPE_FOOTER = R.layout.fragment_stock_list_footer;

	private StockListView mView;
	private StockListViewModel mViewModel;

	public StockListCustomAdapter(StockListView view, StockListViewModel viewModel) {
		mView = view;
		mViewModel = viewModel;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		// inflate view and create view holder
		if (viewType == VIEW_TYPE_HEADER) {
			FragmentStockListHeaderBinding binding = DataBindingUtil.inflate(inflater, VIEW_TYPE_HEADER, parent, false);
			return new HeaderViewHolder(binding);
		} else if (viewType == VIEW_TYPE_ITEM) {
			FragmentStockListItemBinding binding = DataBindingUtil.inflate(inflater, VIEW_TYPE_ITEM, parent, false);
			return new LookupViewHolder(binding, mView);
		} else if (viewType == VIEW_TYPE_FOOTER) {
			FragmentStockListFooterBinding binding = DataBindingUtil.inflate(inflater, VIEW_TYPE_FOOTER, parent, false);
			return new FooterViewHolder(binding);
		} else {
			throw new RuntimeException("There is no view type that matches the type " + viewType);
		}
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
		if (viewHolder instanceof HeaderViewHolder) {
			((HeaderViewHolder) viewHolder).bindData(mView, mViewModel.headers.get(getHeaderPosition(position)));
		} else if (viewHolder instanceof LookupViewHolder) {
			((LookupViewHolder) viewHolder).bindData(mView, mViewModel.lookups.get(getLookupPosition(position)));
		} else if (viewHolder instanceof FooterViewHolder) {
			((FooterViewHolder) viewHolder).bindData(mView, mViewModel.footers.get(getFooterPosition(position)));
		}
	}

	@Override
	public int getItemCount() {
		int size = 0;
		size += mViewModel.headers.size();
		size += mViewModel.lookups.size();
		size += mViewModel.footers.size();
		return size;
	}

	@Override
	public int getItemViewType(int position) {
		int headers = mViewModel.headers.size();
		int lookups = mViewModel.lookups.size();
		int footers = mViewModel.footers.size();

		if (position < headers) return VIEW_TYPE_HEADER;
		else if (position < headers + lookups) return VIEW_TYPE_ITEM;
		else if (position < headers + lookups + footers) return VIEW_TYPE_FOOTER;
		else return -1;
	}

	public int getHeaderCount() {
		return mViewModel.headers.size();
	}

	public int getLookupCount() {
		return mViewModel.lookups.size();
	}

	public int getFooterCount() {
		return mViewModel.footers.size();
	}

	public int getHeaderPosition(int recyclerPosition) {
		return recyclerPosition;
	}

	public int getLookupPosition(int recyclerPosition) {
		return recyclerPosition - getHeaderCount();
	}

	public int getFooterPosition(int recyclerPosition) {
		return recyclerPosition - getHeaderCount() - getLookupCount();
	}

	public int getRecyclerPositionByHeader(int headerPosition) {
		return headerPosition;
	}

	public int getRecyclerPositionByLookup(int lookupPosition) {
		return lookupPosition + getHeaderCount();
	}

	public int getRecyclerPositionByFooter(int footerPosition) {
		return footerPosition + getLookupCount() + getHeaderCount();
	}

	public void stop() {
		// TODO: stop image loader
	}

	public static final class HeaderViewHolder extends RecyclerView.ViewHolder {
		private FragmentStockListHeaderBinding mBinding;

		public HeaderViewHolder(FragmentStockListHeaderBinding binding) {
			super(binding.getRoot());
			mBinding = binding;
		}

		public void bindData(StockListView view, String data) {
			mBinding.setView(view);
			mBinding.setData(data);
			mBinding.executePendingBindings();
		}
	}

	public static final class LookupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
		private FragmentStockListItemBinding mBinding;
		private StockListView mView;

		public LookupViewHolder(FragmentStockListItemBinding binding, StockListView view) {
			super(binding.getRoot());
			mBinding = binding;
			mView = view;

			// set listener
			itemView.setOnClickListener(this);
			itemView.setOnLongClickListener(this);
		}

		@Override
		public void onClick(View view) {
			int position = getAdapterPosition();
			if (position != RecyclerView.NO_POSITION) {
				mView.onItemClick(view, position, getItemId(), getItemViewType());
			}
		}

		@Override
		public boolean onLongClick(View view) {
			int position = getAdapterPosition();
			if (position != RecyclerView.NO_POSITION) {
				mView.onItemLongClick(view, position, getItemId(), getItemViewType());
			}
			return true;
		}

		public void bindData(StockListView view, LookupEntity data) {
			mBinding.setView(view);
			mBinding.setData(data);
			mBinding.executePendingBindings();
		}
	}

	public static final class FooterViewHolder extends RecyclerView.ViewHolder {
		FragmentStockListFooterBinding mBinding;

		public FooterViewHolder(FragmentStockListFooterBinding binding) {
			super(binding.getRoot());
			mBinding = binding;
		}

		public void bindData(StockListView view, Object data) {
			mBinding.setView(view);
			mBinding.setData(data);
			mBinding.executePendingBindings();
		}
	}
}
