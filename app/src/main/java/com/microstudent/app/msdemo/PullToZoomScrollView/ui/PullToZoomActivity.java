package com.microstudent.app.msdemo.PullToZoomScrollView.ui;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.microstudent.app.msdemo.R;

public class PullToZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setupView();
    }
    public void setupView()
    {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);

        if (fragment == null) {
            fragment = new UserInfoFragment();

            fm.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }
}
