package com.streamlet.common.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.streamlet.R;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.base.AppApplication;
import com.streamlet.base.Config;
import com.streamlet.common.interfaces.WheelClick;
import com.streamlet.common.widget.CustomProgressDialog;
import com.streamlet.common.widget.ProgressWheel;
import com.streamlet.common.widget.WheelView;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UIHelper {

    public static final int ACT_TRAN_HEAD = 43;

    public static UIHelper getInstance() {
        return new UIHelper();
    }

    /**
     * 获取屏幕分辨率：宽
     *
     * @param context
     * @return
     */
    public static int getScreenPixWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        if (!(context instanceof Activity)) {
            dm = context.getResources().getDisplayMetrics();
            return dm.widthPixels;
        }

        WindowManager wm = ((Activity) context).getWindowManager();
        if (wm == null) {
            dm = context.getResources().getDisplayMetrics();
            return dm.widthPixels;
        }

        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕分辨率：高
     *
     * @param context
     * @return
     */
    public static int getScreenPixHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        if (!(context instanceof Activity)) {
            dm = context.getResources().getDisplayMetrics();
            return dm.heightPixels;
        }

        WindowManager wm = ((Activity) context).getWindowManager();
        if (wm == null) {
            dm = context.getResources().getDisplayMetrics();
            return dm.heightPixels;
        }

        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;

    }


    /**
     * 默认时间LENGTH_SHORT
     *
     * @param msg
     */
    public static void showToast(Context context, Toast toast, String msg) {
        showToast(context, toast, msg, Toast.LENGTH_SHORT);
    }

    /***
     * @param msg
     * @param length 显示时间
     */
    public static void showToast(Context context, Toast toast, String msg, int length) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, length);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 获取当前手机的独立像素
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * dp2px
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * getDensity(AppApplication.getInstance()) + 0.5f);
    }

    /**
     * px2dp
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / getDensity(AppApplication.getInstance()) + 0.5f);
    }


    // 获取当前版本号
    public static int getVersionCode() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = AppApplication.getInstance().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo;
            packInfo = packageManager.getPackageInfo(AppApplication.getInstance().getPackageName(), 0);
            int versionCode = packInfo.versionCode;
            return versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 获取当前版本号
    public static String getVersionName() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = AppApplication.getInstance().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo;
            packInfo = packageManager.getPackageInfo(AppApplication.getInstance().getPackageName(), 0);
            String versionName = packInfo.versionName;
            return versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*// 设置头部信息
    public static void initTopTitle(final Activity activity, View top, String title, boolean btnLeftGone, boolean btnRightGone,
                                    final TittleClick tittleCick) {
        if (!StringUtils.isEmpty(title)) {
            ((TextView) top.findViewById(R.id.centerTitle)).setText(title);
        }
        if (top.findViewById(R.id.leftButton) != null) {
            if (btnLeftGone) {
                top.findViewById(R.id.leftButton).setVisibility(View.GONE);
            }
            top.findViewById(R.id.leftButton).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (tittleCick == null) {
                        activity.finish();
                    } else {
                        tittleCick.getInterface().onLeftClick();
                    }
                }
            });
        }
        if (top.findViewById(R.id.rightButton) != null) {
            if (btnRightGone) {
                top.findViewById(R.id.rightButton).setVisibility(View.GONE);
            }
            top.findViewById(R.id.rightButton).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (tittleCick == null) {
                        activity.finish();
                    } else {
                        tittleCick.getInterface().onRightClick();
                    }
                }
            });
        }
    }*/

    /********************************************
     * 滚动条对话框
     ***********************************************/

    public static CustomProgressDialog dialog;

    public static Dialog showProgressDialog(Context context, String message) {
        try {
            if (dialog != null) {
                dialog.cancel();
                dialog = null;
            }
            dialog = CustomProgressDialog.createDialog(context);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage(message);
            dialog.show();
            return dialog;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static Dialog showProgressDialog(Context context) {
        return showProgressDialog(context, null);
    }

    // 去掉进度条
    public static void cancleProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog.cancel();
        }
    }

    public static boolean isLogin(Context context) {
        String token = SharedPreferenceUtil.getInstance(context).getString(SharedPreferenceUtil.TOKEN);
        if (!StringUtils.isEmpty(token)) {
            return true;
        }
        return false;
    }

    /**
     * 滑动选择
     */
    private static String wheelTemp = "";
    private static int mposition = 0;

    public static void wheelDialog(BaseActivity activity, String[] PLANETS, final WheelClick wheelCick) {
        mposition = 0;
        wheelTemp = PLANETS[0];
        WheelView wheelview;
        final Dialog dialog;
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.view_wheelview, null);
        dialog = new AlertDialog.Builder(activity).create();
        dialog.show();
        dialog.setContentView(view);
        WindowManager m = activity.getWindowManager();
        Window dialogWindow = dialog.getWindow();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (d.getWidth());
        params.height = params.height;
        dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL);
        // 设置bottom的偏移量
        dialog.getWindow().setAttributes(params);
        wheelview = (WheelView) view.findViewById(R.id.main_wv);
        wheelview.setOffset(1);
        wheelview.setItems(Arrays.asList(PLANETS));
        wheelview.setOnWheelPickerListener(new WheelView.OnWheelPickerListener() {
            @Override
            public void wheelSelect(int position, String content) {
                wheelTemp = content;
                mposition = position;
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                wheelCick.getInterface().onRightClick(wheelTemp, mposition);
            }
        });
        view.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                wheelCick.getInterface().onRightClick(wheelTemp, mposition);
            }
        });
    }



    public static Map<String, Object> convertBeanToMap(Object bean) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = bean.getClass().getDeclaredFields();
        HashMap<String, Object> data = new HashMap<String, Object>();
        for (Field field : fields) {
            field.setAccessible(true);
            data.put(field.getName(), field.get(bean));
        }
        return data;
    }

    public static void openBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        LogUtil.d("down", "" + url);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    public static void cancleAllNotification(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

    public interface OnDialogClickListener {
        public void onClick();
    }

    /**
     * 有两个按钮的对话框
     */
    public static Dialog showTowButtonDialog(Context context, String title, String message, String btn_right, String btn_left,
                                             final OnDialogClickListener rightListener, final OnDialogClickListener leftListener) {
        try {
            final Dialog dialog = new Dialog(context, R.style.CustomDialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_tow_button, null);
            TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
            TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_msg);
            TextView btn1 = (TextView) contentView.findViewById(R.id.btn1);
            TextView btn2 = (TextView) contentView.findViewById(R.id.btn2);
            tvTitle.setText(title);
            tvMsg.setText(message);
            btn1.setText(btn_right);
            btn2.setText(btn_left);
            btn1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (rightListener != null)
                        rightListener.onClick();
                }
            });
            btn2.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (leftListener != null)
                        leftListener.onClick();
                }
            });
            dialog.setContentView(contentView);
            dialog.show();
            return dialog;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * 一个按钮的对话框
     */
    public static Dialog showOneButtonDialog(Context context, String title, String message, String btnStr, final OnDialogClickListener lis,
                                             boolean isCenter, boolean isHide) {
        try {
            final Dialog dialog = new Dialog(context, R.style.CustomDialog);
            dialog.setCanceledOnTouchOutside(isHide);
            View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_one_button, null);
            TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
            TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_msg);
            Button btnOk = (Button) contentView.findViewById(R.id.btn_ok);
            tvTitle.setText(title);
            tvMsg.setText(message);
            if (isCenter)
                tvMsg.setGravity(Gravity.CENTER);
            if (!StringUtils.isEmpty(btnStr))
                btnOk.setText(btnStr);
            btnOk.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (lis != null)
                        lis.onClick();
                }
            });
            dialog.setContentView(contentView);
            dialog.show();
            return dialog;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }


    /**
     * 从页面底部弹出dialog窗口
     */
    public static Dialog showButtonDialog(BaseActivity activity, final OnDialogClickListener topListener, final OnDialogClickListener OnDialogClickListener) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.row_dialog_style_bottom, null);
        final Dialog dialog = new AlertDialog.Builder(activity).create();
        dialog.show();
        dialog.setContentView(view);
        WindowManager m = activity.getWindowManager();
        Window dialogWindow = dialog.getWindow();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = d.getWidth();
        params.height = params.height;
        dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL);
        //设置bottom的偏移量
        //params.y=30;
        dialog.getWindow().setAttributes(params);

        view.findViewById(R.id.tv_follow).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topListener != null)
                    topListener.onClick();
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_delete).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnDialogClickListener != null)
                    OnDialogClickListener.onClick();
                dialog.dismiss();
            }
        });
        return dialog;
    }

  /*  *//**
     * 显示二维码dialog
     *//*
    public static void showQRDialog(Context context, CompanyDetailResponse companyDetailResponse, Bitmap qRBimap) {
        try {
            final Dialog dialog = new Dialog(context, R.style.CustomDialog);
            dialog.setCanceledOnTouchOutside(true);
            View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_qrcode, null);
            CircleImageView circleImageView = (CircleImageView) contentView.findViewById(R.id.cv_logo);
            TextView tv_companyName = (TextView) contentView.findViewById(R.id.tv_companyName);
            TextView tv_comapnyAdress = (TextView) contentView.findViewById(R.id.tv_companyAdress);
            ImageView img_QR = (ImageView) contentView.findViewById(R.id.img_QR);
            if (companyDetailResponse != null && companyDetailResponse.getPictures() != null && companyDetailResponse.getPictures().size() > 0) {
                UIHelper.imageNet(context, companyDetailResponse.getPictures().get(0).getPath(), circleImageView, false, R.drawable.icon_logo);
            }
            tv_companyName.setText(companyDetailResponse.getName());
            tv_comapnyAdress.setText(companyDetailResponse.getAddress());
            img_QR.setImageBitmap(qRBimap);
            dialog.setContentView(contentView);
            dialog.show();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }*/

    public static void initLoadView(SwipeRefreshLayout mSwipeLayout, final ProgressWheel progressWheel) {
        mSwipeLayout.setColorSchemeResources(R.color.top_bg);
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeLayout.setEnabled(false);
        progressWheel.spin();
//        progressWheel.setBarWidth(2);
        progressWheel.setBarWidth(dip2px(2));
    }

    public static void imageNet(Context context, String url, ImageView view, boolean isLocal, int defaltIcon) {
        if (!StringUtils.isEmpty(url) && url.startsWith("http")) {
            imageNet2(context, url, view, isLocal, defaltIcon);
        } else {
            Glide.with(context).load(isLocal ? url : getImgUrl(url)).dontAnimate().placeholder(defaltIcon).into(view);
        }

    }

    public static void imageNet2(Context context, String url, ImageView view, boolean isLocal, int defaltIcon) {
        Glide.with(context).load(isLocal ? url : url).dontAnimate().placeholder(defaltIcon).into(view);
    }

    public static String getImgUrl(String url) {
        return Config.TEST_IMG_BASE_URL + url;
    }

    /*
     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        if (w == h) {
            return bitmap;
        } else {
            int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

            int retX = w > h ? (w - h) / 2 : 0;// 基于原图，取正方形左上角x坐标
            int retY = w > h ? 0 : (h - w) / 2;

            // 下面这句是关键
            return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
        }

    }

    public static String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = AppApplication.getInstance().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(AppApplication.getInstance().getPackageName(), 0);
        } catch (NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    public static void setRatingbarScore(RatingBar ratingBar, float score) {
        float scoreNum = (float) 3.5;
        if (score >= 1 && score < 1.5) {
            scoreNum = 1;
        } else if (score >= 1.5 && score < 2) {
            scoreNum = (float) 1.5;
        } else if (score >= 2 && score < 2.5) {
            scoreNum = (float) 2;
        } else if (score >= 2.5 && score < 3) {
            scoreNum = (float) 2.5;
        } else if (score >= 3 && score < 3.5) {
            scoreNum = (float) 3;
        } else if (score >= 3.5 && score < 4) {
            scoreNum = (float) 3.5;
        } else if (score >= 4 && score < 4.5) {
            scoreNum = (float) 4;
        } else if (score >= 4.5 && score < 5) {
            scoreNum = (float) 4.5;
        } else if (score >= 5) {
            scoreNum = (float) 5;
        }
        ratingBar.setRating(scoreNum);
        ratingBar.setVisibility(View.VISIBLE);
    }

    /**
     * 电话号码验证
     */
    public static boolean checkPhoneAvalible(BaseActivity activity, String phoneNum, String msg) {
        try {
            if (!StringUtils.phoneNumberValid(phoneNum)) {
                activity.showToast(msg);
                return false;
            }
        } catch (Exception e) {
            activity.showToast("请输入正确的电话号码");
            return false;
        }
        return true;
    }

    /**
     * 拨打电话
     */
    public static void callPhone(BaseActivity context, String phoneNum) {
        if (StringUtils.isEmpty(phoneNum)) {
            context.showToast("商家还未开通电话服务");
        } else {
            Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
            context.startActivity(intent1);
        }
    }

    /**
     * TextView非空校验
     */
    public static boolean checkTv(BaseActivity activity, TextView tv, String msg) {
        String temp = tv.getText().toString().trim();
        if (StringUtils.isEmpty(temp)) {
            activity.showToast(msg);
            return false;
        }
        return true;
    }

    /**
     * 判断密码是否一致
     */
    public static boolean checkPwdSame(BaseActivity activity,TextView tv,TextView tvConfirm,String msg){
        String temp=tv.getText().toString().trim();
        String tempConfirm=tvConfirm.getText().toString().trim();
        if(temp.equals(tempConfirm)){
            return true;
        }
        activity.showToast(msg);
        return false;
    }
    private Drawable drawable_def[] = new Drawable[5];//系统生成默认的logo

   /* *//**
     * 将一个布局转换为一个图片：商家没有设置头像的时候，随机生成一张包含商家名字第一个字的图片
     *//*
    public Bitmap getRandomBitmap(BaseActivity activity, String companyName) {
        //商家没有提供图片时，随机显示颜色
        drawable_def[0] = activity.getResources().getDrawable(R.color.logo1);
        drawable_def[1] = activity.getResources().getDrawable(R.color.logo2);
        drawable_def[2] = activity.getResources().getDrawable(R.color.logo3);
        drawable_def[3] = activity.getResources().getDrawable(R.color.logo4);
        drawable_def[4] = activity.getResources().getDrawable(R.color.logo5);
        View view = activity.getLayoutInflater().inflate(R.layout.view_change_to_image, null);
        TextView tv_companyName = (TextView) view.findViewById(R.id.tv_companyName);
        tv_companyName.setText(companyName.substring(0, 1));
        int number = new Random().nextInt(5);
        tv_companyName.setBackground(drawable_def[number]);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }*/

}
