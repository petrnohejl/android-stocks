package com.example;

import com.example.entity.LookupEntity;
import com.example.entity.QuoteEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {
	private Gson mGson;

	public ParserTest() {
		mGson = new GsonBuilder().setDateFormat("EEE MMM d HH:mm:ss 'UTC'XXX yyyy").create();
	}

	@Test
	public void parseQuoteEntity() throws Exception {
		String json = "{\"Status\":\"SUCCESS\",\"Name\":\"Intel Corp\",\"Symbol\":\"INTC\",\"LastPrice\":31.62,\"Change\":0.0700000000000003,\"ChangePercent\":0.221870047543583,\"Timestamp\":\"Fri Apr 8 15:59:00 UTC-04:00 2016\",\"MSDate\":42468.6659722222,\"MarketCap\":149164219620,\"Volume\":1213653,\"ChangeYTD\":34.45,\"ChangePercentYTD\":-8.21480406386067,\"High\":32.01,\"Low\":31.48,\"Open\":31.77}";
		QuoteEntity quote = mGson.fromJson(json, QuoteEntity.class);
		assertEquals(quote.getSymbol(), "INTC");
		assertEquals(quote.getTimestamp().getTime(), 1460145540000L);
	}

	@Test
	public void parseLookupEntity() throws Exception {
		String json = "[{\"Symbol\":\"UBSH\",\"Name\":\"Union Bankshares Corp\",\"Exchange\":\"NASDAQ\"},{\"Symbol\":\"UNP\",\"Name\":\"Union Pacific Corp\",\"Exchange\":\"NYSE\"},{\"Symbol\":\"WU\",\"Name\":\"Western Union Co\",\"Exchange\":\"NYSE\"},{\"Symbol\":\"UNP\",\"Name\":\"Union Pacific Corp\",\"Exchange\":\"BATS Trading Inc\"},{\"Symbol\":\"WU\",\"Name\":\"Western Union Co\",\"Exchange\":\"BATS Trading Inc\"},{\"Symbol\":\"CUBN\",\"Name\":\"Commerce Union Bancshares Inc\",\"Exchange\":\"NASDAQ\"}]";
		LookupEntity[] lookupArray = mGson.fromJson(json, LookupEntity[].class);
		assertEquals(lookupArray.length, 6);
		assertEquals(lookupArray[1].getName(), "Union Pacific Corp");
	}
}
