package com.llb.nature.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.llb.nature.R;
import com.llb.nature.adapter.WebApp2GridviewAdapter;
import com.llb.nature.domain.WebAppItem2Bean;

public class WebApp2Fragment extends Fragment{
	private View view=null;
	private ArrayList<WebAppItem2Bean> list;
	private WebApp2GridviewAdapter adapter;
	private GridView gridView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list=new ArrayList<WebAppItem2Bean>();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view=inflater.inflate(R.layout.webapp2_fragment,null);
			gridView=(GridView) view.findViewById(R.id.gridview);
			
			initList();
			adapter=new WebApp2GridviewAdapter(view.getContext(), list);
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if(parent!=null){
			parent.removeView(view);//先移除
		}
		
		gridView.setAdapter(adapter);
		
		return view;
	}
	private void initList() {
		// TODO Auto-generated method stub
		for(int i=0;i<8;i++){
			list.add(new WebAppItem2Bean("图片url","游戏", "暗黑世界、神魔仙界、植物大战僵尸", 0));
		}
	}
}
