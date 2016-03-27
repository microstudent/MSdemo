package com.microstudent.app.msdemo.ColorfulAnimView.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.microstudent.app.msdemo.ColorfulAnimView.ColorfulAnimView;
import com.microstudent.app.msdemo.R;


public class ColorAnimActivity extends AppCompatActivity {
    private ColorfulAnimView animView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_anim);
        animView = (ColorfulAnimView) findViewById(R.id.anim_view);
    }

    public void startAnim(View view) {
        if (animView != null) {
            animView.startAnim();
        }
    }

    public void showDialog(View view) {
        AnimProgressDialog dialog = new AnimProgressDialog(this);
        dialog.show();
    }
}
