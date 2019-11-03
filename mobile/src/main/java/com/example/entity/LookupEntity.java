package com.example.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LookupEntity extends BaseObservable {
	@Bindable
	@SerializedName("Symbol")
	@Expose
	private String symbol;

	@Bindable
	@SerializedName("Name")
	@Expose
	private String name;

	@Bindable
	@SerializedName("Exchange")
	@Expose
	private String exchange;

	public LookupEntity() {
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
		notifyPropertyChanged(BR.symbol);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		notifyPropertyChanged(BR.name);
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
		notifyPropertyChanged(BR.exchange);
	}
}
