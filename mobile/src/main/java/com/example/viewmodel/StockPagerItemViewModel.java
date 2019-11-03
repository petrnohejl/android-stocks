package com.example.viewmodel;

import androidx.databinding.BaseObservable;

import com.example.StocksConfig;
import com.example.entity.LookupEntity;

public class StockPagerItemViewModel extends BaseObservable {
	public final LookupEntity lookup;

	public StockPagerItemViewModel(LookupEntity lookup) {
		this.lookup = lookup;
	}

	public String getChartUrl() {
		return String.format(StocksConfig.CHART_BASE_URL, lookup.getSymbol());
	}
}
