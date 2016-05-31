package com.example.adapter.base;

import android.databinding.ObservableArrayList;

import com.example.BR;
import com.example.ui.BaseView;

import java.util.List;


abstract public class MultiDataBoundAdapter extends BaseDataBoundAdapter
{
	private BaseView mView;
	private ObservableArrayList<Object> mItems1;
	private ObservableArrayList<Object> mItems2;
	private ObservableArrayList<Object> mItems3;


	public MultiDataBoundAdapter(BaseView view, ObservableArrayList<Object> items1)
	{
		this(view, items1, null, null);
	}


	public MultiDataBoundAdapter(BaseView view, ObservableArrayList<Object> items1, ObservableArrayList<Object> items2)
	{
		this(view, items1, items2, null);
	}


	public MultiDataBoundAdapter(BaseView view, ObservableArrayList<Object> items1, ObservableArrayList<Object> items2, ObservableArrayList<Object> items3)
	{
		mView = view;
		mItems1 = items1;
		mItems2 = items2;
		mItems3 = items3;
	}


	@Override
	protected void bindItem(DataBoundViewHolder holder, int position, List payloads)
	{
		holder.binding.setVariable(BR.view, mView);
		holder.binding.setVariable(BR.data, getItem(position));
	}


	@Override
	public int getItemCount()
	{
		int size = 0;
		if(mItems1 != null) size += mItems1.size();
		if(mItems2 != null) size += mItems2.size();
		if(mItems3 != null) size += mItems3.size();
		return size;
	}


	public Object getItem(int position)
	{
		int size1 = mItems1 != null ? mItems1.size() : 0;
		int size2 = mItems2 != null ? mItems2.size() : 0;
		int size3 = mItems3 != null ? mItems3.size() : 0;

		if(mItems1 != null && position < size1) return mItems1.get(position);
		else if(mItems2 != null && position < size1 + size2) return mItems2.get(position - size1);
		else if(mItems3 != null && position < size1 + size2 + size3) return mItems3.get(position - size1 - size2);
		else return null;
	}
}
