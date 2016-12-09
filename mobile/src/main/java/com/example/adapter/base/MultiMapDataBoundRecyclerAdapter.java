package com.example.adapter.base;

import android.databinding.ObservableArrayMap;

import com.example.BR;
import com.example.ui.BaseView;

import java.util.List;


public abstract class MultiMapDataBoundRecyclerAdapter extends BaseDataBoundRecyclerAdapter
{
	private BaseView mView;
	private ObservableArrayMap<?, ?>[] mItems;


	public MultiMapDataBoundRecyclerAdapter(BaseView view, ObservableArrayMap<?, ?>... items)
	{
		mView = view;
		mItems = items;
	}


	@Override
	protected void bindItem(BaseDataBoundRecyclerViewHolder holder, int position, List payloads)
	{
		holder.binding.setVariable(BR.view, mView);
		holder.binding.setVariable(BR.data, getItem(position));
	}


	@Override
	public int getItemCount()
	{
		int size = 0;
		for(ObservableArrayMap<?, ?> map : mItems)
		{
			size += map.size();
		}
		return size;
	}


	public Object getItem(int position)
	{
		int counter = 0;
		for(ObservableArrayMap<?, ?> map : mItems)
		{
			if(position - counter - map.size() < 0)
			{
				int index = position - counter;
				return map.valueAt(index);
			}
			counter += map.size();
		}
		return null;
	}
}
