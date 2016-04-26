package com.example.rest;

import com.example.StocksConfig;
import com.example.utility.Logcat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public final class RetrofitClient
{
	private static volatile Retrofit sRetrofit;


	private RetrofitClient() {}


	public static Retrofit getRetrofit()
	{
		if(sRetrofit == null)
		{
			synchronized(RetrofitClient.class)
			{
				if(sRetrofit == null)
				{
					sRetrofit = buildRetrofit();
				}
			}
		}
		return sRetrofit;
	}


	public static <T> T createService(Class<T> service)
	{
		return getRetrofit().create(service);
	}


	private static Retrofit buildRetrofit()
	{
		Retrofit.Builder builder = new Retrofit.Builder();
		builder.baseUrl(StocksConfig.REST_DEV ? StocksConfig.REST_BASE_URL_DEV : StocksConfig.REST_BASE_URL_PROD);
		builder.client(buildClient());
		builder.addConverterFactory(createConverterFactory());
		return builder.build();
	}


	private static OkHttpClient buildClient()
	{
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.addInterceptor(createLoggingInterceptor());
		return builder.build();
	}


	private static Interceptor createLoggingInterceptor()
	{
		HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger()
		{
			@Override
			public void log(String message)
			{
				Logcat.d(message);
			}
		};
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(logger);
		interceptor.setLevel(StocksConfig.LOGS ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
		return interceptor;
	}


	private static Converter.Factory createConverterFactory()
	{
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("EEE MMM d HH:mm:ss 'UTC'zzzzz yyyy");
		Gson gson = builder.create();
		return GsonConverterFactory.create(gson);
	}
}
