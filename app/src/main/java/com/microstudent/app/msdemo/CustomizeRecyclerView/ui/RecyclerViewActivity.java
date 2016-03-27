package com.microstudent.app.msdemo.CustomizeRecyclerView.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.microstudent.app.msdemo.CustomizeRecyclerView.EditableMembersAdapter;
import com.microstudent.app.msdemo.CustomizeRecyclerView.UserBean;
import com.microstudent.app.msdemo.R;
import com.microstudent.app.msdemo.util.FullyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewActivity extends AppCompatActivity {

    private List<UserBean> members;
    private RecyclerView recyclerView;
    private  EditableMembersAdapter adapter;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = (RecyclerView) findViewById(R.id.rv_members);

        initData();

        context = this;

        adapter = new EditableMembersAdapter(members, this, new EditableMembersAdapter.OnMemberDeleteListener() {
            @Override
            public void onMemberDelete(long user_id) {

            }
        });

        adapter.setAbleToAdd(true);
        adapter.setAbleToDelete(true);
        


        if(recyclerView!=null){
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setAdapter(adapter);

                    int member_size = (int) getResources().getDimension(R.dimen.member_size);
                    int width = recyclerView.getWidth();
                    recyclerView.setLayoutManager(new FullyGridLayoutManager(context, width / member_size));
                }
            });
        }

    }

    private void initData() {
        members = new ArrayList<>();
        for(int i  = 0;i<20;i++) {
            members.add(new UserBean("用户" + i, i));
        }
    }
}
