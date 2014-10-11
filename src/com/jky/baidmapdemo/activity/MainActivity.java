package com.jky.baidmapdemo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.jky.baidmapdemo.R;

public class MainActivity extends Activity {

	private MyBaiduReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	/**
	 * hellomap
	 * @param view
	 */
	public void helloMap(View view){
		startActivity(new Intent(this, HelloMapActivity.class));
	}
	
	/**
	 * 基本地图
	 * @param view
	 */
	public void baseMap(View view){
		startActivity(new Intent(this, BaseMapActivity.class));
	}
	
	/**
	 * 添加自定义的覆盖物
	 * @param view
	 */
	public void overlayMap(View view){
		startActivity(new Intent(this, OverlayMapActivity.class));
	}
	
	/**
	 * 自定义marker 覆盖物
	 * @param view
	 */
	public void overlayMarkerMap(View view){
		startActivity(new Intent(this, OverlayMarkerMapActivity.class));
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		//注册Baidumap的广播监听
		receiver = new MyBaiduReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		filter.addAction(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE);
		this.registerReceiver(receiver, filter);
	}
	
	/**
	 * 当连接百度服务器的时候 
	 * 如果网络或者key出现错误给用户提示一下
	 * @author 郑好
	 *
	 * 2014年9月29日
	 */
	private class MyBaiduReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			//网络错误广播 action string
			if (intent.getAction().equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				Toast.makeText(getApplicationContext(), "网络错误!", Toast.LENGTH_SHORT).show();
			}
			//key 验证失败广播 action string
			else if (intent.getAction().equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				Toast.makeText(getApplicationContext(), "key 验证失败!", Toast.LENGTH_SHORT).show();
			}
			//key 验证失败广播 intent 中附加信息错误码键
			else if (intent.getAction().equals(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)) {
				Toast.makeText(getApplicationContext(), "key 验证失败 包含错误信息!", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//取消注册
		unregisterReceiver(receiver);
	}

}
