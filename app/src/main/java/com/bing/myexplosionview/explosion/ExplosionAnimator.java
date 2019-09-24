package com.bing.myexplosionview.explosion;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

//粒子控制器（主要是控制动画的进度）
public class ExplosionAnimator extends ValueAnimator {
    private static int default_duration = 1500;
    private ParticleFactory mParticleFactory;//粒子工厂
    private Particle[][] mParticle;//粒子对象
    private View mContainter;//field
    private Paint mPaint;

    public ExplosionAnimator(View mContainter, Bitmap bitmap, Rect bound,ParticleFactory mParticleFactory) {
        this.mParticleFactory = mParticleFactory;
        this.mContainter = mContainter;
        mPaint = new Paint();
        setFloatValues(0f,1f);
        setDuration(default_duration);
        mParticle = mParticleFactory.generateParticles(bitmap,bound);
    }
    //绘制
    public void draw(Canvas canvas){
        if(!isStarted()){
            //动画未开始
            return;
        }
        //所有的粒子开始运功
        for (Particle[] particles : mParticle) {
            for (Particle particle : particles) {
                particle.advance(canvas,mPaint, (Float) getAnimatedValue());
            }
        }
        mContainter.invalidate();

    }

    @Override
    public void start() {
        super.start();
        mContainter.invalidate();
    }
}
