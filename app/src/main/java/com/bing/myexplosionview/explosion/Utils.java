package com.bing.myexplosionview.explosion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

public class Utils {
    public static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final Canvas CANVAS = new Canvas();
    private static final float DENSIY = Resources.getSystem().getDisplayMetrics().density;
    public static int dp2Px(int dp){
        return Math.round(dp*DENSIY);
    }

    public static Bitmap createBitmapFromView(View view){
        view.clearFocus();//使view失去焦点恢复成原本样式
        Bitmap bitmap = createBitmapSafely(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888,1);
        if (bitmap != null) {
            synchronized (CANVAS){
                CANVAS.setBitmap(bitmap);
                view.draw(CANVAS);
                CANVAS.setBitmap(null);
            }
        }
        return bitmap;
    }

    private static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width,height,config);
        }catch (Exception e){
            e.printStackTrace();
            if(retryCount>0){
                System.gc();
                return createBitmapSafely(width,height,config,retryCount);
            }
            return null;
        }
    }
}
