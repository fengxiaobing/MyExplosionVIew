package com.bing.myexplosionview.explosion;

import android.graphics.Canvas;
import android.graphics.Paint;

//粒子对象
public abstract class Particle {
    float cx;
    float cy;
    int color;

    public Particle(float cx, float cy, int color) {
        this.cx = cx;
        this.cy = cy;
        this.color = color;
    }

    //计算  运动规则
    protected abstract void calculate(float factor);

    //绘制  粒子类型（样式）
    protected abstract void draw(Canvas canvas, Paint paint);

    //逐步绘制方法
    public void advance(Canvas canvas, Paint paint,float factor){
        calculate(factor);
        draw(canvas,paint);
    }
}
