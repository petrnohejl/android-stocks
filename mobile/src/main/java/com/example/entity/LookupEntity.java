package com.example.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LookupEntity extends ErrorEntity
{
	@SerializedName("Symbol")
	@Expose
	private String symbol;
	@SerializedName("Name")
	@Expose
	private String name;
	@SerializedName("Exchange")
	@Expose
	private String exchange;


	public LookupEntity()
	{
	}


	public String getSymbol()
	{
		return symbol;
	}


	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public String getExchange()
	{
		return exchange;
	}


	public void setExchange(String exchange)
	{
		this.exchange = exchange;
	}
}
