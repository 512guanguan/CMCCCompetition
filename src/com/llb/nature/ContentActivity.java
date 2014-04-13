package com.llb.nature;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.llb.nature.adapter.ActionbarNewsPopupListviewAdapter;
import com.llb.nature.adapter.mViewPagerAdapter;
import com.llb.nature.fragment.News1Fragment;
import com.llb.nature.fragment.News2Fragment;
import com.llb.nature.fragment.News3Fragment;
import com.llb.nature.fragment.SoftWare1Fragment;
import com.llb.nature.fragment.SoftWare3Fragment;
import com.llb.nature.fragment.WebApp1Fragment;
import com.llb.nature.fragment.WebApp2Fragment;

public class ContentActivity extends FragmentActivity implements OnPageChangeListener,OnTouchListener{
	private int key=0;//0-资讯 1--软件
	private ActionBar actionBar;
	private View popView;
	private ActionbarNewsPopupListviewAdapter popupAdapter;
	private ListView popupListView;
	private ArrayList<String> popupList;//存储列表值
	private PopupWindow popupWindow;
	
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
//		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);//让ActionBar漂浮
		setContentView(R.layout.activity_content);
		key=this.getIntent().getIntExtra("key", 0);
		initActionBar();
		initTabTitle();//上面滑动的线条和标题头
		initViewPager();
	}
	private void initActionBar(){
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); 
		//actionBar.setDisplayShowTitleEnabled(true);//默认是true
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setTitle("你妹");//设置新的标题
		
		
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
			actionBar.setTitle("梦网资讯");
			title.get(0).setText("要闻");
			title.get(1).setText("体育");
			title.get(2).setText("娱乐");
			
			//关于右上角的弹出框
			popView=LayoutInflater.from(this).inflate(R.layout.actionbar_news_popup, null);
			popupList=new ArrayList<String>();
			popupList.add("娱乐");
			popupList.add("财经");
			popupList.add("股票");
			popupList.add("女性");
			popupList.add("体育");
			popupAdapter=new ActionbarNewsPopupListviewAdapter(this,popupList);
			popupListView=(ListView) popView.findViewById(R.id.lv_popup);//popupview上面的listview
			popupListView.setAdapter(popupAdapter);
			
			break;
		case 1://软件已经默认了
			actionBar.setTitle("MM商城");
			break;
		case 2://添加轻应用的页面
			actionBar.setTitle("轻应用");
			title.get(0).setText("热门");
			title.get(1).setText("分类");
			title.get(2).setText("发现");
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
			fragments.add(new WebApp2Fragment());//暂时使用跟轻应用一个页面
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			Log.i("Llb","离开");
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.content, menu);//渲染同样的ActionBar样式，可个性化
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case android.R.id.home://ActionBar的向上导航
		        Intent intent = new Intent(this, MainActivity.class);
		        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(intent);
		        return true;
		    case R.id.action_list_content://列表
		    	Log.i("llb", "我要弹出来哟");
		    	popupWindow=new PopupWindow(popView, 250, LayoutParams.WRAP_CONTENT);
		    	//popupWindow.showAtLocation(this.getCurrentFocus(), Gravity.BOTTOM, 0, 0);
		    	popupWindow.showAsDropDown(findViewById(R.id.action_list_content), 0, 0);
		    	popupWindow.setOutsideTouchable(true);//默认点击外围没反应
				popupWindow.setFocusable(true);//默认不可点击
		    	return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
	}
}
