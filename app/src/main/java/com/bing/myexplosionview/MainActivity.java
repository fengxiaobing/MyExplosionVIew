package com.bing.myexplosionview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bing.myexplosionview.explosion.ExplosionField;
import com.bing.myexplosionview.explosion.FallingParticleFactory;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
        Button button = findViewById(R.id.button);
        ExplosionField explosionField = new ExplosionField(this,new FallingParticleFactory());
        explosionField.addListener(tv);
        explosionField.addListener(button);
    }
}
