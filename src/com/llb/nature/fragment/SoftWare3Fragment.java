package com.llb.nature.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.llb.nature.R;
import com.llb.nature.adapter.Software3ListViewAdapter;
import com.llb.nature.domain.SoftwareItem3Bean;

public class SoftWare3Fragment extends Fragment{
	private View view=null;
	private ArrayList<SoftwareItem3Bean> list;
	private ListView listView;
	private Software3ListViewAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list=new ArrayList<SoftwareItem3Bean>(32);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view=inflater.inflate(R.layout.software3_fragment,null);
			listView=(ListView) view.findViewById(R.id.lv_software3);
			initList();
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if(parent!=null){
			parent.removeView(view);//先移除
		}
		
		adapter=new Software3ListViewAdapter(view.getContext(),list);
		listView.setAdapter(adapter);
		return view;
	}
	private void  initList(){
		for(int i=0;i<8;i++){
			list.add(new SoftwareItem3Bean("图片url", "找你妹", 3.2f, "1万+",
					"76.8MB", false));
		}
		for(int i=0;i<4;i++){
			list.add(new SoftwareItem3Bean("图片url", "秦时明月", 4.2f, "10万+",
					"66.8MB", false));
		}
	}
}
