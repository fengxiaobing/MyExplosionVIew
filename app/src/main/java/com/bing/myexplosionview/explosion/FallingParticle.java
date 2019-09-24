package com.bing.myexplosionview.explosion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class FallingParticle extends Particle {
    float radius = FallingParticleFactory.PART_WH / 2;
    float alph = 1f;
    Rect mBound;//控制区域(获取改变的x、y)

    public FallingParticle(float cx, float cy, int color, Rect bound) {
        super(cx, cy, color);
        mBound = bound;

    }

    /**
     * @param factor 动态的百分比
     */
    @Override
    protected void calculate(float factor) {
        //位置的改变
        cx = (float) (cx + factor * Utils.RANDOM.nextInt(mBound.width()) * (Utils.RANDOM.nextFloat() - 0.5));
        cy = cy + factor * Utils.RANDOM.nextInt(mBound.height() / 2);
        radius = radius - factor * Utils.RANDOM.nextInt(2);
        alph = (1 - factor) * (1 + Utils.RANDOM.nextFloat());
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alph));
        canvas.drawCircle(cx,cy,radius,paint);
    }
}
