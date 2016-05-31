package com.example.adapter.base;

import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;

import com.example.BR;
import com.example.ui.BaseView;

import java.util.List;


abstract public class SimpleDataBoundAdapter<T extends ViewDataBinding> extends BaseDataBoundAdapter<T>
{
	@LayoutRes private int mLayoutId;
	private BaseView mView;
	private ObservableArrayList<Object> mItems;


	public SimpleDataBoundAdapter(@LayoutRes int layoutId, BaseView view, ObservableArrayList<Object> items)
	{
		mLayoutId = layoutId;
		mView = view;
		mItems = items;
	}


	@Override
	protected void bindItem(DataBoundViewHolder holder, int position, List payloads)
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
