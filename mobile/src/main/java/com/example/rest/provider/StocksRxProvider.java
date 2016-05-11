package com.example.rest.provider;

import com.example.entity.LookupEntity;
import com.example.entity.QuoteEntity;
import com.example.rest.RetrofitClient;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public class StocksRxProvider
{
	public static final String QUOTE_CALL_TYPE = "quote";
	public static final String LOOKUP_CALL_TYPE = "lookup";

	private static volatile StocksService sService;


	public interface StocksService
	{
		@GET("Quote/{format}")
		Observable<Response<QuoteEntity>> quote(@Path("format") String format, @Query("symbol") String symbol);

		@GET("Lookup/{format}")
		Observable<Response<List<LookupEntity>>> lookup(@Path("format") String format, @Query("input") String input);
	}


	private StocksRxProvider() {}


	public static StocksService getService()
	{
		if(sService == null)
		{
			synchronized(StocksRxProvider.class)
			{
				if(sService == null)
				{
					sService = RetrofitClient.createService(StocksService.class);
				}
			}
		}
		return sService;
	}
}
