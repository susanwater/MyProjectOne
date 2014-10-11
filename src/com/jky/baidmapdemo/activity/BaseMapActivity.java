package com.jky.baidmapdemo.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.SnapshotReadyCallback;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.jky.baidmapdemo.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 基本地图
 * @author 郑好
 *
 * 2014年9月29日
 */
public class BaseMapActivity extends Activity implements OnClickListener {

	private MapView mapView;
	private RadioButton rb_normal, rb_satellite;
	private CheckBox cb_traffic, cb_look_down, cb_compass;
	private Button bn_capture;
	private BaiduMap map;
	private TextView tv_rotate;
	private int angle = 0;
	private UiSettings uiSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.base_map);
		
		initView();
		initListener();
	}
	
	private void initView() {
		mapView = (MapView) this.findViewById(R.id.mapView);
		rb_normal = (RadioButton) this.findViewById(R.id.rb_normal);
		rb_satellite = (RadioButton) this.findViewById(R.id.rb_satellite);
		cb_traffic = (CheckBox) this.findViewById(R.id.cb_traffic);
		bn_capture = (Button) this.findViewById(R.id.bn_capture);
		tv_rotate = (TextView) this.findViewById(R.id.tv_rotate);
		cb_look_down = (CheckBox) this.findViewById(R.id.cb_look_down);
		cb_compass = (CheckBox) this.findViewById(R.id.cb_compass);
		
		map = mapView.getMap();
		//获取地图ui控制器
		uiSettings = map.getUiSettings();
		
		map.setOnMapLoadedCallback(new OnMapLoadedCallback() {
			
			//地图加载完成回调函数
			@Override
			public void onMapLoaded() {
				//指南针的位置
				Point point = new Point(50, 100);
				//设置指南针的位置，在 onMapLoadFinish 后生效
				uiSettings.setCompassPosition(point);
				uiSettings.setCompassEnabled(false);
			}
		});
		
	}

	private void initListener() {
		rb_normal.setOnClickListener(this);
		rb_satellite.setOnClickListener(this);
		bn_capture.setOnClickListener(this);
		tv_rotate.setOnClickListener(this);
		
		//显示交通图
		cb_traffic.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//设置是否打开交通图层
				if (isChecked) {
					map.setTrafficEnabled(true);
				}else{
					map.setTrafficEnabled(false);
				}
			}
		});
		
		//俯视
		cb_look_down.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					cb_look_down.setText("正常");
					//俯视的角度 -45~0  缩放级别  3~19  0-360
					MapStatus status = new  MapStatus.Builder().overlook(-45).build();
					MapStatusUpdate newMapStatus = MapStatusUpdateFactory.newMapStatus(status);
					//以动画方式更新地图状态，动画耗时 300 ms
					map.animateMapStatus(newMapStatus);
				}else{
					cb_look_down.setText("俯视");
					//俯视的角度 -45~0  缩放级别  3~19
					MapStatus status = new  MapStatus.Builder().overlook(0).build();
					MapStatusUpdate newMapStatus = MapStatusUpdateFactory.newMapStatus(status);
					//以动画方式更新地图状态，动画耗时 300 ms
					map.animateMapStatus(newMapStatus);
				}
			}
		});
		
		//指南针
		cb_compass.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					//显示指南针设置是否允许指南针
					uiSettings.setCompassEnabled(true);
				}else{
					uiSettings.setCompassEnabled(false);
				}
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//设置地图类型 MAP_TYPE_NORMAL 普通图； MAP_TYPE_SATELLITE 卫星图
		//设置成普通地图
		case R.id.rb_normal:
			map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			break;
		//设置成卫星图
		case R.id.rb_satellite:
			map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			break;
		//截图
		case R.id.bn_capture:
			//地图截屏回调接口
			map.snapshot(new SnapshotReadyCallback() {
				
				//地图截屏回调接口
				@Override
				public void onSnapshotReady(Bitmap snapshot) {
					try {
						//保存到SDCard
						File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".png");
						FileOutputStream fos = new FileOutputStream(file);
						//指定格式去压缩文件保存指定的格式
						if (snapshot.compress(
								CompressFormat.PNG, //指定输出格式
								100, //图压缩质量 0-100 100最好
								fos)) {
							fos.flush();
							fos.close();
							Toast.makeText(getApplicationContext(), "图片保存成功!", Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "图片保存失败!", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				}
			});
			break;
		//旋转地图
		case R.id.tv_rotate:
			angle += 30;
			MapStatus status = new  MapStatus.Builder().rotate(angle).build();
			MapStatusUpdate newMapStatus = MapStatusUpdateFactory.newMapStatus(status);
			//以动画方式更新地图状态，动画耗时 300 ms
			map.animateMapStatus(newMapStatus);
			break;
		}
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
