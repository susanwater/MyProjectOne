package com.jky.baidmapdemo;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class BaidMapApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		//初始化百度SDK
		SDKInitializer.initialize(getApplicationContext());
	}
}
