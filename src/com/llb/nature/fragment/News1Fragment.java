package com.llb.nature.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llb.nature.R;
import com.llb.nature.adapter.News1ListViewAdapter;
import com.llb.nature.domain.NewsItem1Bean;
import com.llb.nature.util.PullToRefreshListView;
import com.llb.nature.util.PullToRefreshListView.OnRefreshListener;

public class News1Fragment extends Fragment implements OnRefreshListener{
	private View view=null;
	private ArrayList<NewsItem1Bean> list;
	private PullToRefreshListView listView;
	private News1ListViewAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list=new ArrayList<NewsItem1Bean>(32);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view=inflater.inflate(R.layout.news1_fragment,null);
			listView=(PullToRefreshListView) view.findViewById(R.id.lv_news1);
			
			initList();
			adapter=new News1ListViewAdapter(view.getContext(), list);
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if(parent!=null){
			parent.removeView(view);//先移除
		}
		listView.setAdapter(adapter);
		listView.setOnRefreshListener(this);
		return view;
	}
	private void initList() {
		// TODO Auto-generated method stub
		for(int i=0;i<7;i++){
			list.add(new NewsItem1Bean("图片网址", "詹姆斯有如科铁附身全心打铁", "4月2日出版的解放军报以整版篇幅，刊登各大军种、军区大员" +
					"署名文章，多角度阐述中国梦、强军梦，支持习主席指示。", "2014-3-22"));
		}	
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		list.add(0,new NewsItem1Bean("图片网址", "七大军区司令员等集体发声 响应习近平指示", "18名将领从多个角度阐释了习近平提出的“中国梦”和“强军梦”，表达他们支持" +
				"习近平指示的态度。","2014-4-1"));
		list.add(1,new NewsItem1Bean("图片网址", "马航调查指向机上4吨山竹果 警方已掌握线索", "18名将领从多个角度阐释了习近平提出的“中国梦”和“强军梦”，表达他们支持" +
				"习近平指示的态度。","2014-4-1"));
		adapter.notifyDataSetChanged();
		listView.onRefreshComplete();
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		list.add(new NewsItem1Bean("图片网址", "文章出轨“红”遍全球 美联社BBC集体报道", "18名将领从多个角度阐释了习近平提出的“中国梦”和“强军梦”，" +
				"表达他们支持习近平指示的态度。","2014-4-1"));
		list.add(new NewsItem1Bean("图片网址", "小沈阳谈不雅视频：谁没看过黄片？", "18名将领从多个角度阐释了习近平提出的“中国梦”和“强军梦”，" +
				"表达他们支持习近平指示的态度。","2014-4-1"));
		adapter.notifyDataSetChanged();
		listView.onRefreshComplete();
	}
}
