package com.microstudent.app.msdemo.PullToZoomScrollView.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.microstudent.app.msdemo.PullToZoomScrollView.PullToZoomScrollViewEx;
import com.microstudent.app.msdemo.R;


/**
 * Created by 45517 on 2015/10/23.
 */
public class UserInfoFragment extends Fragment {
    private PullToZoomScrollViewEx pullToZoomScrollView;
    private TextView userNameView,descView,idView,phoneView,emailView, addressView;
    private ImageView sexView,avatarView,zoomView;

    private long userId;

    private View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_user_info, container, false);
            initViews(mContentView);
            setupViews(savedInstanceState);
        }
        // 缓存的mContentView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个mContentView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mContentView.getParent();
        if (parent != null) {
            parent.removeView(mContentView);
        }
        return mContentView;
    }

    protected void initViews(View view) {
        pullToZoomScrollView = (PullToZoomScrollViewEx) view.findViewById(R.id.zoom_scrollView);

        userNameView = (TextView) pullToZoomScrollView.findViewById(R.id.tv_username);
        descView = (TextView) pullToZoomScrollView.findViewById(R.id.tv_desc);
        idView = (TextView) pullToZoomScrollView.findViewById(R.id.tv_id);
        phoneView = (TextView) pullToZoomScrollView.findViewById(R.id.tv_phone);
        emailView = (TextView) pullToZoomScrollView.findViewById(R.id.tv_email);
        addressView = (TextView) pullToZoomScrollView.findViewById(R.id.tv_address);
        sexView = (ImageView) pullToZoomScrollView.findViewById(R.id.iv_sex);
        avatarView = (ImageView) pullToZoomScrollView.findViewById(R.id.iv_avatar);
        zoomView = (ImageView) pullToZoomScrollView.findViewById(R.id.iv_zoom);


        Window window = getActivity().getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

    }

    protected void setupViews(Bundle bundle) {
        zoomView.setImageResource(R.drawable.header);
    }
}
