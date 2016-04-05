package com.example.entity;

import android.os.Parcel;
import android.os.Parcelable;


public class StockEntity implements Parcelable
{
	private long id;
	private String name;


	public static final Parcelable.Creator<StockEntity> CREATOR = new Parcelable.Creator<StockEntity>()
	{
		public StockEntity createFromParcel(Parcel parcel)
		{
			return new StockEntity(parcel);
		}


		public StockEntity[] newArray(int size)
		{
			return new StockEntity[size];
		}
	};


	// empty constructor
	public StockEntity()
	{
	}


	// parcel constructor
	public StockEntity(Parcel parcel)
	{
		readFromParcel(parcel);
	}


	// copy constructor
	public StockEntity(StockEntity origin)
	{
		id = origin.id;
		if(origin.name!=null) name = new String(origin.name);
	}


	@Override
	public int describeContents()
	{
		return 0;
	}


	@Override
	public void writeToParcel(Parcel parcel, int flags)
	{
		// order is important
		parcel.writeLong(id);
		parcel.writeString(name);
	}


	private void readFromParcel(Parcel parcel)
	{
		// order is important
		id = parcel.readLong();
		name = parcel.readString();
	}


	public long getId()
	{
		return id;
	}


	public void setId(long id)
	{
		this.id = id;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}
}
