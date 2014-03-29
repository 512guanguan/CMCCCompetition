package com.llb.nature;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.llb.nature.adapter.LeftViewAdapter;
import com.llb.nature.adapter.MyViewPagerAdapter;
import com.llb.nature.domain.LeftViewItemBean;
import com.llb.nature.fragment.News1Fragment;
import com.llb.nature.fragment.News2Fragment;
import com.llb.nature.fragment.News3Fragment;
import com.llb.nature.fragment.SoftWare1Fragment;
import com.llb.nature.fragment.SoftWare2Fragment;
import com.llb.nature.fragment.SoftWare3Fragment;
import com.llb.nature.fragment.WebAppFragment;

public class MainActivity extends SlidingFragmentActivity implements OnPageChangeListener,OnClickListener {
	private PagerAdapter pagerAdapter;
	private ViewPager viewPager;
	private PagerTabStrip pagerTabStrip;
	private ArrayList<String> tabTitleList;
	private float screenWidth;// 屏幕宽度
	private SlidingMenu sm;
	private ListView leftListView;// 左边页面的listview
	private LeftViewAdapter adapter;
	private ArrayList<LeftViewItemBean> list;//左边页面列表显示内容
	private ArrayList<TextView> title=new ArrayList<TextView>(7);
	private TextView tv_tab1,tv_tab2,tv_tab3;//上方的三个标题
	private ImageView iv_line;//标题下面的线条
	private int currentLineIndex=0;//记录当前所在的页面索引
	private int space;//标题底下每条线条占三分之一
	private int offset;//距离三分之一界面的距离
	private ImageButton mNews,mWebapp,mSoftware;//底部三个
	private ArrayList<Fragment> fragments=new ArrayList<Fragment>(7);//用来存储Fragment
	
	private LinearLayout linearLayout;//标题栏
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSlidingMenu();// 初始化右边滑屏，这里面有setContentView函数，必须最前面
		initLeftView();
		initMainView();
//		initNewsPager();
		initTabTitle();//上面滑动的线条和标题头
		initViewPager();
	}
	/**
	 * 初始化开源组件SlidingMenu
	 */
	private void initSlidingMenu() {
		// 实例化滑动菜单对象
		sm = getSlidingMenu();
		setContentView(R.layout.activity_main);// 设置当前的视图
		setBehindContentView(R.layout.left);// 设置左页视图

		// ActionBar actionBar=getActionBar();

		sm.setMode(SlidingMenu.LEFT);
		// 设置滑动阴影的宽度
		// sm.setShadowWidthRes(R.dimen.shadow_width);
		// 设置滑动阴影的图像资源
		// sm.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		// sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.5f);
		// 设置触摸屏幕的模式
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// .TOUCHMODE_MARGIN);
		// sm.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置左页的响应范围
		// sm.setTouchmodeMarginThreshold(60);//这个设置的是隔屏幕边缘多远开始响应
		// sm.setBehindOffset(50);//设置左页距离屏幕右边缘的距离，右页会补上

		WindowManager wManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wManager.getDefaultDisplay().getWidth();// 获取屏幕的宽度
		sm.setBehindWidth((int) (screenWidth * 0.8));// 设置左页的宽度
	}
	/**
	 * 初始化左边页面
	 */
	private void initLeftView() {
		// TODO Auto-generated method stub
		// 左边视图
		leftListView = (ListView) sm.getMenu().findViewById(R.id.left_listView);
		list = new ArrayList<LeftViewItemBean>();
		list.add(new LeftViewItemBean("我的消息"));
		list.add(new LeftViewItemBean("设置"));
		list.add(new LeftViewItemBean("注销"));
//		list.add(new LeftViewItemBean(R.drawable.left_item5_exit, "注销"));
		adapter = new LeftViewAdapter(sm.getContext(), list);
		leftListView.setAdapter(adapter);
	}
	private void initMainView(){
		linearLayout=(LinearLayout) findViewById(R.id.linear0);
		viewPager=(ViewPager) findViewById(R.id.pager);
		mNews=(ImageButton) findViewById(R.id.mNews);
		mWebapp=(ImageButton) findViewById(R.id.mWebapp);
		mSoftware=(ImageButton) findViewById(R.id.mSoftware);
		mNews.setOnClickListener(this);
		mWebapp.setOnClickListener(this);
		mSoftware.setOnClickListener(this);
	}
	/**
	 * 初始化资讯页面
	 */
//	private void initNewsPager(){
		//初始化各个控件
//		tv_tab1=(TextView) findViewById(R.id.tv_tab1);
//		tv_tab2=(TextView) findViewById(R.id.tv_tab2);
//		tv_tab3=(TextView) findViewById(R.id.tv_tab3);
//		iv_line=(ImageView) findViewById(R.id.iv_title_line);
//		viewPager=(ViewPager) findViewById(R.id.pager);
//		pagerTabStrip=(PagerTabStrip) findViewById(R.id.pager_tab_strip);
		
//		title.add(0,tv_tab1);
//		title.add(1,tv_tab2);
//		title.add(2,tv_tab3);
//		title.add(3,tv_tab3);
//		title.add(4,tv_tab3);
//		title.add(5,tv_tab3);
//		title.add(6,tv_tab3);
//	}
	private void initTabTitle(){
		tv_tab1=(TextView) findViewById(R.id.tv_tab1);
		tv_tab2=(TextView) findViewById(R.id.tv_tab2);
		tv_tab3=(TextView) findViewById(R.id.tv_tab3);
		iv_line=(ImageView) findViewById(R.id.iv_title_line);
		pagerTabStrip=(PagerTabStrip) findViewById(R.id.pager_tab_strip);
		title.add(0,tv_tab1);
		title.add(1,tv_tab2);
		title.add(2,tv_tab3);
		title.add(3,tv_tab3);//轻应用
		title.add(4,tv_tab1);
		title.add(5,tv_tab2);
		title.add(6,tv_tab3);
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.blue));
		pagerTabStrip.setDrawFullUnderline(false);
		pagerTabStrip.setTextSpacing(10);
		tabTitleList=new ArrayList<String>(3);//新闻列表
		tabTitleList.add(0,"头条");//这个需要做成根据订阅更改的形式
		tabTitleList.add(1,"体育");
		tabTitleList.add(2,"娱乐");
		tabTitleList.add(3,"轻应用");
		tabTitleList.add(4,"软件1");
		tabTitleList.add(5,"软件2");
		tabTitleList.add(6,"软件3");
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
		fragments.add(new News1Fragment());//资讯
		fragments.add(new News2Fragment());
		fragments.add(new News3Fragment());
		fragments.add(new WebAppFragment());
		fragments.add(new SoftWare1Fragment());
		fragments.add(new SoftWare2Fragment());
		fragments.add(new SoftWare3Fragment());
		pagerAdapter=new MyViewPagerAdapter(getSupportFragmentManager(), fragments,tabTitleList);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(this);
	}
	/**
	 * 下面三个是OnPageChangeListener的接口函数
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
		Log.i("llb", "agr0="+arg0+" currentIndex="+currentLineIndex);
		if(arg0!=3){//不是轻应用页面
			linearLayout.setVisibility(View.VISIBLE);
			title.get(currentLineIndex).setTextColor(getResources().getColor(R.color.chocolate));//恢复
			title.get(arg0).setTextColor(getResources().getColor(R.color.blue));//设置当前
			if(arg0==2){
				title.get(0).setText("要闻");
				title.get(1).setText("体育");
				title.get(2).setText("娱乐");
			}else if(arg0==4){
				title.get(4).setText("榜单");
				title.get(5).setText("分类");
				title.get(6).setText("新锐");
			}
		}else {
			linearLayout.setVisibility(View.GONE);
			//下面这句修复颜色变换时的bug
			title.get(currentLineIndex).setTextColor(getResources().getColor(R.color.chocolate));
		}
		TranslateAnimation tAnimation = new TranslateAnimation(space
				* (currentLineIndex%4), space * (arg0%4), 0, 0);//0 1 2和4 5 6对4取模都是0 1 2 
		currentLineIndex=arg0;//必须要在上面语句之后
		tAnimation.setFillAfter(true);
		tAnimation.setDuration(100);
		iv_line.startAnimation(tAnimation);
		
		if (arg0 == 0) {
			// 如果当前是第一页，那么设置触摸屏幕的模式为全屏模式
			sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置成全屏响应
		} else {
			// 如果不是第一页，设置触摸屏幕的模式为边缘60px的地方
			sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			sm.setTouchmodeMarginThreshold(60);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mNews:
//			initNewsActivity();
			linearLayout.setVisibility(View.VISIBLE);
			viewPager.setCurrentItem(0);
			currentLineIndex=0;
			title.get(0).setText("要闻");
			title.get(1).setText("体育");
			title.get(2).setText("娱乐");
			break;
		case R.id.mWebapp:
			linearLayout.setVisibility(View.GONE);
			viewPager.setCurrentItem(3);
			currentLineIndex=3;
			break;
		case R.id.mSoftware:
			viewPager.setCurrentItem(4);
			currentLineIndex=4;
			linearLayout.setVisibility(View.VISIBLE);
			title.get(4).setText("榜单");
			title.get(5).setText("分类");
			title.get(6).setText("新锐");
			break;
		}
		
	}
}
