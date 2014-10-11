package com.jky.baidmapdemo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.location.Criteria;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.jky.baidmapdemo.R;
import com.jky.baidmapdemo.util.Constants;

/**
 * 添加覆盖物
 * @author 郑好
 *
 * 2014年9月29日
 */
public class OverlayMapActivity extends Activity {

	private MapView mapView;
	private BaiduMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.overlay_map);
		
		mapView = (MapView) this.findViewById(R.id.mapView);
		map = mapView.getMap();
		
		//改变地图的缩放比例
		MapStatus status = new  MapStatus.Builder().zoom(16).build();
		MapStatusUpdate newMapStatus = MapStatusUpdateFactory.newMapStatus(status);
		//以动画方式更新地图状态，动画耗时 300 ms
		map.animateMapStatus(newMapStatus);
	}
	
	/**
	 * 添加覆盖物
	 * @param view
	 */
	public void add(View view){
		//OverlayOptions
		//1.定义覆盖物
		//2.初始化
		//3.最终添加到地图
		
		//向地图添加一个 Overlay
		//map.addOverlay(arg0)
		//创建一个圆的覆盖物
		createCircleOptions();
		
		//创建折线覆盖物选项类
		createPolylineOptions();
	}
	
	/**
	 * 创建折线覆盖物选项类
	 */
	private void createPolylineOptions() {
		
		PolylineOptions polylineOptions = new PolylineOptions();
		//设置折线颜色
		polylineOptions.color(Color.BLUE);
		//设置折线线宽， 默认为 5， 单位：像素
		polylineOptions.width(5);
		
		List<LatLng> latLngs = new ArrayList<LatLng>();
		
		//1折线坐标起点 北长街小学
		LatLng cbjxuLatLng = new LatLng(Constants.START_LATITUDE, Constants.START_LONGITUDE);
		latLngs.add(cbjxuLatLng);
		createTextOptions("开始", cbjxuLatLng);
		
		//2折线坐标 故宫博物院
		LatLng ggbfgLatLng = new LatLng(Constants.POLYLINE_LATITUDE_1, Constants.POLYLINE_LONGITUDE_1);
		latLngs.add(ggbfgLatLng);
		createTextOptions("故宫博物院", ggbfgLatLng);
		
		//3折线坐标 午门
		LatLng wmLatLng = new LatLng(Constants.POLYLINE_LATITUDE_2, Constants.POLYLINE_LONGITUDE_2);
		latLngs.add(wmLatLng);
		createTextOptions("午门", wmLatLng);
		
		//4折线坐标 常青园
		LatLng cjyLatLng = new LatLng(Constants.POLYLINE_LATITUDE_3, Constants.POLYLINE_LONGITUDE_3);
		latLngs.add(cjyLatLng);
		createTextOptions("常青园", cjyLatLng);
		
		//5折线坐标 普渡寺
		LatLng pdsLatLng = new LatLng(Constants.POLYLINE_LATITUDE_4, Constants.POLYLINE_LONGITUDE_4);
		latLngs.add(pdsLatLng);
		createTextOptions("普渡寺", pdsLatLng);
		
		//6折线坐标 天安门广场
		LatLng tamLatLng = new LatLng(Constants.POLYLINE_LATITUDE_5, Constants.POLYLINE_LONGITUDE_5);
		latLngs.add(tamLatLng);
		createTextOptions("天安门广场", tamLatLng);
		
		//7折线终点坐标  老舍茶馆
		LatLng lscgLatLng = new LatLng(Constants.STOP_LATITUDE, Constants.STOP_LONGITUDE);
		latLngs.add(lscgLatLng);
		createTextOptions("终点", lscgLatLng);
		
		
		//设置折线坐标点列表
		polylineOptions.points(latLngs);
		
		map.addOverlay(polylineOptions);
	}
	
	/**
	 * 创建一个文字的覆盖物
	 * @param text 文字显示
	 * @param latLog 坐标
	 */
	public void createTextOptions(String text, LatLng latLog){
		TextOptions textOptions = new TextOptions();
		//设置文字覆盖物背景颜色
		textOptions.bgColor(Color.GRAY);
		//设置文字覆盖物字体颜色，默认黑色
		textOptions.fontColor(Color.RED);
		//设置文字覆盖物字体大小 px sp
		textOptions.fontSize(20);
		//设置文字覆盖物地理坐标
		textOptions.position(latLog);
		
		//设置文字覆盖物的文字内容
		textOptions.text(text);
		
		//最终添加到地图上
		map.addOverlay(textOptions);
	}

	/**
	 * 创建圆的覆盖物 画圆
	 */
	private void createCircleOptions() {
		//1.定义创建圆的选项
		CircleOptions circleOptions = new CircleOptions();
		//2.初始化圆
		//LatLng(double latitude, double longitude)
		LatLng latLng = new LatLng(Constants.TAM_LATITUDE, Constants.TAM_LONGITUDE);
		//设置圆心坐标
		circleOptions.center(latLng);
		
		//设置圆填充颜色 argb 前面有透明度
		circleOptions.fillColor(Color.argb(33, 0, 255, 0));
		
		//设置圆半径
		circleOptions.radius(500);
		
		//边框类，可以给圆、多边形设置一个边框
		//1边框的宽度 像素 2边框的颜色
		Stroke stroke = new Stroke(3, Color.RED);
		//设置圆边框信息
		circleOptions.stroke(stroke);
		
		//地图添加一个覆盖物
		map.addOverlay(circleOptions);
	}

	/**
	 * 清空覆盖物
	 * @param view
	 */
	public void clear(View view){
		//清空地图所有的 Overlay 覆盖物以及 InfoWindow
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
