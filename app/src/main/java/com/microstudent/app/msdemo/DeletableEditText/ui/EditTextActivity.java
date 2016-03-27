package com.microstudent.app.msdemo.DeletableEditText.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.microstudent.app.msdemo.R;

public class EditTextActivity extends AppCompatActivity {
    private Button mPwButton;
    private boolean mIsPwVisitable = false;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        mPwButton = (Button) findViewById(R.id.password_eye);
        mPassword = (EditText) findViewById(R.id.editview_password);
        setupPwButton();

    }

    private void setupPwButton() {
        mPwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIsPwVisible(!mIsPwVisitable);
                if (mIsPwVisitable) {
                    mPwButton.setText("HIDE");
                    mIsPwVisitable = false;
                    setIsPwVisible(false);
                } else {
                    mPwButton.setText("SHOW");
                    mIsPwVisitable = true;
                    setIsPwVisible(true);
                }
            }
        });
    }

    private void setIsPwVisible(boolean visible) {
        if (visible) {
            mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            //定位到最后
            CharSequence text = mPassword.getText();
            if (text != null) {
                Spannable spanText = (Spannable) text;
                Selection.setSelection(spanText, text.length());
            }
        } else {
            mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            //定位到最后
            CharSequence text = mPassword.getText();
            if (text != null) {
                Spannable spanText = (Spannable) text;
                Selection.setSelection(spanText, text.length());
            }
        }
    }
}
