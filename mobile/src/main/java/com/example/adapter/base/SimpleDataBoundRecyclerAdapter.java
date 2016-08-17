package com.example.adapter.base;

import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;

import com.example.BR;
import com.example.ui.BaseView;

import java.util.List;


abstract public class SimpleDataBoundRecyclerAdapter<T extends ViewDataBinding> extends BaseDataBoundRecyclerAdapter<T>
{
	@LayoutRes private int mLayoutId;
	private BaseView mView;
	private ObservableArrayList<?> mItems;


	public SimpleDataBoundRecyclerAdapter(@LayoutRes int layoutId, BaseView view, ObservableArrayList<?> items)
	{
		mLayoutId = layoutId;
		mView = view;
		mItems = items;
	}


	@Override
	protected void bindItem(BaseDataBoundRecyclerViewHolder holder, int position, List payloads)
	{
		holder.binding.setVariable(BR.view, mView);
		holder.binding.setVariable(BR.data, mItems.get(position));
	}


	@Override
	public int getItemLayoutId(int position)
	{
		return mLayoutId;
	}


	@Override
	public int getItemCount()
	{
		return mItems.size();
	}
}
