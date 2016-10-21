package com.streamlet.base;

import android.app.Application;

public class AppApplication extends Application {

	public static final String TAG = "com.brother.yckx";

	private static AppApplication mInstance;

	public static AppApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}



}
