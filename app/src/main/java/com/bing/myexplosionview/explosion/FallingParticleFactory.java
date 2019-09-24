package com.bing.myexplosionview.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class FallingParticleFactory extends ParticleFactory {
    public static final int PART_WH = 14;//小球默认宽高


    @Override
    public Particle[][] generateParticles(Bitmap bitmap, Rect bound) {
        int w = bound.width();
        int h = bound.height();

        int partW_count = w / PART_WH;  //横向个数（列数）
        int partH_count = h / PART_WH;  //竖向个数（行数）
        partW_count = partW_count > 0 ? partW_count : 1;
        partH_count = partH_count > 0 ? partW_count : 1;

        int bitmap_part_w = bitmap.getWidth() / partW_count;  //列
        int bitmap_part_H = bitmap.getHeight() / partH_count;  //行

        Particle[][] particles = new Particle[partH_count][partW_count];
        for (int row = 0; row < partH_count; row++) {
            for (int colum = 0; colum < partW_count; colum++) {
                //获取当前粒子所在位置的颜色
                int color = bitmap.getPixel(colum * bitmap_part_w, row * bitmap_part_H);
                float x = bound.left + PART_WH * colum;
                float y = bound.top + PART_WH * row;
                //关联粒子对象
                particles[row][colum] = new FallingParticle(x, y, color, bound);
            }
        }
        return particles;

    }
}
