package com.llb.nature.fragment;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewSwitcher.ViewFactory;

import com.llb.nature.R;
import com.llb.nature.adapter.Software1ListViewAdapter;
import com.llb.nature.domain.SoftwareItem1Bean;
import com.llb.nature.util.MyImageSwitcher;
import com.llb.nature.util.Rotate3D;

public class SoftWare1Fragment extends Fragment implements OnClickListener{
	private View view=null;
	private ArrayList<SoftwareItem1Bean> list;
	private ListView listView;
	private Software1ListViewAdapter adapter;
	
	private MyImageSwitcher imageSwitcher;
	private float halfWidth,halfHeight;//imageswitcher的一半尺寸
	private long duration=600;//旋转动画持续时间
	private int index=0;//轮播到第几张图片
	private GestureDetector gestureDetector;//手势监听
	
	private ImageAsynTask imageAsynTask;
	
	private int[] imageList={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};//ArrayList<String> imageList;//存储轮播图片url
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list=new ArrayList<SoftwareItem1Bean>(32);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view=inflater.inflate(R.layout.software1_fragment,null);
			listView=(ListView) view.findViewById(R.id.lv_software1);
			initList();
			
			imageSwitcher=(MyImageSwitcher) view.findViewById(R.id.imageswitcher);
			imageSwitcher.setViewPager((ViewPager)getActivity().findViewById(R.id.pager));
//			halfHeight=imageSwitcher.getHeight()/2.0f;
//			halfWidth=imageSwitcher.getWidth()/2.0f;
//			Log.i("llb","尺寸"+halfWidth+"*"+halfHeight);
			gestureDetector=new GestureDetector(view.getContext(), new MyOnGestureListener());
			imageSwitcher.setFactory(new ViewFactory() {
				@Override
				public View makeView() {
					//生成一个显示页面，一张图片，要是复杂的可以利用xml设计
					ImageView imageView=new ImageView(view.getContext());
//					imageView.setBackgroundColor(color);
					imageView.setScaleType(ImageView.ScaleType.FIT_XY);
					imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
							LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
					return imageView;
				}
			});
//			imageAsynTask=new ImageAsynTask();
//			imageAsynTask.execute(index);
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if(parent!=null){
			parent.removeView(view);//先移除
		}
		
		adapter=new Software1ListViewAdapter(view.getContext(),list);
		listView.setAdapter(adapter);
		
		
		imageSwitcher.setImageResource(imageList[index]);
		imageSwitcher.setTag("imageswitcher1");
		imageSwitcher.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				halfHeight=imageSwitcher.getHeight()/2.0f;
				halfWidth=imageSwitcher.getWidth()/2.0f;
//				Log.i("llb","尺寸"+halfWidth+"*"+halfHeight);
				Log.i("llb","imageswitcher检测到了事件");
				boolean a=gestureDetector.onTouchEvent(event);
				Log.i("llb","a="+a);
				return true;
			}
		});
		
		imageAsynTask=new ImageAsynTask();
		imageAsynTask.execute(index);
//		halfHeight=imageSwitcher.getHeight()/2.0f;
//		halfWidth=imageSwitcher.getWidth()/2.0f;
		return view;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	private void  initList(){
		for(int i=0;i<5;i++){
			list.add(new SoftwareItem1Bean("图片url", "刀塔传奇", 3.2f, "1万+",
					"76.8MB", false, "2014年首款动作卡牌手游，推陈出新，颠覆传统卡牌游戏战斗体验"));
		}
		for(int i=0;i<4;i++){
			list.add(new SoftwareItem1Bean("图片url", "秦时明月", 4.2f, "10万+",
					"66.8MB", false, "百度多酷官方QQ群：787298989,心浪微博：“百度多酷”"));
		}
	}
	
	
	
	private class MyOnGestureListener implements GestureDetector.OnGestureListener{
		@Override
		public boolean onDown(MotionEvent e) {
			Log.i("llb","imageswitcher onDown");
			return true;
		}
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if(velocityX>0){//x正方向有速度 即向右滑
				Log.i("llb","velocityX>0");
				Rotate3D rotateIn=new Rotate3D(-90, 0, halfWidth, halfHeight);//新图进来 从-90°到0°，
				rotateIn.setDuration(duration);    
				rotateIn.setFillAfter(true);//运动保持
		        imageSwitcher.setInAnimation(rotateIn);   
		        Rotate3D rotateOut = new Rotate3D(0,90,halfWidth,halfHeight);//旧图退出去
		        rotateOut.setDuration(duration);    
		        rotateOut.setFillAfter(true);
		        imageSwitcher.setOutAnimation(rotateOut); 
		        
		        index-=1;//序号降一
		        index=(index==-1)?(imageList.length-1):index;//只有等于0时需要调整
		        
		        imageSwitcher.setImageResource(imageList[index]);//设置新图片
			}else if(velocityX<0){//手指左滑，沿X轴负向
				Rotate3D rotateIn=new Rotate3D(90, 0, halfWidth, halfHeight);//新图进来
				rotateIn.setDuration(duration);    
				rotateIn.setFillAfter(true);
		        imageSwitcher.setInAnimation(rotateIn);   
		        Rotate3D rotateOut = new Rotate3D(0,-90,halfWidth,halfHeight);//旧图出去
		        rotateOut.setDuration(duration);    
		        rotateOut.setFillAfter(true);
		        imageSwitcher.setOutAnimation(rotateOut); 
		        
		        index+=1;//序号加一
		        index=(index==imageList.length)?0:index;//只有等于0时需要调整
		        
		        imageSwitcher.setImageResource(imageList[index]);//设置新图片
			}
			Log.i("Llb","super.onFling()");
			return true;
		}
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.i("llb","imageswitcher onSingleTapUp=");
			return true;
		}
		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.i("llb","imageswitcher onShowPress");
		}
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.i("llb","imageswitcher onScroll");
			return true;
		}
		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.i("llb","imageswitcher onLongPress");
		}
	}
	
	class ImageAsynTask extends AsyncTask{
		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			Log.i("llb","输入的数据："+params[0]);
			int index=(Integer)params[0];
			for(int i=0;i<500;i++){
				try {
				Thread.sleep(5000);
				index=(index==0)?3:(index-1);//逆序
				publishProgress(index);//反馈
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(Object... values) {
			super.onProgressUpdate(values);
			Log.i("llb","得到的数据："+values[0]);
			halfHeight=imageSwitcher.getHeight()/2.0f;
			halfWidth=imageSwitcher.getWidth()/2.0f;
			
			Rotate3D rotateIn=new Rotate3D(-90, 0, halfWidth, halfHeight);//新图进来 从-90°到0°，
			rotateIn.setDuration(duration);    
			rotateIn.setFillAfter(true);//运动保持
	        imageSwitcher.setInAnimation(rotateIn);   
	        Rotate3D rotateOut = new Rotate3D(0,90,halfWidth,halfHeight);//旧图退出去
	        rotateOut.setDuration(duration);    
	        rotateOut.setFillAfter(true);
	        imageSwitcher.setOutAnimation(rotateOut); 
	        
	        imageSwitcher.setImageResource(imageList[(Integer)values[0]]);
		}
	}
	
}
