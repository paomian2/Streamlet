package com.streamlet.base;

import android.app.Activity;
import android.content.Intent;

import com.streamlet.R;
import com.streamlet.appui.activity.common.LoginActivity;
import com.streamlet.common.util.SharedPreferenceUtil;

import java.util.LinkedList;

/**
 * 这是一个单例模式的 activity管理器，没创建一个activity就加入到这个栈内，finish就将 它移除，
 * 当应用退出时，遍历栈内activity，并且finish退出
 * 
 * @author wj
 * 
 */
public class AppActivityManager {
	private static AppActivityManager instance = null;
	private LinkedList<Activity> acts;

	private AppActivityManager() {
		acts = new LinkedList<Activity>();
	};

	public static AppActivityManager getInstance() {
		if (instance == null) {
			instance = new AppActivityManager();
		}
		return instance;
	}

	public void goTo(Activity self, Class<? extends Activity> cls) {
		Intent it = new Intent(self, cls);
		self.startActivity(it);
	}

	public void goTo(Activity self, Intent it) {
		self.startActivity(it);
	}

	public void goFoResult(Activity self, Class<? extends Activity> cls, int RequestCode) {
		Intent it = new Intent(self, cls);
		goFoResult(self, it, RequestCode);
	}

	public void goFoResult(Activity self, Intent it, int RequestCode) {
		self.startActivityForResult(it, RequestCode);
	}

	public void goFoResultBottom(Activity self, Class<? extends Activity> cls, int RequestCode) {
		Intent it = new Intent(self, cls);
		goFoResultBottom(self, it, RequestCode);
	}

	public void goFoResultBottom(Activity self, Intent it, int RequestCode) {
		self.startActivityForResult(it, RequestCode);
		self.overridePendingTransition(R.anim.dock_bottom_enter, R.anim.dock_bottom_exit);
	}

	public void addActivity(Activity act) {
		acts.add(act);
	}

	public void removeActivity(Activity act) {
		if (acts != null && acts.indexOf(act) >= 0) {
			acts.remove(act);
		}
	}

	public void cleanActivity() {
		while (acts.size() != 0) {
			Activity act = acts.poll();
			act.finish();
		}
	}

	public Activity getTopActivity() {
		return (acts == null || acts.size() <= 0) ? null : acts.get(acts.size() - 1);
	}

	/**t跳转到需要登录状态的页面*/
	public void goToActivityOfLogin(Activity self, Class<? extends Activity> cls){
		if(SharedPreferenceUtil.isLogin(self)){
			Intent it = new Intent(self, cls);
			self.startActivity(it);
		}else{
			Intent it = new Intent(self, LoginActivity.class);
			self.startActivity(it);
		}
	}

	public void quit(Activity context) {
		/*NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancelAll();
		// 清除临时文件
		File temppraryFile = new File(Constant.TEMPORARY_FILE_PATH);
		if (temppraryFile.exists()){
			UIHelper.deleteAllFiles(temppraryFile);
		}
		cleanActivity();
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		// 2.2版本以下的直接使用restartPackage
		if (Build.VERSION.SDK_INT < 8) {
			am.restartPackage(context.getPackageName());
		} else {
			try {
				Method killBackgroundProcesses = am.getClass().getDeclaredMethod("killBackgroundProcesses", String.class);
				killBackgroundProcesses.setAccessible(true);
				killBackgroundProcesses.invoke(am, context.getPackageName());
			} catch (Exception e) {
				LogUtil.log(e);
			}
		}
		System.exit(0);
		*/
	}
}
