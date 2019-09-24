package com.bing.myexplosionview.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Parcel;

//粒子管理类
public abstract class ParticleFactory {
    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect rect);

}
