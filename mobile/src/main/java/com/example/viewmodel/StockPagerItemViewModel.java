package com.example.viewmodel;

import com.example.StocksConfig;
import com.example.entity.LookupEntity;

import androidx.databinding.BaseObservable;

public class StockPagerItemViewModel extends BaseObservable {
	public final LookupEntity lookup;

	public StockPagerItemViewModel(LookupEntity lookup) {
		this.lookup = lookup;
	}

	public String getChartUrl() {
		return String.format(StocksConfig.CHART_BASE_URL, lookup.getSymbol());
	}
}
