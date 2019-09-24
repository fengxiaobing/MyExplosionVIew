package com.bing.myexplosionview.explosion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

//粒子特效场地
public class ExplosionField extends View {
    //可以同时执行多个动画
    private ArrayList<ExplosionAnimator> mExplosionAnimator;
    //可以实现不同的粒子效果
    private ParticleFactory mParticleFactory;
    //给控件添加点击事件
    private OnClickListener onClickListener;

    public ExplosionField(Context context, ParticleFactory particleFactory) {
        super(context);
        mParticleFactory = particleFactory;
        mExplosionAnimator = new ArrayList<>();
        //将动画区域绑定到Activity

        attach2Activity();

    }

    private void attach2Activity() {
        ViewGroup decorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(this);
    }

    /**
     * @param view 需要实现粒子效果的view
     */
    public void addListener(View view) {
        if(view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                addListener(viewGroup.getChildAt(i));
            }
        }else {
            view.setClickable(true);
            view.setOnClickListener(getOnClickListener());
        }
    }

    private OnClickListener getOnClickListener() {
        if(onClickListener == null){
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View view) {
              //开启粒子爆炸效果
              ExplosionField.this.explode(view);
                }
            };
        }
        return onClickListener;
    }
//动画开始的地方（主入口）
    private void explode(final View view) {
        //获取控件的位置
         final Rect rect = new Rect();//保存了控件的位置信息
        view.getGlobalVisibleRect(rect);
        if(rect.width() == 0 || rect.height() == 0){
            return;
        }
        //开始动画效果
        //1、抖动（缩小）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1f).setDuration(150);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setTranslationX((float) ((Utils.RANDOM.nextFloat()-0.5)*getWidth()*0.05));
                view.setTranslationY((float) ((Utils.RANDOM.nextFloat()-0.5)*getHeight()*0.05));
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束
                //2、粒子效果
                explode2(view,rect);
                view.setTranslationX(0);
                view.setTranslationY(0);
            }
        });
        //开启震动动画
      valueAnimator.start();




    }

    private void explode2(final View view, Rect rect) {
        //获取动画控制器
        final ExplosionAnimator animator = new ExplosionAnimator(this,Utils.createBitmapFromView(view),
                rect,mParticleFactory);
        //添加到动画集合
        mExplosionAnimator.add(animator);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setClickable(false);
                //缩小
                view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0).start();

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setClickable(true);
                view.animate().setDuration(150).scaleX(1f).scaleY(1f).alpha(1).start();
                //蒋动画从集合中移除
                mExplosionAnimator.remove(animator);
            }

        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //开启所有的动画
        for (ExplosionAnimator explosionAnimator : mExplosionAnimator) {
            explosionAnimator.draw(canvas);
        }
    }
}
