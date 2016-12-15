package com.example.utility;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.R;
import com.example.StocksConfig;
import com.example.graphics.CircularDrawable;

import org.alfonz.utility.Logcat;


public final class GlideUtility
{
	private GlideUtility() {}


	public static void setupRequestBuilder(GenericRequestBuilder builder, Drawable placeholder, Drawable error)
	{
		builder.diskCacheStrategy(DiskCacheStrategy.RESULT);

		if(builder instanceof DrawableRequestBuilder) ((DrawableRequestBuilder) builder).crossFade();

		if(StocksConfig.LOGS) builder.listener(createLogRequestListener());

		if(placeholder != null) builder.placeholder(placeholder);

		if(error != null) builder.error(error);
		else builder.error(R.drawable.placeholder);
	}


	public static Target createCircularTarget(ImageView imageView)
	{
		return new BitmapImageViewTarget(imageView)
		{
			@Override
			protected void setResource(Bitmap resource)
			{
				CircularDrawable drawable = new CircularDrawable(resource);
				imageView.setImageDrawable(drawable);
			}
		};
	}


	private static RequestListener createLogRequestListener()
	{
		return new RequestListener()
		{
			@Override
			public boolean onException(Exception e, Object model, Target target, boolean isFirstResource)
			{
				Logcat.d("%s / %s / isFirstResource=%s", e, model, isFirstResource);
				e.printStackTrace();
				return false;
			}


			@Override
			public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource)
			{
				Logcat.d("%s / isFromMemoryCache=%s / isFirstResource=%s", model, isFromMemoryCache, isFirstResource);
				return false;
			}
		};
	}
}
