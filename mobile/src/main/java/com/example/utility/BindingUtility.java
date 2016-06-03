package com.example.utility;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.R;
import com.example.graphics.CircularDrawable;
import com.example.widget.LinearDividerItemDecoration;


public final class BindingUtility
{
	public enum RecyclerLayout { LINEAR_VERTICAL }
	public enum RecyclerDecoration { LINEAR_DIVIDER }
	public enum RecyclerAnimator { DEFAULT }


	private BindingUtility() {}


	@BindingAdapter({"onClick"})
	public static void setOnClick(View view, View.OnClickListener listener)
	{
		view.setOnClickListener(listener);
	}


	@BindingAdapter({"onLongClick"})
	public static void setOnLongClick(View view, View.OnLongClickListener listener)
	{
		view.setOnLongClickListener(listener);
	}


	@BindingAdapter({"recyclerLayout"})
	public static void setRecyclerLayout(RecyclerView recyclerView, RecyclerLayout recyclerLayout)
	{
		RecyclerView.LayoutManager layoutManager;

		if(recyclerLayout == RecyclerLayout.LINEAR_VERTICAL)
		{
			LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
			linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
			layoutManager = linearLayoutManager;
		}
		else
		{
			throw new IllegalArgumentException();
		}

		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
	}


	@BindingAdapter({"recyclerDecoration"})
	public static void setRecyclerDecoration(RecyclerView recyclerView, RecyclerDecoration recyclerDecoration)
	{
		RecyclerView.ItemDecoration itemDecoration;

		if(recyclerDecoration == RecyclerDecoration.LINEAR_DIVIDER)
		{
			itemDecoration = new LinearDividerItemDecoration(recyclerView.getContext(), null);
		}
		else
		{
			throw new IllegalArgumentException();
		}

		recyclerView.addItemDecoration(itemDecoration);
	}


	@BindingAdapter({"recyclerAnimator"})
	public static void setRecyclerAnimator(RecyclerView recyclerView, RecyclerAnimator recyclerAnimator)
	{
		RecyclerView.ItemAnimator itemAnimator;

		if(recyclerAnimator == RecyclerAnimator.DEFAULT)
		{
			itemAnimator = new DefaultItemAnimator();
		}
		else
		{
			throw new IllegalArgumentException();
		}

		recyclerView.setItemAnimator(itemAnimator);
	}


	@BindingAdapter({"imageUrl"})
	public static void setImageUrl(ImageView imageView, String url)
	{
		Glide
				.with(imageView.getContext())
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.RESULT)
				.crossFade()
				.error(R.drawable.placeholder)
				.into(imageView);
	}


	@BindingAdapter(value = {"imageUrl", "imageCircular", "imagePlaceholder", "imageError"}, requireAll = false)
	public static void loadImage(ImageView imageView, String url, boolean circular, Drawable placeholder, Drawable error)
	{
		RequestManager requestManager = Glide.with(imageView.getContext());
		if(!circular)
		{
			DrawableTypeRequest builder = requestManager.load(url);
			setupGlideRequestBuilder(builder, placeholder, error);
			builder.into(imageView);
		}
		else
		{
			BitmapTypeRequest builder = requestManager.load(url).asBitmap();
			setupGlideRequestBuilder(builder, placeholder, error);
			builder.into(getGlideCircularTarget(imageView));
		}
	}


	private static void setupGlideRequestBuilder(GenericRequestBuilder builder, Drawable placeholder, Drawable error)
	{
		builder.diskCacheStrategy(DiskCacheStrategy.RESULT);

		if(builder instanceof DrawableRequestBuilder) ((DrawableRequestBuilder) builder).crossFade();

		if(placeholder != null) builder.placeholder(placeholder);

		if(error != null) builder.error(error);
		else builder.error(R.drawable.placeholder);
	}


	private static Target getGlideCircularTarget(ImageView imageView)
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
}
