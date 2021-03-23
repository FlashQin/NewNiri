package com.flashqin.niri.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.flashqin.niri.R;
import com.flashqin.niri.base.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;

import net.qiujuer.genius.ui.widget.Button;

import java.util.Hashtable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReUsdtTwoActivity extends BaseActivity {


    @BindView(R.id.imgback)
    ImageView imgback;
    @BindView(R.id.txttitle)
    TextView txttitle;
    @BindView(R.id.txtmore)
    TextView txtmore;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtngnamount)
    TextView txtngnamount;
    @BindView(R.id.txtrate)
    TextView txtrate;
    @BindView(R.id.txtusdtamount)
    TextView txtusdtamount;
    @BindView(R.id.edtemail)
    EditText edtemail;
    @BindView(R.id.edteblock)
    EditText edteblock;
    @BindView(R.id.btncancle)
    Button btncancle;
    @BindView(R.id.btnsure)
    Button btnsure;

    @Override
    public int getLayoutId() {
        return R.layout.activity_usdttwo;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(toolbar) //可以为任意view，如果是自定义xml实现标题栏的话，标题栏根节点不能为RelativeLayout或者ConstraintLayout，以及其子类
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public void initView() {

        txttitle.setText("USDT deposit confirmation");


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.imgback, R.id.btncancle, R.id.btnsure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgback:
                break;
            case R.id.btncancle:
                break;
            case R.id.btnsure:
                break;
        }
    }

}
