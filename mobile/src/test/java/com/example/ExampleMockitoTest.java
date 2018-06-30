package com.example;

import android.os.Bundle;
import android.view.View;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExampleMockitoTest {
	@Mock private Bundle mBundle;

	@Test
	public void testBundle() {
		Mockito.when(mBundle.getString("id")).thenReturn("val");
		String val = mBundle.getString("id");
		Assert.assertEquals(val, "val");
	}

	@Test
	public void testView() {
		int i = View.GONE;
		int j = View.VISIBLE;
		Assert.assertNotEquals(i, j);
	}
}
