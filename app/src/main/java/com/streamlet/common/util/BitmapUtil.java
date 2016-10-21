package com.streamlet.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.streamlet.base.Constant;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class BitmapUtil {
	private final static String TAG = BitmapUtil.class.getSimpleName();
	public static Uri uri;
	public static String fileName = "";

	/**
	 * 计算缩放比例
	 *
	 * filename 图片路径 maxWidth 目标宽度 maxHeight 目标高度
	 */
	public static Bitmap scalePicture(String filename, int maxWidth, int maxHeight) {
		Bitmap bitmap = null;
		try {
			Options opts = new Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filename, opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int desWidth = 0;
			int desHeight = 0;

			// 缩放比例
			double ratio = 0.0;

			if (srcWidth > srcHeight) {
				ratio = srcWidth / maxWidth;
				desWidth = maxWidth;
				desHeight = (int) (srcHeight / ratio);
			} else {
				ratio = srcHeight / maxHeight;
				desHeight = maxHeight;
				desWidth = (int) (srcWidth / ratio);
			}
			// 设置输出宽度、高度
			Options newOpts = new Options();
			newOpts.inSampleSize = (int) (ratio) + 1;
			newOpts.inJustDecodeBounds = false;
			newOpts.outWidth = desWidth;
			newOpts.outHeight = desHeight;
			bitmap = BitmapFactory.decodeFile(filename, newOpts);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 计算缩放比例
	 *
	 * srcBitmap 原图 目标宽度 目标高度
	 */
	public static Bitmap scalePicture(Bitmap srcBitmap, int maxWidth, int maxHeight) {
		try {
			Bitmap bitmap = null;
			int srcWidth = srcBitmap.getWidth();
			int srcHeight = srcBitmap.getHeight();
			// 计算缩放率，新尺寸除原始尺寸
			float scaleWidth = ((float) maxWidth) / srcWidth;
			float scaleHeight = ((float) maxHeight) / srcHeight;

			// 创建操作图片用的matrix对象
			Matrix matrix = new Matrix();
			// 缩放图片动作
			matrix.postScale(scaleWidth, scaleHeight);
			// 创建新的图片
			bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcWidth, srcHeight, matrix, true);
			return bitmap;
		} catch (Throwable t) {
			t.printStackTrace();
			System.gc();
		}
		return srcBitmap;
	}

	public static Bitmap createBitmap(String bmPath, Options opt) {
		try {
			opt.inPreferredConfig = Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			FileInputStream fis = new FileInputStream(bmPath);
			return BitmapFactory.decodeStream(fis, null, opt);
		} catch (Throwable t) {
			LogUtil.log(t);
		}
		return null;
	}

    public static Bitmap createBitmap(Context context, int resId) {
        Bitmap bmp;
        Resources res = context.getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bmp = BitmapFactory.decodeResource(res, resId);
        } else {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            /**
             * 如果 inPurgeable 设为True的话表示使用BitmapFactory创建的Bitmap 用于存储Pixel的内存空间在系统内存不足时可以被回收，
             * 在应用需要再次访问Bitmap的Pixel时（如绘制Bitmap或是调用getPixel）， 系统会再次调用BitmapFactory decoder重新生成Bitmap的Pixel数组。
             * 为了能够重新解码图像，bitmap要能够访问存储Bitmap的原始数据。
             *
             * 在inPurgeable为false时表示创建的Bitmap的Pixel内存空间不能被回收， 这样BitmapFactory在不停decodeByteArray创建新的Bitmap对象，
             * 不同设备的内存不同，因此能够同时创建的Bitmap个数可能有所不同， 200个bitmap足以使大部分的设备重新OutOfMemory错误。 当isPurgable设为true时，系统中内存不足时，
             * 可以回收部分Bitmap占据的内存空间，这时一般不会出现OutOfMemory 错误。
             * API 21 时被忽略
             */
            opt.inPurgeable = true;
            /**
             * 配合inPurgeable使用，决定bitmap是否可以共享加载图片数据的引用，如果是false重新创建时会使用深层拷贝
             */
            opt.inInputShareable = true;
            bmp = BitmapFactory.decodeResource(res, resId, opt);
        }
        return bmp;
    }

	public static Bitmap createBitmap(Bitmap oriBm) {
		try {
			return Bitmap.createBitmap(oriBm);
		} catch (OutOfMemoryError e) {
			System.gc();
			LogUtil.log(e);
			try {
				LogUtil.e("System.out", "try to creat the bitmap again");
				return Bitmap.createBitmap(oriBm);
			} catch (Throwable t) {
				LogUtil.log(t);
			}
		} catch (Throwable t) {
			LogUtil.log(t);
		} finally {
			System.gc();
		}
		return null;
	}

	public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height, Matrix m, boolean filter) {
		try {
			return Bitmap.createBitmap(source, x, y, width, height, m, filter);
		} catch (OutOfMemoryError e) {
			System.gc();
			LogUtil.log(e);
			try {
				LogUtil.e("System.out", "try to creat the bitmap again");
				return Bitmap.createBitmap(source, x, y, width, height, m, filter);
			} catch (Throwable t) {
				LogUtil.log(t);
			}
		} catch (Throwable t) {
			LogUtil.log(t);
		} finally {
			System.gc();
		}
		return null;
	}

	public static Bitmap createBitmap(int width, int height, Config config) {
		try {
			return Bitmap.createBitmap(width, height, config);
		} catch (OutOfMemoryError e) {
			System.gc();
			LogUtil.log(e);
			try {
				LogUtil.e("System.out", "try to creat the bitmap again");
				return Bitmap.createBitmap(width, height, config);
			} catch (Throwable t) {
				LogUtil.log(t);
			}
		} catch (Throwable t) {
			LogUtil.log(t);
		} finally {
			System.gc();
		}
		return null;
	}

	public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height) {
		try {
			return Bitmap.createBitmap(source, x, y, width, height);
		} catch (OutOfMemoryError e) {
			System.gc();
			LogUtil.log(e);
			try {
				LogUtil.e("System.out", "try to creat the bitmap again");
				return Bitmap.createBitmap(source, x, y, width, height);
			} catch (Throwable t) {
				LogUtil.log(t);
			}
		} catch (Throwable t) {
			LogUtil.log(t);
		} finally {
			System.gc();
		}
		return null;
	}

	public static Bitmap createBitmap(InputStream is) {
		Options opt = new Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// opt.inSampleSize = 2;
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/** 使用c层代码早图片 */
	public static Bitmap createBitmap(String path) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return createBitmap(fis);
	}

	/** 回收不使用的bitmap */
	public static void recyledBitmap(Bitmap bitmap) {
		try {
			if (bitmap != null) {
				if (!bitmap.isRecycled()) {
					bitmap.recycle();
					bitmap = null;
				}
			}
			System.gc();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/** 获取指定View的截图 */
	public static Bitmap captureView(View view) throws Throwable {
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmShare = view.getDrawingCache();
		if (bmShare == null) {
			// 使用反射，强制将mViewFlags设置为正确的数值
			Field mViewFlagsField = View.class.getDeclaredField("mViewFlags");
			mViewFlagsField.setAccessible(true);
			mViewFlagsField.set(view, Integer.valueOf(402685952));
			bmShare = view.getDrawingCache();
		}
		return bmShare;
	}

	/** 获取屏幕的截图 */
	public static String captureView(Context context, String filename) {
		String path = Constant.TEMPORARY_FILE_PATH + filename;
		try {

			WindowManager windowManager = ((Activity) context).getWindowManager();

			Display display = windowManager.getDefaultDisplay();

			int w = display.getWidth();

			int h = display.getHeight();

			Bitmap bmShare = Bitmap.createBitmap(w, h, Config.ARGB_8888);

			// 2.获取屏幕

			View decorview = ((Activity) context).getWindow().getDecorView();

			decorview.setDrawingCacheEnabled(true);

			bmShare = decorview.getDrawingCache();
			CompressFormat format = CompressFormat.JPEG;
			OutputStream stream = new FileOutputStream(path);
			bmShare.compress(format, 100, stream);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return path;
	}

	/**
	 * 创建指定bitmap的倒影bitmap
	 *
	 * @param bitmap
	 *            原始的图片
	 * @param refMargin
	 *            倒影和原图之间的距离
	 * @param refLeng
	 *            倒影的长度
	 */
	public static Bitmap getInvertedImage(Bitmap bitmap, int refMargin, int refLeng) {
		int width = bitmap.getWidth();
		int bmHeight = bitmap.getHeight();
		int height = bmHeight + refMargin + refLeng;
		Bitmap bm = Bitmap.createBitmap(width, height, Config.ARGB_8888);

		Canvas canvas = new Canvas();
		canvas.setBitmap(bm);
		Paint paint = new Paint();
		canvas.drawBitmap(bitmap, 0, 0, paint);

		if (refLeng > 0) {
			Matrix matrix = new Matrix();
			matrix.preScale(1, -1);
			Bitmap refBm = Bitmap.createBitmap(bitmap, 0, bmHeight - refLeng, width, refLeng, matrix, false);
			canvas.drawBitmap(refBm, 0, bmHeight + refMargin, paint);
			refBm.recycle();

			paint.setAntiAlias(true);
			LinearGradient gradient = new LinearGradient(0, bmHeight + refMargin, 0, height, 0xff000000, 0x0000000, Shader.TileMode.CLAMP);
			paint.setShader(gradient);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
			canvas.drawRect(0, bmHeight + refMargin, width, height, paint);
		}

		return bm;
	}

	public static Bitmap getImageReflectionPart(Bitmap bitmap, int refLeng) {
		int width = bitmap.getWidth();
		int bmHeight = bitmap.getHeight();

		Bitmap bm = Bitmap.createBitmap(width, refLeng, Config.ARGB_8888);
		Canvas canvas = new Canvas();
		canvas.setBitmap(bm);
		Paint paint = new Paint();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap refBm = createBitmap(bitmap, 0, 0, width, bmHeight, matrix, true);
		canvas.drawBitmap(refBm, 0, 0, paint);
		refBm.recycle();

		paint.setAntiAlias(true);
		LinearGradient gradient = new LinearGradient(0, 0, 0, refLeng, 0xc0000000, 0x00000000, Shader.TileMode.CLAMP);
		paint.setShader(gradient);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		canvas.drawRect(0, 0, width, refLeng, paint);

		return bm;
	}

	/**
	 * 生成阴影
	 *
	 * @param bitmap
	 * @param shadow
	 * @return
	 */
	public static Bitmap getShadowBitmap(Bitmap bitmap, int shadow) {
		if (shadow <= 0) {
			return bitmap;
		}

		BlurMaskFilter filter = new BlurMaskFilter(shadow, BlurMaskFilter.Blur.OUTER);
		Paint shadowPaint = new Paint();
		shadowPaint.setMaskFilter(filter);

		int[] offsetXY = new int[] { shadow, shadow };
		return bitmap.extractAlpha(shadowPaint, offsetXY);
	}

	/**
	 * 获取图片的分辨率
	 *
	 * @param path
	 * @return
	 * @throws Throwable
	 */
	public static int[] getBitmapSize(String path) throws Throwable {
		Options opt = new Options();
		opt.inJustDecodeBounds = true;
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		FileInputStream fis = new FileInputStream(path);
		BitmapFactory.decodeStream(fis, null, opt);
		int[] size = new int[] { opt.outWidth, opt.outHeight };
		return size;
	}

	/**
	 * TODO 图片按比例大小压缩方法（根据路径获取图片并压缩)
	 *
	 * @param srcPath
	 *            SD卡路径
	 * @param reqWidth
	 *            需要的宽度
	 * @param reqHeight
	 *            需要的高度
	 * @return Bitmap
	 */
	public static Bitmap CompressImageByPath(String srcPath, int reqWidth, int reqHeight) {
		Options op = new Options();
		op.inJustDecodeBounds = true;// 只是读图片的属性
		BitmapFactory.decodeFile(srcPath, op);
		if (reqWidth == 0 && reqHeight == 0) {
			reqWidth = op.outWidth;
			reqHeight = op.outHeight;
		}
		op.inSampleSize = computeSampleSize(op, -1, reqWidth * reqHeight);// 设置缩放比例
		op.inJustDecodeBounds = false;
		op.inPreferredConfig = Config.RGB_565;
		return compressImage(BitmapFactory.decodeFile(srcPath, op), 80);// 压缩好比例大小后再进行质量压缩

	}

	/**
	 * 动态计算图片缩放大小的方法
	 *
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 *
	 * 质量压缩方法
	 *
	 * bitmap源 options开始压缩的质量 《=100
	 *
	 * @return 压缩之后的bitmap
	 */
	public static Bitmap compressImage(Bitmap bitmap, int options) {
		Bitmap bit = null;
		try {
			if (null == bitmap) {
				return bit;
			}
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, options, bout);// 质量压缩
																		// 100
																		// 表示不压缩
			System.out.println("*****************大少****************:" + bout.toByteArray().length / 1024);
			ByteArrayInputStream isBm = new ByteArrayInputStream(bout.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			bit = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
			bout.flush();
			bout.close();
			isBm.close();

			if (!bitmap.isRecycled()) {
				bitmap.recycle();// 记得释放资源，否则会内存溢出
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bit;
	}

	public static boolean storageState() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	// 压缩并获得流用于上传
	public static InputStream getISByBitmap(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 得到输出流
		bitmap.compress(CompressFormat.PNG, 100, baos);
		// 转输入流
		InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		return isBm;
	}

	// 根据时间创建名字
	public static String callTime() {

		long backTime = new Date().getTime();

		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date(backTime));

		int year = cal.get(Calendar.YEAR);

		int month = cal.get(Calendar.MONTH) + 1;

		int date = cal.get(Calendar.DAY_OF_MONTH);

		int hour = cal.get(Calendar.HOUR_OF_DAY);

		int minute = cal.get(Calendar.MINUTE);

		int second = cal.get(Calendar.SECOND);

		String time = "" + year + month + date + hour + minute + second;

		return time;

	}

	public static Bitmap getScreenBitmap(View v) {
		View view = v.getRootView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		bitmap = compressImage(bitmap);
		return bitmap;
	}

	/**
	 * 将Bitmap压缩到一定程度
	 *
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {
		Bitmap bitmap;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 100;
			while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				baos.reset();// 重置baos即清空baos
				image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
				options -= 10;// 每次都减少10
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
			isBm.reset();
			isBm.close();
		} catch (Exception e) {
			bitmap = image;
		}

		return bitmap;
	}

	/**
	 * 截图功能
	 *
	 * View v 返回byte[]
	 */
	public static byte[] shotScreen(View v) {
		byte[] buff = null;
		Bitmap bitmap = getScreenBitmap(v);
		if (bitmap != null) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100, stream);// (0 -
																		// 100)压缩文件
			buff = stream.toByteArray();
			System.out.println("bitmap got!");
			return buff;
		} else {
			return null;
		}

	}

	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// byte(字节)根据长度转成kb(千字节)和mb(兆字节)
	public static int bytes2kb(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024 * 1024);
		float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
		if (returnValue > 2) {
			return 8;
		} else if (returnValue > 1) {
			return 6;
		}
		BigDecimal kilobyte = new BigDecimal(1024);
		returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue();
		if (returnValue >= 700) {
			return 4;
		} else if (returnValue >= 500) {
			return 3;
		} else if (returnValue >= 300) {
			return 2;
		} else {
			return 1;
		}
	}

	public static Bitmap compressImage(Bitmap image, int reqWidth, int reqHeight) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Options newOpts = new Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = reqHeight;// 这里设置高度为800f
		float ww = reqWidth;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	// 写到sdcard中
	public static void write(Bitmap bm) throws IOException {
		if (bm == null) {
			return;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.PNG, 100, baos);// png类型
		FileOutputStream out = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + File.separator + "tesstttt.png"));
		out.write(baos.toByteArray());
		out.flush();
		out.close();
	}

	/**
	 *
	 * @param x
	 *            图像的宽度
	 * @param y
	 *            图像的高度
	 * @param image
	 *            源图片
	 * @param outerRadiusRat
	 *            圆角的大小
	 * @return 圆角图片
	 */
	public static Bitmap createFramedPhoto(int x, int y, Bitmap image, float outerRadiusRat) {
		// 根据源文件新建一个darwable对象
		Drawable imageDrawable = new BitmapDrawable(image);

		// 新建一个新的输出图片
		Bitmap output = Bitmap.createBitmap(x, y, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		// 新建一个矩形
		RectF outerRect = new RectF(0, 0, x, y);

		// 产生一个红色的圆角矩形
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

		// 将源图片绘制到这个圆角矩形上
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		imageDrawable.setBounds(0, 0, x, y);
		canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
		imageDrawable.draw(canvas);
		canvas.restore();

		return output;
	}

	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));

		Options options = new Options();
		options.inJustDecodeBounds = true;
		// Bitmap btBitmap=BitmapFactory.decodeFile(path);
		// System.out.println("原尺寸高度："+btBitmap.getHeight());
		// System.out.println("原尺寸宽度："+btBitmap.getWidth());
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 800) && (options.outHeight >> i <= 800)) {
				in = new BufferedInputStream(new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		// 当机型为三星时图片翻转
		// bitmap = Photo.photoAdapter(path, bitmap);
		// System.out.println("-----压缩后尺寸高度：" + bitmap.getHeight());
		// System.out.println("-----压缩后尺寸宽度度：" + bitmap.getWidth());
		return bitmap;
	}

	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 手机截屏 */
	public static Bitmap takeScreenShot(Activity activity) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();
		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay().getHeight();// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

	/**
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		if (null == context) {
			return 0;
		}
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getApplicationContext().getResources().getDisplayMetrics();
		return dm.heightPixels;
	}



	//api4.2以上才能使用的毛玻璃效果图
		/*private void blur(Context context,Bitmap bkg, View view, float radius) {
	        Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);  
	        Canvas canvas = new Canvas(overlay);  
	        canvas.drawBitmap(bkg, -view.getLeft(), -view.getTop(), null);  
	        RenderScript rs = RenderScript.create(context);  
	        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);  
	        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());  
	        blur.setInput(overlayAlloc);  
	        blur.setRadius(radius);  
	        blur.forEach(overlayAlloc);  
	        overlayAlloc.copyTo(overlay);  
	        view.setBackground(new BitmapDrawable(context.getResources(), overlay));  
	        rs.destroy();  
	    }  */

		/**毛玻璃效果图*/
		public static Bitmap fastblur(Bitmap sentBitmap, int radius) {

	        // Stack Blur v1.0 from
	        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
	        //
	        // Java Author: Mario Klingemann <mario at quasimondo.com>
	        // http://incubator.quasimondo.com
	        // created Feburary 29, 2004
	        // Android port : Yahel Bouaziz <yahel at kayenko.com>
	        // http://www.kayenko.com
	        // ported april 5th, 2012

	        // This is a compromise between Gaussian Blur and Box blur
	        // It creates much better looking blurs than Box Blur, but is
	        // 7x faster than my Gaussian Blur implementation.
	        //
	        // I called it Stack Blur because this describes best how this
	        // filter works internally: it creates a kind of moving stack
	        // of colors whilst scanning through the image. Thereby it
	        // just has to add one new block of color to the right side
	        // of the stack and remove the leftmost color. The remaining
	        // colors on the topmost layer of the stack are either added on
	        // or reduced by one, depending on if they are on the right or
	        // on the left side of the stack.
	        //
	        // If you are using this algorithm in your code please add
	        // the following line:
	        //
	        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

	        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

	        if (radius < 1) {
	            return (null);
	        }

	        int w = bitmap.getWidth();
	        int h = bitmap.getHeight();

	        int[] pix = new int[w * h];
	        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

	        int wm = w - 1;
	        int hm = h - 1;
	        int wh = w * h;
	        int div = radius + radius + 1;

	        int r[] = new int[wh];
	        int g[] = new int[wh];
	        int b[] = new int[wh];
	        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
	        int vmin[] = new int[Math.max(w, h)];

	        int divsum = (div + 1) >> 1;
	        divsum *= divsum;
	        int dv[] = new int[256 * divsum];
	        for (i = 0; i < 256 * divsum; i++) {
	            dv[i] = (i / divsum);
	        }

	        yw = yi = 0;

	        int[][] stack = new int[div][3];
	        int stackpointer;
	        int stackstart;
	        int[] sir;
	        int rbs;
	        int r1 = radius + 1;
	        int routsum, goutsum, boutsum;
	        int rinsum, ginsum, binsum;

	        for (y = 0; y < h; y++) {
	            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
	            for (i = -radius; i <= radius; i++) {
	                p = pix[yi + Math.min(wm, Math.max(i, 0))];
	                sir = stack[i + radius];
	                sir[0] = (p & 0xff0000) >> 16;
	                sir[1] = (p & 0x00ff00) >> 8;
	                sir[2] = (p & 0x0000ff);
	                rbs = r1 - Math.abs(i);
	                rsum += sir[0] * rbs;
	                gsum += sir[1] * rbs;
	                bsum += sir[2] * rbs;
	                if (i > 0) {
	                    rinsum += sir[0];
	                    ginsum += sir[1];
	                    binsum += sir[2];
	                } else {
	                    routsum += sir[0];
	                    goutsum += sir[1];
	                    boutsum += sir[2];
	                }
	            }
	            stackpointer = radius;

	            for (x = 0; x < w; x++) {

	                r[yi] = dv[rsum];
	                g[yi] = dv[gsum];
	                b[yi] = dv[bsum];

	                rsum -= routsum;
	                gsum -= goutsum;
	                bsum -= boutsum;

	                stackstart = stackpointer - radius + div;
	                sir = stack[stackstart % div];

	                routsum -= sir[0];
	                goutsum -= sir[1];
	                boutsum -= sir[2];

	                if (y == 0) {
	                    vmin[x] = Math.min(x + radius + 1, wm);
	                }
	                p = pix[yw + vmin[x]];

	                sir[0] = (p & 0xff0000) >> 16;
	                sir[1] = (p & 0x00ff00) >> 8;
	                sir[2] = (p & 0x0000ff);

	                rinsum += sir[0];
	                ginsum += sir[1];
	                binsum += sir[2];

	                rsum += rinsum;
	                gsum += ginsum;
	                bsum += binsum;

	                stackpointer = (stackpointer + 1) % div;
	                sir = stack[(stackpointer) % div];

	                routsum += sir[0];
	                goutsum += sir[1];
	                boutsum += sir[2];

	                rinsum -= sir[0];
	                ginsum -= sir[1];
	                binsum -= sir[2];

	                yi++;
	            }
	            yw += w;
	        }
	        for (x = 0; x < w; x++) {
	            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
	            yp = -radius * w;
	            for (i = -radius; i <= radius; i++) {
	                yi = Math.max(0, yp) + x;

	                sir = stack[i + radius];

	                sir[0] = r[yi];
	                sir[1] = g[yi];
	                sir[2] = b[yi];

	                rbs = r1 - Math.abs(i);

	                rsum += r[yi] * rbs;
	                gsum += g[yi] * rbs;
	                bsum += b[yi] * rbs;

	                if (i > 0) {
	                    rinsum += sir[0];
	                    ginsum += sir[1];
	                    binsum += sir[2];
	                } else {
	                    routsum += sir[0];
	                    goutsum += sir[1];
	                    boutsum += sir[2];
	                }

	                if (i < hm) {
	                    yp += w;
	                }
	            }
	            yi = x;
	            stackpointer = radius;
	            for (y = 0; y < h; y++) {
	                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
	                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

	                rsum -= routsum;
	                gsum -= goutsum;
	                bsum -= boutsum;

	                stackstart = stackpointer - radius + div;
	                sir = stack[stackstart % div];

	                routsum -= sir[0];
	                goutsum -= sir[1];
	                boutsum -= sir[2];

	                if (x == 0) {
	                    vmin[y] = Math.min(y + r1, hm) * w;
	                }
	                p = x + vmin[y];

	                sir[0] = r[p];
	                sir[1] = g[p];
	                sir[2] = b[p];

	                rinsum += sir[0];
	                ginsum += sir[1];
	                binsum += sir[2];

	                rsum += rinsum;
	                gsum += ginsum;
	                bsum += binsum;

	                stackpointer = (stackpointer + 1) % div;
	                sir = stack[stackpointer];

	                routsum += sir[0];
	                goutsum += sir[1];
	                boutsum += sir[2];

	                rinsum -= sir[0];
	                ginsum -= sir[1];
	                binsum -= sir[2];

	                yi += w;
	            }
	        }

	        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

	        return (bitmap);
	    }
		/**将制定的Bitmap保存到文件*/
		public  static void savePic(Bitmap b, String strFileName){
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(strFileName);
				if (null != fos)
				{
					b.compress(CompressFormat.PNG, 90, fos);
					fos.flush();
					fos.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

}
