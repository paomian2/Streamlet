package com.streamlet.appui.activity.common;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.streamlet.R;
import com.streamlet.appui.base.BaseActivity;

import java.util.ArrayList;

public class GuideActivity extends BaseActivity {
			private ViewPager viewPager;
			private GuideAdapter adapter;
			private ImageView[] icon=new ImageView[3];
			private Button btn_come;
			private TranslateAnimation mShowAction,mHiddenAction;
			private Animation animation;
			@Override
			public void onCreate(Bundle savedInstanceState) {
				// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				setContentView(R.layout.act_guide);
				initUI();
				initData();
	}

	@Override
	protected void initUI() {
		/*// TODO Auto-generated method stub
		viewPager=(ViewPager) findViewById(id.viewpager);
		viewPager.setOnPageChangeListener(pageChangeListener);
		adapter=new GuideAdapter();
		viewPager.setAdapter(adapter);
		icon[0]=(ImageView) findViewById(id.icon1);
		icon[1]=(ImageView) findViewById(id.icon2);
		icon[2]=(ImageView) findViewById(id.icon3);
		btn_come=(Button) findViewById(id.btn_come);
		btn_come.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//MainActivity.luanch(activity);
			}});
		//动画显示
		mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAction.setDuration(500);
		//动画隐藏
		mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f);
		mHiddenAction.setDuration(500);


		animation = AnimationUtils.loadAnimation(this, R.anim.anim_view_show);*/

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		/*ImageView imageView = null;
		imageView = (ImageView) getLayoutInflater().inflate(layout.inflate_lead_icon, null);
		imageView.setImageResource(drawable.bg_luach);
		imageView.setScaleType(ScaleType.FIT_XY);
		adapter.addViewToAdapter(imageView);

		imageView = (ImageView) getLayoutInflater().inflate(layout.inflate_lead_icon, null);
		imageView.setImageResource(drawable.bg_launch);
		imageView.setScaleType(ScaleType.FIT_XY);
		adapter.addViewToAdapter(imageView);

		imageView = (ImageView) getLayoutInflater().inflate(layout.inflate_lead_icon, null);
		imageView.setImageResource(drawable.bg_luach);
		imageView.setScaleType(ScaleType.FIT_XY);
		adapter.addViewToAdapter(imageView);

		adapter.notifyDataSetChanged();*/
	}

	@Override
	public String setTag() {
		// TODO Auto-generated method stub
		return null;
	}


	private void resetPoint(int index){
		/*for (int i = 0; i < 3; i++) {
			icon[i].setBackgroundResource(drawable.icon_point_def);
		}
		icon[index].setBackgroundResource(drawable.icon_point_select);*/

	}


	private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			resetPoint(arg0);
			if(arg0==2){
				btn_come.startAnimation(animation);
				btn_come.setVisibility(View.VISIBLE);
			}else{
				//btn_come.startAnimation(mHiddenAction);
				btn_come.setVisibility(View.INVISIBLE);
			}
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};


	/** 引导面适配器*/
	class GuideAdapter extends PagerAdapter {
		/** 适配器内的所有View */
		private ArrayList<View> viewList = new ArrayList<View>();

		/** 向适配器内添加View */
		public void addViewToAdapter(View view) {
			if (view != null) {
				viewList.add(view);
			}
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = viewList.get(position);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = viewList.get(position);
			container.removeView(view);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
	}
}
