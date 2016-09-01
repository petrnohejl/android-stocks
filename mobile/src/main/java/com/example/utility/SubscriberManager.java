package com.example.utility;

import com.android.annotations.NonNull;
import com.example.rest.RetrofitHttpException;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


@SuppressWarnings({"WeakerAccess", "unused"})
public class SubscriberManager
{
	private static SubscriberManager sInstance;
	private Map<Class, RegisteredEntity> mRegisteredItems = new HashMap<>();


	@SuppressWarnings("WeakerAccess")
	public SubscriberManager()
	{
	}


	@SuppressWarnings("WeakerAccess")
	public static SubscriberManager getInstance()
	{
		if(sInstance == null)
			sInstance = new SubscriberManager();
		return sInstance;
	}


	public static void unSubscribe(Class c)
	{
		Logcat.v("UnSubscribe: " + c.getName());
		CompositeSubscription composite = getInstance().mRegisteredItems.get(c).getCompositor();
		if(composite != null)
		{
			composite.unsubscribe();
			getInstance().mRegisteredItems.remove(c);
		}
	}


	public static void subscribe(Class c, Subscription subscription)
	{
		Logcat.v("Subscribe: " + c.getName());
		RegisteredEntity registeredEntity = getInstance().mRegisteredItems.get(c);
		if(registeredEntity == null)
		{
			registeredEntity = new RegisteredEntity();
			registeredEntity.getCompositor().add(subscription);
			getInstance().mRegisteredItems.put(c, registeredEntity);
		}
		else
		{
			registeredEntity.getCompositor().add(subscription);
		}
	}


	@RxLogObservable
	public static <T extends Response<?>> Observable<T> createSubscribedObservable(Observable<T> restObservable, final String callType, Class c)
	{
		return Observable.create(subscriber -> {
			Subscription subscription = restObservable
					.flatMap(RxUtility::catchHttpError)
					.compose(RxUtility.applySchedulers())
					.doOnSubscribe(() -> registerCall(c, callType))
					.doOnNext(t -> {
						logSuccess(t, callType);
						if(!subscriber.isUnsubscribed())
							subscriber.onNext(t);
					})
					.doOnError(throwable -> {
						// log
						if(throwable instanceof RetrofitHttpException)
						{
							logError((RetrofitHttpException) throwable, callType);
						}
						else
						{
							logFail(throwable, callType);
						}

						if(!subscriber.isUnsubscribed())
							subscriber.onError(throwable);
						unregisterCall(c, callType);
					})
					.doOnCompleted(() -> {
						if(!subscriber.isUnsubscribed())
							subscriber.onCompleted();
						unregisterCall(c, callType);
					})
					.subscribe(subscriber);
			subscribe(c, subscription);
			subscribe(c, subscriber);
		});
	}


	public static boolean isCallRegistered(Class c, String callType)
	{
		RegisteredEntity registeredEntity = getInstance().mRegisteredItems.get(c);
		return registeredEntity != null && registeredEntity.getCallTypes().contains(callType);
	}


	private static void registerCall(Class c, String callType)
	{
		Logcat.v("Registered call type: " + callType);
		RegisteredEntity registeredEntity = getInstance().mRegisteredItems.get(c);
		if(registeredEntity != null)
			registeredEntity.getCallTypes().add(callType);
	}


	private static void unregisterCall(Class c, String callType)
	{
		Logcat.v("Unregistered call type: " + callType);
		RegisteredEntity registeredEntity = getInstance().mRegisteredItems.get(c);
		if(registeredEntity != null)
			registeredEntity.getCallTypes().remove(callType);
	}


	private static void logSuccess(Response<?> response, String callType)
	{
		String status = response.code() + " " + response.message();
		String result = response.body() != null ? response.body().getClass().getSimpleName() : "empty body";
		Logcat.d("%s call succeed with %s: %s", callType, status, result);
	}


	private static void logError(RetrofitHttpException exception, String callType)
	{
		String status = exception.code() + " " + exception.message();
		String result = RestUtility.getErrorMessage(exception);
		Logcat.d("%s call err with %s: %s", callType, status, result);
	}


	private static void logFail(Throwable throwable, String callType)
	{
		String exceptionName = throwable.getClass().getSimpleName();
		String exceptionMessage = throwable.getMessage();
		Logcat.d("%s call fail with %s exception: %s", callType, exceptionName, exceptionMessage);
	}


	private static class RegisteredEntity
	{
		@NonNull
		private CompositeSubscription mCompositor;
		@NonNull
		private List<String> mCallTypes;


		public RegisteredEntity()
		{
			mCompositor = new CompositeSubscription();
			mCallTypes = new ArrayList<>();
		}


		public CompositeSubscription getCompositor()
		{
			return mCompositor;
		}


		public void setCompositor(CompositeSubscription compositor)
		{
			mCompositor = compositor;
		}


		public List<String> getCallTypes()
		{
			return mCallTypes;
		}


		public void setCallTypes(List<String> callTypes)
		{
			mCallTypes = callTypes;
		}
	}
}
