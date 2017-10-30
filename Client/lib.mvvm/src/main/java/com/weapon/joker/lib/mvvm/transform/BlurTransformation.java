package com.weapon.joker.lib.mvvm.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.RSRuntimeException;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.mvvm.transform.BlurTransformation
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/30
 *     desc   :
 * </pre>
 */

public class BlurTransformation extends BitmapTransformation {

    private static int MAX_RADIUS = 10;
    private static int DEFAULT_DOWN_SAMPLING = 1;

    private int radius;
    private int sampling;
    private Context mContext;


    public BlurTransformation(Context context) {
        this(context, MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }


    public BlurTransformation(Context context, int radius) {
        this(context, radius, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, int radius, int sampling) {
        this.mContext = context;
        this.radius = radius;
        this.sampling = sampling;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int scaledWidth = width / sampling;
        int scaledHeight = height / sampling;

        Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) sampling, 1 / (float) sampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(toTransform, 0, 0, paint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                bitmap = RSBlur.blur(mContext, bitmap, radius);
            } catch (RSRuntimeException e) {
                bitmap = FastBlur.blur(bitmap, radius, true);
            }
        } else {
            bitmap = FastBlur.blur(bitmap, radius, true);
        }

        return bitmap;
    }


    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}
