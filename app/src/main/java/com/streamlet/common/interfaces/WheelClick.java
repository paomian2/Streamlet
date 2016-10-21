package com.streamlet.common.interfaces;

public class WheelClick {
	
	OnWheelClickInterface wheelClickInterface;
	
	public void setOnWheelClickInterface(OnWheelClickInterface wheelClickInterface){
		this.wheelClickInterface=wheelClickInterface;
	}
	
	public OnWheelClickInterface getInterface(){
		return wheelClickInterface;
	}
	
	public interface OnWheelClickInterface{
		public void onLeftClick(String content, int position);
		public void onRightClick(String content, int position);
	}

}
