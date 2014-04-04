package com.llb.nature.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * 这个adapter里面有Fragment数组
 * @author llb
 *
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> fragments=new ArrayList<Fragment>(7);//需要添加到上面的Fragment
	private ArrayList<String> tabTitleList=new ArrayList<String>(7);//标题
	
	public MyViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	/**
	 * 自定义的构造函数
	 * @param fm
	 * @param fragments ArrayList<Fragment>
	 */
	public MyViewPagerAdapter(FragmentManager fm,
			ArrayList<Fragment> fragments, ArrayList<String> tabTitleList) {
		super(fm);
		this.fragments = fragments;
		this.tabTitleList=tabTitleList;
	}
	public MyViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}
	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);//返回Fragment对象
	}
	@Override
	public int getCount() {
		return fragments.size();//返回Fragment的个数
	}
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return tabTitleList.get(position);
	}
}
