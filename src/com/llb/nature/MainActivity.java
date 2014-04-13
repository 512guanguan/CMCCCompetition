package com.llb.nature;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.llb.nature.adapter.DragGridViewAdapter;
import com.llb.nature.adapter.LeftViewAdapter;
import com.llb.nature.domain.GridViewItemBean;
import com.llb.nature.domain.LeftViewItemBean;
import com.llb.nature.util.Configure;
import com.llb.nature.util.DragGridView;
import com.llb.nature.util.DragGridView.OnMyLongItemClickListener;

public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener, OnMyLongItemClickListener,OnItemClickListener {
	private ActionBar actionBar;
	private DragGridView gridView;
	private DragGridViewAdapter gridViewAdapter;
//	private ImageView deleteImageView;
//	private ArrayList<String> itemNames;//
	private ArrayList<GridViewItemBean> itemBeans;//用来放主页的item数据
	private Button finishButton;//底部的完成按钮
	public static  boolean show=false;//长按时显示的内容
	
	private float screenWidth;// 屏幕宽度
	private SlidingMenu sm;
	private ListView leftListView;// 左边页面的listview
	private LeftViewAdapter adapter;
	private ArrayList<LeftViewItemBean> list;//左边页面列表显示内容
	private ImageView imageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		initSlidingMenu();// 初始化右边滑屏，这里面有setContentView函数，必须最前面
		initLeftView();
		initMainView();
	}
	/**
	 * 初始化开源组件SlidingMenu
	 */
	private void initSlidingMenu() {
		// 实例化滑动菜单对象
		sm = getSlidingMenu();
		setContentView(R.layout.activity_main);// 设置当前的视图
		setBehindContentView(R.layout.left);// 设置左页视图

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
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//.TOUCHMODE_FULLSCREEN);// .TOUCHMODE_MARGIN);
		sm.setTouchmodeMarginThreshold(30);
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
		list.add(new LeftViewItemBean("我的消息",R.drawable.menu_msg));
		list.add(new LeftViewItemBean("我的评论",R.drawable.menu_comment));
		list.add(new LeftViewItemBean("我的下载",R.drawable.menu_download));
		list.add(new LeftViewItemBean("我的收藏",R.drawable.menu_favorite));
		list.add(new LeftViewItemBean("移动服务",R.drawable.menu_mobile));
		list.add(new LeftViewItemBean("设置",R.drawable.menu_settting));
		list.add(new LeftViewItemBean("注销",R.drawable.menu_logout));
//		list.add(new LeftViewItemBean(R.drawable.left_item5_exit, "注销"));
		adapter = new LeftViewAdapter(sm.getContext(), list);
		leftListView.setAdapter(adapter);
	}
	private void initMainView(){
		actionBar=getActionBar();
//		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);//支持向上返回parent，需要在设置里面弄一下
//		actionBar.setDisplayShowHomeEnabled(false);//这样把actionbar左边的icon去除了
		
		gridView=(DragGridView) findViewById(R.id.gridview);
		finishButton=(Button) findViewById(R.id.finish);
		
//		itemNames=new ArrayList<String>();
		itemBeans=new ArrayList<GridViewItemBean>();
		itemBeans.add(new GridViewItemBean("0", "资讯", ""	));
		itemBeans.add(new GridViewItemBean("1", "应用下载", ""));
		itemBeans.add(new GridViewItemBean("a1", "税务查询", "http://..."	));
		itemBeans.add(new GridViewItemBean("a2", "有信", "http://..."	));
		itemBeans.add(new GridViewItemBean("a3", "红孩子商城", "http://..."	));
		itemBeans.add(new GridViewItemBean("a4", "路透社", "http://..."	));
		itemBeans.add(new GridViewItemBean("a5", "新浪微博", "http://..."	));
		itemBeans.add(new GridViewItemBean("a6", "旅行翻译官", "http://..."	));
		itemBeans.add(new GridViewItemBean("a7", "今日头条", "http://..."	));
		itemBeans.add(new GridViewItemBean("a8", "中国电信", "http://..."	));
		itemBeans.add(new GridViewItemBean("", "", ""));
//		String[] names={"小米","大米","黑米","红米","紫米","二货","二逼","移动","联通",""};
//		for(String item:names){
//			itemNames.add(item);
//		}
		Configure.init(this);
		gridViewAdapter=new DragGridViewAdapter(this, itemBeans);
		gridView.setAdapter(gridViewAdapter);
		gridView.setOnMyLongItemClickListener(this);//设置item长按监听
		gridView.setOnItemClickListener(this);
		finishButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//跳到左边页面
//			Intent intent=new Intent(this, MainActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
			sm.toggle();//打开侧边栏
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	@Override
	public void onClick(View arg0) {
		show=false;
		finishButton.setVisibility(View.GONE);//操作完毕
		gridViewAdapter.setToShowDelItem(false);//隐藏删除上角标
		
	}
	@Override
	public Void timeToChange() {
		Log.i("llb","长按事件");
		show=true;
		finishButton.setVisibility(View.VISIBLE);//长按时按钮可见
		gridViewAdapter.setToShowDelItem(true);//显示按钮
		return null;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Log.i("llb","单击事件");
		final int lastOne=arg0.getCount()-1;//最后一个添加按钮的索引
		switch (position) {
		case 0://第一个
			Intent intent=new Intent(this, ContentActivity.class);
			intent.putExtra("key", 0);//资讯
			startActivity(intent);
			break;
		case 1://第二个
			Intent intent1=new Intent(this, ContentActivity.class);
			intent1.putExtra("key", 1);//应用下载
			startActivity(intent1);
			break;
		}
		if(position==lastOne){//最后一个添加按钮
			Intent intent2=new Intent(this, ContentActivity.class);
			intent2.putExtra("key", 2);//轻应用添加
			startActivity(intent2);
		}
	}
}
