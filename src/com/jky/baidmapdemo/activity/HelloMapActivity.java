package com.jky.baidmapdemo.activity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.jky.baidmapdemo.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * hello map
 * @author 郑好
 *
 * 2014年9月29日
 */
public class HelloMapActivity extends Activity {

	private MapView mapView;
	private BaiduMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.hello_map);
		
		mapView = (MapView) this.findViewById(R.id.mapView);
		map = mapView.getMap();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
}
