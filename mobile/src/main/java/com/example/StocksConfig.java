package com.example;

public class StocksConfig {
	public static final boolean LOGS = BuildConfig.LOGS;
	public static final boolean DEV_ENVIRONMENT = BuildConfig.DEV_ENVIRONMENT;

	public static final String REST_BASE_URL_PROD = "http://dev.markitondemand.com/MODApis/Api/v2/";
	public static final String REST_BASE_URL_DEV = "http://dev.markitondemand.com/MODApis/Api/v2/";
	public static final String REST_BASE_URL = StocksConfig.DEV_ENVIRONMENT ? StocksConfig.REST_BASE_URL_DEV : StocksConfig.REST_BASE_URL_PROD;

	public static final String CHART_BASE_URL = "http://finviz.com/chart.ashx?t=%s&ty=c&ta=0&p=m&s=l";
}
