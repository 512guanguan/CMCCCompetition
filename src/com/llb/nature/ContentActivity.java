package com.llb.nature;

import java.util.ArrayList;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.llb.nature.adapter.mViewPagerAdapter;
import com.llb.nature.fragment.News1Fragment;
import com.llb.nature.fragment.News2Fragment;
import com.llb.nature.fragment.News3Fragment;
import com.llb.nature.fragment.SoftWare1Fragment;
import com.llb.nature.fragment.SoftWare2Fragment;
import com.llb.nature.fragment.SoftWare3Fragment;
import com.llb.nature.fragment.WebApp1Fragment;
import com.llb.nature.fragment.WebApp2Fragment;

public class ContentActivity extends FragmentActivity implements OnPageChangeListener,OnTouchListener{
	private int key=0;//0-资讯 1--软件
	private PagerAdapter pagerAdapter;
	private ViewPager viewPager;
	private ArrayList<TextView> title=new ArrayList<TextView>(3);
	private TextView tv_tab1,tv_tab2,tv_tab3;//上方的三个标题
	private ImageView iv_line;//标题下面的线条
	private int currentLineIndex=0;//记录当前所在的页面索引
	private int space;//标题底下每条线条占三分之一
	private int offset;//距离三分之一界面的距离
	private ArrayList<Fragment> fragments=new ArrayList<Fragment>(3);//用来存储Fragment
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		key=this.getIntent().getIntExtra("key", 0);
		initTabTitle();//上面滑动的线条和标题头
		initViewPager();
	}
	private void initTabTitle(){
		tv_tab1=(TextView) findViewById(R.id.tv_tab1);
		tv_tab2=(TextView) findViewById(R.id.tv_tab2);
		tv_tab3=(TextView) findViewById(R.id.tv_tab3);
		iv_line=(ImageView) findViewById(R.id.iv_title_line);
		title.add(0,tv_tab1);
		title.add(1,tv_tab2);
		title.add(2,tv_tab3);
		switch (key) {
		case 0://待个性化定制
			title.get(0).setText("要闻");
			title.get(1).setText("体育");
			title.get(2).setText("娱乐");
			break;
		case 1://软件已经默认了
			break;
		}
		DisplayMetrics displayMetrics=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int widthPixels=displayMetrics.widthPixels;//屏幕的像素宽度
		int lineWidth=iv_line.getLayoutParams().width;//标题底下线条的长度
		space=widthPixels/3;//计算第二个tab底下的起点
//		Matrix matrix=new Matrix();
//		offset=(widthPixels/3-lineWidth)/2;//初始的第一个tab起点
//		matrix.postTranslate(offset, 0);
//		iv_line.setImageMatrix(matrix);
//		Log.i("llb","lineWidth="+lineWidth+" widthpixels="+widthPixels+" offset="+offset);
	}
	private void initViewPager(){
		viewPager=(ViewPager) findViewById(R.id.pager);
		switch (key) {
		case 0://资讯
			fragments.add(new News1Fragment());
			fragments.add(new News2Fragment());
			fragments.add(new News3Fragment());
			break;
		case 1://软件已经默认了
			fragments.add(new SoftWare1Fragment());
			fragments.add(new SoftWare2Fragment());
			fragments.add(new SoftWare3Fragment());
			break;
		case 2://轻应用
			fragments.add(new WebApp1Fragment());
			fragments.add(new WebApp2Fragment());
			fragments.add(new WebApp1Fragment());//暂时只为做页面，把它们设置成同一个
			break;
		}
		pagerAdapter=new mViewPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(this);
		if(key==1){
//			viewPager.setOnTouchListener(this);//软件界面
		}
		
	}
	/**
	 * OnPageChangeListener
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		switch (arg0) {
		case 0:
			iv_line.setVisibility(View.VISIBLE);//无操作
			break;
		case 1:
			iv_line.setVisibility(View.GONE);//正在滑动
			break;
		case 2:
//			Log.i("llb", "滑动结束currentIndex="+currentLineIndex);
			iv_line.setVisibility(View.VISIBLE);//滑动结束
			break;
		}
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	@Override
	public void onPageSelected(int arg0) {
		title.get(currentLineIndex).setTextColor(getResources().getColor(R.color.chocolate));//恢复
		title.get(arg0).setTextColor(getResources().getColor(R.color.blue));//设置当前
		TranslateAnimation tAnimation = new TranslateAnimation(space
				* (currentLineIndex%4), space * (arg0%4), 0, 0);//0 1 2和4 5 6对4取模都是0 1 2 
		currentLineIndex=arg0;//必须要在上面语句之后
		tAnimation.setFillAfter(true);
		tAnimation.setDuration(100);
		iv_line.startAnimation(tAnimation);
	}
	/**
	 * viewpager和上面的imageSwitcher焦点冲突了
	 * @param v
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		ImageSwitcher imageSwitcher=(ImageSwitcher) v.findViewById(R.id.imageswitcher);
		ImageSwitcher imageSwitcher=(ImageSwitcher) v.findViewWithTag("imageswitcher1");
		Rect rect=new Rect();
		if(imageSwitcher!=null){
			imageSwitcher.getDrawingRect(rect);
		}
//		imageSwitcher.getLocalVisibleRect(rect);
		if(rect.contains((int)event.getX(), (int)event.getY())&&imageSwitcher!=null){
			Log.i("llb","捕捉到了事件");
			return imageSwitcher.dispatchTouchEvent(event);
//			return a;//imageSwitcher.dispatchTouchEvent(event);
		}
		return false;
	}
}
