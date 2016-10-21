package com.streamlet.common.util;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 时间倒计时工具类
 * 
 * */
public abstract class TimerUtil {

	MyTimer timer;
	private int time=60;
	private int showTime;
	private boolean isRunning;

	public void setTime(int time){
		this.time=time;
	}


	public MyTimer startRunning(){
		showTime=time;
		timer=new MyTimer();
		final TimerTask timerTask=new TimerTask() {
			@Override
			public void run() {
				showTime--;
				Message msg=new Message();
				msg.what=showTime;
				showTimeHandler.sendMessage(msg);
			}
		};
		timer.schedule(timerTask, 1000, 1000);
		return timer;
	}

	class MyTimer extends Timer {
		public void resCancel(){
			this.cancel();
		};
	}


	private Handler showTimeHandler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				donInFinishTime(msg.what);
				isRunning=false;
				if(timer!=null)
					timer.resCancel();
				break;
			default:
				doInRunTime(msg.what);
				break;
			}
		};
	};


	public abstract void doInRunTime(int time);

	public abstract void donInFinishTime(int time);
}
