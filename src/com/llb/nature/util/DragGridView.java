package com.llb.nature.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.llb.nature.MainActivity;
import com.llb.nature.adapter.DragGridViewAdapter;

public class DragGridView extends GridView {

	private int dragPosition;
	private int dropPosition;
	private int holdPosition;
	private int startPosition;
	private int specialPosition = -1;
	private int leftBottomPosition = -1;
	
	private int nColumns = 3;
	private int nRows;
	private int Remainder;
	
	private int itemTotalCount;	
	private int halfItemWidth;	

	private ImageView dragImageView = null;
	private ViewGroup dragItemView = null;

	private WindowManager windowManager = null;
	private WindowManager.LayoutParams windowParams = null;
	
	private int mLastX,xtox;
	private int mLastY,ytoy;
	private int specialItemY;
	private int leftBtmItemY;
	
	private String LastAnimationID;
	
	private boolean isCountXY = false;	
	private boolean isMoving = false;
	
	private OnMyLongItemClickListener onMyLongItemClickListener;
	
	//private ArrayList<ViewGroup> mItemViewList ;

	public DragGridView(Context context, AttributeSet attrs) {
		super(context, attrs);		
	}

	public DragGridView(Context context) {
		super(context);
	}

	boolean flag = false;

	public void setLongFlag(boolean temp) {
		flag = temp;
	}
	
	public boolean setOnItemLongClickListener(final MotionEvent ev) {
		this.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(arg2==(getCount()-1) && !MainActivity.show){//最后一个，且不处于拖动状态
					return true;//直接忽略
				}else if (arg2<2 && !MainActivity.show) {//前面两个，且不处于拖动状态
					return true;//直接忽略     资讯和MM
				}
				int x = (int) ev.getX();
				int y = (int) ev.getY();
				mLastX=x;mLastY=y;
				startPosition = dragPosition = dropPosition = arg2;
				if (dragPosition == AdapterView.INVALID_POSITION) {
					return false;
				}				
				ViewGroup itemView = (ViewGroup) getChildAt(dragPosition-getFirstVisiblePosition());
				if(!isCountXY){
					halfItemWidth = itemView.getWidth()/2;
				    int rows;
				    itemTotalCount = getCount();//一共有几个item
				    rows = itemTotalCount/nColumns;//行数 可能少一行
				    Remainder = itemTotalCount%nColumns;//多出几个
				    nRows =  Remainder == 0 ?  rows : rows + 1;//实际有几行
//				    specialPosition = itemTotalCount - 1 - Remainder;//?????
				    if(Remainder!=1)
				    	leftBottomPosition = nColumns*(nRows-1);
//				    if(Remainder == 0 || nRows == 1)
//				    	specialPosition = -1;			    
				    isCountXY = true;
				}
//			    if(specialPosition != dragPosition && dragPosition != -1){
//			    	System.out.println("specialP="+specialPosition);
//			        specialItemY = getChildAt(specialPosition).getTop();
//			    }else{
//			    	specialItemY = -1;
//			    }
//			    if(leftBottomPosition != dragPosition && dragPosition != -1){
//			    	leftBtmItemY = getChildAt(leftBottomPosition).getTop();
//			    }else{
//			    	leftBtmItemY = -1;
//			    }
				//通知主程序这是长按事件，返回true是为了消费这个事件，防止出现dragItemview在重新刷新页面后无处落脚的bug
				if(onMyLongItemClickListener!=null && !MainActivity.show){
					onMyLongItemClickListener.timeToChange();//把内部的长按事件传递出去，慢了一些
					return true;
				}
				
				dragItemView = itemView;
				itemView.destroyDrawingCache();
				itemView.setDrawingCacheEnabled(true);
				itemView.setDrawingCacheBackgroundColor(0x000000);
				Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache(true));
				Bitmap bitmap = Bitmap.createBitmap(bm, 8, 8, bm.getWidth()-8, bm.getHeight()-8);
				startDrag(bitmap, x, y);
				hideDropItem();
				itemView.setVisibility(View.INVISIBLE);				
				isMoving = false;
				
//				if(!MainActivity.show){
//					((DragGridViewAdapter) getAdapter()).setToShowDelItem(true);
//				}
				return false;//未消费事件
			};
		});
		return super.onInterceptTouchEvent(ev);
	}
	
	public void GetItemShadow(int x,int y){
		
		
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		//Log.i("llb","onInterceptTouchEvent"+MainActivity.show);
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
				boolean a=setOnItemLongClickListener(ev);
				Log.i("llb","onInterceptTouchEvent ACTION_DOWN :return "+a);
			return setOnItemLongClickListener(ev);
			
		}
		Log.i("llb","onInterceptTouchEvent:return "+super.onInterceptTouchEvent(ev));
		return super.onInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		if (dragImageView != null
				&& dragPosition != AdapterView.INVALID_POSITION) {
			
			
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				if(!isCountXY) {
					xtox = x-mLastX;
				    ytoy = y-mLastY;
				    isCountXY= true;
				}
				onDrag(x, y);
				if(!isMoving )
				    OnMove(x,y);			
				break;
			case MotionEvent.ACTION_UP:
				stopDrag();
				onDrop(x, y);
				break;
			}
		}
		Log.i("llb","onTouchEvent return "+super.onTouchEvent(ev));
		return super.onTouchEvent(ev);
	}

	private void startDrag(Bitmap bm, int x, int y) {
		stopDrag();
		windowParams = new WindowManager.LayoutParams();
		windowParams.gravity = Gravity.TOP | Gravity.LEFT;
		windowParams.x = dragItemView.getLeft();// + 8;
		windowParams.y = dragItemView.getTop()+dragItemView.getHeight();//Configure.screenDensity);
		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.alpha = 0.9f;//透明度 1表示全透明

		ImageView iv = new ImageView(getContext());//长按时弹出的图像
		iv.setImageBitmap(bm);
		windowManager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		windowManager.addView(iv, windowParams);
		
		dragImageView = iv;
	}
	
	public  void OnMove(int x, int y){
		int TempPosition = pointToPosition(x,y);
//		int sOffsetY = specialItemY == -1 ? y - mLastY : y - specialItemY - halfItemWidth;
//		int lOffsetY = leftBtmItemY == -1 ? y - mLastY : y - leftBtmItemY - halfItemWidth;
		if(TempPosition != AdapterView.INVALID_POSITION && TempPosition != dragPosition){
			dropPosition = TempPosition;
		}
//		else if(specialPosition != -1 && dragPosition == specialPosition && sOffsetY >= halfItemWidth){
//			dropPosition = (itemTotalCount - 1);
//		}
//		else if(leftBottomPosition != -1 && dragPosition == leftBottomPosition && lOffsetY >= halfItemWidth){
//			dropPosition = (itemTotalCount - 1);
//		}	
		if(dragPosition != startPosition)
			dragPosition = startPosition;
		int MoveNum = dropPosition - dragPosition;
		if(dragPosition != startPosition && dragPosition == dropPosition)
			MoveNum = 0;
//		Log.i("llb","dropPosition="+dropPosition+" dragPosition="+dragPosition+" MoveNum="+MoveNum);
		if(MoveNum != 0){
			int itemMoveNum = Math.abs(MoveNum);
			float Xoffset,Yoffset;
			for (int i = 0;i < itemMoveNum;i++){
				if(MoveNum > 0){
					holdPosition = dragPosition + 1;
					Xoffset = (dragPosition/nColumns == holdPosition/nColumns) ? (-1) : (nColumns -1);
					Yoffset = (dragPosition/nColumns == holdPosition/nColumns) ? 0 : (-1);
				}else{
					holdPosition = dragPosition - 1;
					Xoffset = (dragPosition/nColumns == holdPosition/nColumns) ? 1 : (-(nColumns-1));
					Yoffset = (dragPosition/nColumns == holdPosition/nColumns) ? 0 : 1;
				}
			    ViewGroup moveView = (ViewGroup)getChildAt(holdPosition);
			    Animation animation = getMoveAnimation(Xoffset,Yoffset);
			    if(null!=moveView){
			    	moveView.startAnimation(animation);
			    }
				
				dragPosition = holdPosition;
				if(dragPosition == dropPosition)
					LastAnimationID = animation.toString();
				final DragGridViewAdapter adapter = (DragGridViewAdapter)this.getAdapter();
				animation.setAnimationListener(new Animation.AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub
						isMoving = true;
					}
					@Override
					public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub
					}
					@Override
					public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
						String animaionID = animation.toString();
						if(animaionID.equalsIgnoreCase(LastAnimationID)){
							adapter.exchange(startPosition, dropPosition);
							startPosition = dropPosition;
							isMoving = false;
						}					
					}
				});	
			}
		}
	}
	
	private void onDrop(int x,int y){
	    final DragGridViewAdapter adapter = (DragGridViewAdapter) this.getAdapter();
		adapter.showDropItem(true);
		adapter.notifyDataSetChanged();	
//		System.out.println("在onDrop(x,y)函数");
	}

	private void onDrag(int x, int y) {
		if (dragImageView != null) {
//			System.out.println("在onDrag(x,y)函数");
			windowParams.alpha = 0.9f;
			windowParams.x = (x-mLastX-xtox)+dragItemView.getLeft();//+8;
			windowParams.y = (y-mLastY-ytoy)+dragItemView.getTop()+dragItemView.getHeight();//dragItemView.getTop();//+(int)(45*Configure.screenDensity);
			windowManager.updateViewLayout(dragImageView, windowParams);
		}
	}
	
	private void stopDrag() {
		if (dragImageView != null) {
//			System.out.println("在stopDrag()函数");
			windowManager.removeView(dragImageView);
			dragImageView = null;
		}
	}
	
	private void hideDropItem(){
//		System.out.println("在hideDropItem()函数");
		final DragGridViewAdapter adapter = (DragGridViewAdapter)this.getAdapter();
		adapter.showDropItem(false);
	}
	
	public Animation getMoveAnimation(float x,float y){
//		System.out.println("在getMoveAnimation(x,y)函数");
		TranslateAnimation go = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, x,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, y);
		go.setFillAfter(true);
		go.setDuration(200);	
		return go;
	}
	
//	
//	public Animation getDropAnimation(){
//		ScaleAnimation scale = new ScaleAnimation(1.2f,1.0f,1.2f,1.0f);
//		scale.setDuration(550);
//		scale.setFillAfter(true);
//		return scale;
//		
//	}
//	public Animation getDownAnimation(float x,float y){
//		AnimationSet set = new AnimationSet(true);
//		TranslateAnimation go = new TranslateAnimation(Animation.RELATIVE_TO_SELF, x, Animation.RELATIVE_TO_SELF, x, 
//				Animation.RELATIVE_TO_SELF, y, Animation.RELATIVE_TO_SELF, y);
//		go.setFillAfter(true);go.setDuration(500);
//		
//		AlphaAnimation alpha = new AlphaAnimation(0.0f, 0.0f);
//		alpha.setFillAfter(true);
//		alpha.setDuration(500);
//		
//		set.addAnimation(go);
//		set.addAnimation(alpha);
//		return set;
//	}
	//定义一个接口来实现对长按事件的监听
	public interface OnMyLongItemClickListener{
		public Void timeToChange();
	}
	//设置接口监听
	public void setOnMyLongItemClickListener(OnMyLongItemClickListener onMyLongItemClickListener){
		this.onMyLongItemClickListener=onMyLongItemClickListener;
	}
		
}















