package com.example.rest.router;

import com.example.entity.LookupEntity;
import com.example.entity.QuoteEntity;
import com.example.rest.RetrofitClient;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class StocksRouter {
	public static final String QUOTE_CALL_TYPE = "quote";
	public static final String LOOKUP_CALL_TYPE = "lookup";

	private static volatile StocksService sService;

	public interface StocksService {
		@GET("Quote/{format}")
		Call<QuoteEntity> quote(@Path("format") String format, @Query("symbol") String symbol);

		@GET("Lookup/{format}")
		Call<LookupEntity[]> lookup(@Path("format") String format, @Query("input") String input);
	}

	private StocksRouter() {}

	public static StocksService getService() {
		if (sService == null) {
			synchronized (StocksRouter.class) {
				if (sService == null) {
					sService = RetrofitClient.createService(StocksService.class);
				}
			}
		}
		return sService;
	}
}
