package com.microstudent.app.msdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.microstudent.app.msdemo.ColorfulAnimView.ui.ColorAnimActivity;
import com.microstudent.app.msdemo.CustomizeRecyclerView.ui.RecyclerViewActivity;
import com.microstudent.app.msdemo.DeletableEditText.ui.EditTextActivity;
import com.microstudent.app.msdemo.IndicatorView.ui.WelcomeActivity;
import com.microstudent.app.msdemo.PullToZoomScrollView.ui.PullToZoomActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mListView = (ListView) findViewById(R.id.lv);
        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mData));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positiEditableMembersAdapteron, long id) {
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                if (tv != null) {
                    switch (tv.getText().toString()){
                        case "DeletableEditText":
                            startActivity(new Intent(getApplicationContext(), EditTextActivity.class));
                            break;
                        case "ColorfulAnimView":
                            startActivity(new Intent(getApplicationContext(), ColorAnimActivity.class));
                            break;
                        case "IndicatorView":
                            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                            break;
                        case "PullToZoomScrollView":
                            startActivity(new Intent(getApplicationContext(), PullToZoomActivity.class));
                            break;
                        case "CustomizeRecyclerView":
                            startActivity(new Intent(getApplicationContext(), RecyclerViewActivity.class));
                            break;
                    }
                }
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        mData.add("DeletableEditText");
        mData.add("ColorfulAnimView");
        mData.add("IndicatorView");
        mData.add("PullToZoomScrollView");
        mData.add("CustomizeRecyclerView");
    }
}
