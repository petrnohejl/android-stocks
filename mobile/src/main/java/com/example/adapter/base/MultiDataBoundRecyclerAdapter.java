package com.example.adapter.base;

import android.databinding.ObservableArrayList;

import com.example.BR;
import com.example.ui.BaseView;

import java.util.List;


abstract public class MultiDataBoundRecyclerAdapter extends BaseDataBoundRecyclerAdapter
{
	private BaseView mView;
	private ObservableArrayList<?>[] mItems;


	public MultiDataBoundRecyclerAdapter(BaseView view, ObservableArrayList<?>... items)
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
		for(ObservableArrayList<?> list : mItems)
		{
			size += list.size();
		}
		return size;
	}


	public Object getItem(int position)
	{
		int counter = 0;
		for(ObservableArrayList<?> list : mItems)
		{
			if(position - counter - list.size() < 0)
			{
				int index = position - counter;
				return list.get(index);
			}
			counter += list.size();
		}
		return null;
	}
}
