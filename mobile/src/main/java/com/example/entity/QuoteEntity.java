package com.example.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class QuoteEntity extends ErrorEntity {
	public static final String STATUS_SUCCESS = "SUCCESS";

	@SerializedName("Status")
	@Expose
	private String status;
	@SerializedName("Name")
	@Expose
	private String name;
	@SerializedName("Symbol")
	@Expose
	private String symbol;
	@SerializedName("LastPrice")
	@Expose
	private double lastPrice;
	@SerializedName("Change")
	@Expose
	private double change;
	@SerializedName("ChangePercent")
	@Expose
	private double changePercent;
	@SerializedName("Timestamp")
	@Expose
	private Date timestamp;
	@SerializedName("MSDate")
	@Expose
	private double msDate;
	@SerializedName("MarketCap")
	@Expose
	private long marketCap;
	@SerializedName("Volume")
	@Expose
	private long volume;
	@SerializedName("ChangeYTD")
	@Expose
	private double changeYTD;
	@SerializedName("ChangePercentYTD")
	@Expose
	private double changePercentYTD;
	@SerializedName("High")
	@Expose
	private double high;
	@SerializedName("Low")
	@Expose
	private double low;
	@SerializedName("Open")
	@Expose
	private double open;

	public QuoteEntity() {
	}

	public boolean isSuccess() {
		return STATUS_SUCCESS.equals(getStatus());
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public double getChangePercent() {
		return changePercent;
	}

	public void setChangePercent(double changePercent) {
		this.changePercent = changePercent;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getMSDate() {
		return msDate;
	}

	public void setMSDate(double msDate) {
		this.msDate = msDate;
	}

	public long getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(long marketCap) {
		this.marketCap = marketCap;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public double getChangeYTD() {
		return changeYTD;
	}

	public void setChangeYTD(double changeYTD) {
		this.changeYTD = changeYTD;
	}

	public double getChangePercentYTD() {
		return changePercentYTD;
	}

	public void setChangePercentYTD(double changePercentYTD) {
		this.changePercentYTD = changePercentYTD;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}
}
