package com.jky.baidmapdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.jky.baidmapdemo.R;
import com.jky.baidmapdemo.util.Constants;

/**
 * 自定义OverlayMarker map 
 * @author 郑好
 *
 * 2014年9月29日
 */
public class OverlayMarkerMapActivity extends Activity {

	private MapView mapView;
	private BaiduMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.overlay_marker_map);
		
		mapView = (MapView) this.findViewById(R.id.mapView);
		map = mapView.getMap();
		
		//改变地图的缩放比例
		MapStatus status = new  MapStatus.Builder().zoom(16).build();
		MapStatusUpdate newMapStatus = MapStatusUpdateFactory.newMapStatus(status);
		//以动画方式更新地图状态，动画耗时 300 ms
		map.animateMapStatus(newMapStatus);
		
		initListener();
	}
	
	private void initListener() {
		//设置地图 Marker 覆盖物点击事件监听者
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
					
					//信息窗口点击事件处理函数
					@Override
					public void onInfoWindowClick() {
						//隐藏弹出来的窗体
						map.hideInfoWindow();
					}
				};
				
				Button button = new Button(getApplicationContext());
				//设置背景图片
				button.setBackgroundResource(R.drawable.popup);
				//设置文字的显示
				button.setText(marker.getTitle());
				//在地图中显示一个信息窗口，可以设置一个View作为该窗口的内容，
				//也可以设置一个 BitmapDescriptor 作为该窗口的内容。
				InfoWindow infoWindow = new InfoWindow(
						BitmapDescriptorFactory.fromView(button), //InfoWindow 展示的bitmap
						marker.getPosition(), //显示的地理位置
						-47, //Y 轴偏移量
						listener); // InfoWindow 点击监听者
				
				//显示 InfoWindow
				map.showInfoWindow(infoWindow);
				return false;
			}
		});
	}

	/**
	 * 添加marker覆盖物
	 * @param view
	 */
	public void add(View view){
		//大三元
		LatLng dsyLatLng = new LatLng(Constants.DSY_LATITUDE, Constants.DSY_LONGITUDE);
		createMarkerOptions(R.drawable.icon_marka, dsyLatLng, "大三元");
		
		//老舍茶馆
		LatLng lscgLatLng = new LatLng(Constants.LSCG_LATITUDE, Constants.LSCG_LONGITUDE);
		createMarkerOptions(R.drawable.icon_markb, lscgLatLng, "老舍茶馆");
		
		//前门烤鸭店
		LatLng qmkyLatLng = new LatLng(Constants.QMKYD_LATITUDE, Constants.QMKYD_LONGITUDE);
		createMarkerOptions(R.drawable.icon_markc, qmkyLatLng, "前门烤鸭店");
		
		//全聚德
		LatLng qjdLatLng = new LatLng(Constants.QJD_LATITUDE, Constants.QJD_LONGITUDE);
		createMarkerOptions(R.drawable.icon_markd, qjdLatLng, "全聚德");
	}
	
	/**
	 * 创建markeroptions
	 */
	private void createMarkerOptions(int icon_Id, LatLng latLng, String title) {
		MarkerOptions markerOptions = new MarkerOptions();
		//设置 Marker 覆盖物的图标，相同图案的 icon 的 marker 
		//最好使用同一个 BitmapDescriptor 对象以节省内存空间。
		//根据资源 Id 创建 bitmap 描述信息
		BitmapDescriptor aResource = BitmapDescriptorFactory.fromResource(icon_Id);
		markerOptions.icon(aResource);
		
		//设置 marker 覆盖物的位置坐标
		markerOptions.position(latLng);
		
		//设置 marker 覆盖物的标题
		markerOptions.title(title);
		
		map.addOverlay(markerOptions);
		//刷新
	}

	public void clear(View view){
		map.clear();
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
