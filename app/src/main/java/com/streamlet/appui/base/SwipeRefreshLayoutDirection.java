package com.streamlet.appui.base;

/**
 * Description: 刷新方向.<br/><br/>
 * Author: Create by Yu.Yao on 2016/9/13.<br/><br/>
 */
public enum SwipeRefreshLayoutDirection {

    DOWN(0),
    UP(1),
    BOTH(2),
    DISABLE(3);

    private int mValue;

    SwipeRefreshLayoutDirection(int value) {
        this.mValue = value;
    }

    public static SwipeRefreshLayoutDirection getFromInt(int value) {
        for (SwipeRefreshLayoutDirection direction : SwipeRefreshLayoutDirection.values()) {
            if (direction.mValue == value) {
                return direction;
            }
        }
        return BOTH;
    }

}
