package com.llb.nature.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llb.nature.R;
import com.llb.nature.adapter.WebApp1ListViewAdapter;
import com.llb.nature.domain.WebAppItem1Bean;
import com.llb.nature.util.PullToRefreshListView;
import com.llb.nature.util.PullToRefreshListView.OnRefreshListener;

public class WebApp1Fragment extends Fragment  implements OnRefreshListener{
	private View view=null;
	private ArrayList<WebAppItem1Bean> list;
	private PullToRefreshListView listView;
	private WebApp1ListViewAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list=new ArrayList<WebAppItem1Bean>(32);//默认是16
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view=inflater.inflate(R.layout.webapp1_fragment,null);
			listView=(PullToRefreshListView)view.findViewById(R.id.lv_webapp1);
			initList();
			adapter=new WebApp1ListViewAdapter(view.getContext(), list);
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if(parent!=null){
			parent.removeView(view);//先移除
		}
		
		listView.setAdapter(adapter);
		listView.setOnRefreshListener(this);//正在刷新数据监听
//		listView.setOnItemClickListener(this);//设置点击监听
		return view;
	}
	private void initList() {
		list.add(new WebAppItem1Bean("icon链接", "虎扑体育", "移动轻应用比赛真是极好的，出题水平很高！", false));
		list.add(new WebAppItem1Bean("icon链接", "虎扑体育", "移动轻应用比赛真是极好的，出题水平很高！", false));
		list.add(new WebAppItem1Bean("icon链接", "虎扑体育", "移动轻应用比赛真是极好的，出题水平很高！", true));
		list.add(new WebAppItem1Bean("icon链接", "虎扑体育", "移动轻应用比赛真是极好的，出题水平很高！", true));
		list.add(new WebAppItem1Bean("icon链接", "虎扑体育", "移动轻应用比赛真是极好的，出题水平很高！", false));
		list.add(new WebAppItem1Bean("icon链接", "虎扑体育", "移动轻应用比赛真是极好的，出题水平很高！", false));
		list.add(new WebAppItem1Bean("icon链接", "虎扑体育", "移动轻应用比赛真是极好的，出题水平很高！", false));
		list.add(new WebAppItem1Bean("icon链接", "虎扑体育", "移动轻应用比赛真是极好的，出题水平很高！", true));
		list.add(new WebAppItem1Bean("icon链接", "虎扑体育", "移动轻应用比赛真是极好的，出题水平很高！", false));
		
	}
	@Override
	public void onRefresh() {//下拉刷新
		// TODO Auto-generated method stub
		list.add(0,new WebAppItem1Bean("icon链接", "心浪新闻", "移动轻应用比赛真是极好的，出题水平很高！", true));
		list.add(1,new WebAppItem1Bean("icon链接", "移动3G", "移动轻应用比赛真是极好的，出题水平很高！", false));
		adapter.notifyDataSetChanged();
		listView.onRefreshComplete();
	}
	@Override
	public void onLoadMore() {//上拉
		// TODO Auto-generated method stub
		list.add(new WebAppItem1Bean("icon链接", "娃哈哈", "移动轻应用比赛真是极好的，出题水平很高！", true));
		list.add(new WebAppItem1Bean("icon链接", "找你妹", "移动轻应用比赛真是极好的，出题水平很高！", false));
		adapter.notifyDataSetChanged();
		listView.onRefreshComplete();
	}
}
