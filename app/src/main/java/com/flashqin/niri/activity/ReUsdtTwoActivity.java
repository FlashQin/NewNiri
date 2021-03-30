package com.flashqin.niri.activity;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flashqin.niri.R;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.bean.NewPaySuccess;
import com.flashqin.niri.net.BaseObserver;
import com.gyf.immersionbar.ImmersionBar;
import com.rxjava.rxlife.RxLife;

import net.qiujuer.genius.ui.widget.Button;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

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
        String amount = getIntent().getStringExtra("amount");
        txtngnamount.setText(amount);
        txtrate.setText("0.26%");

        txtusdtamount.setText(roundByScale(Double.valueOf(amount) * 0.0026, 0) + "");

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
                finish();
                break;
            case R.id.btncancle:
                finish();
                break;
            case R.id.btnsure:
                if (edtemail.getText().toString().length() == 0) {
                    ToastUtils.showLong("please enter your email");
                }
                if (edteblock.getText().toString().length() == 0) {
                    ToastUtils.showLong("please enter your Blockchain transaction ID ");
                }
                getPayLink();
                break;
        }
    }

    public void getPayLink() {//获取这个支付链接
        ShowLoading();
        Map<String, Object> map = new HashMap<String, Object>();
        String json = createJsonString(map, "localeAmount", txtngnamount.getText().toString());
        json = createJsonString(map, "cryptoAmount", txtusdtamount.getText().toString());
        json = createJsonString(map, "identifier", edteblock.getText().toString());

        System.out.println("------------" + json);
        RxHttp.postJson("https://pay.kaymu.vip/v1/members/"+SPUtils.getInstance().getString("id", "0") +"/bitcoin/usdt")
                .setJsonParams(json)
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            NewPaySuccess homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), NewPaySuccess.class);
//                            Map<String, Object> map = new HashMap<String, Object>();
//                            String json = createJsonString(map, "amount", homeListBean.getBody().getData().getAmount());
//                            json = createJsonString(map, "appId", homeListBean.getBody().getData().getAppId());
//                            json = createJsonString(map, "applyDate", homeListBean.getBody().getData().getApplyDate());
//                            json = createJsonString(map, "channel", homeListBean.getBody().getData().getChannel());
//                            json = createJsonString(map, "clientIp", homeListBean.getBody().getData().getClientIp());
//                            json = createJsonString(map, "clientSn", homeListBean.getBody().getData().getClientSn());
//                            json = createJsonString(map, "notifyUrl", homeListBean.getBody().getData().getNotifyUrl());
//                            json = createJsonString(map, "outOrderNo", homeListBean.getBody().getData().getOutOrderNo());
//                            json = createJsonString(map, "sign", homeListBean.getBody().getData().getSign());
//                            json = createJsonString(map, "userId", homeListBean.getBody().getData().getUserId());
                            // json = createJsonString(map, "email", homeListBean.getBody().getData().getEmail());
//                            if (tongdao.equals("910")) {
//                                //  json = createJsonString(map, "contactName", edtname.getText().toString());
//                            }
                            //  json = createJsonString(map, "contactName", edtname.getText().toString());
//                            json = "https://h5.kaymu.vip/pay.html?channel=" + homeListBean.getBody().getData().getChannel() + "&data=" + json;
//
//                            System.out.println("111111111///" + json);
                            Goto(WebActivity.class, "json", homeListBean.getBody().getData().getOrder_data());
                            ToastUtils.showShort("Succse");

                        } else

                            ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort(e.getMessage().toString());
                        HideLoading();
                    }
                });
    }
}
