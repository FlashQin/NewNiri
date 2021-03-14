package com.flashqin.niri.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.flashqin.niri.MainActivity;
import com.flashqin.niri.R;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.bean.ResigerBean;
import com.flashqin.niri.net.BaseObserver;
import com.flashqin.niri.utlis.ACache;
import com.flashqin.niri.utlis.CommonTimer;
import com.flashqin.niri.utlis.CommonUtils;
import com.flashqin.niri.utlis.UtilTool;
import com.gyf.immersionbar.ImmersionBar;
import com.rxjava.rxlife.RxLife;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;


public class ResigerActivity extends BaseActivity {


    boolean islook = false;
    @BindView(R.id.linback)
    RelativeLayout linback;
    @BindView(R.id.edtname)
    EditText edtname;
    @BindView(R.id.edtusername)
    EditText edtusername;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.edtsms)
    EditText edtsms;
    @BindView(R.id.btncode)
    Button btncode;
    @BindView(R.id.edtpassword)
    EditText edtpassword;
    @BindView(R.id.edtrepassword)
    EditText edtrepassword;
    @BindView(R.id.btnmodiftinfo)
    net.qiujuer.genius.ui.widget.Button btnmodiftinfo;
    @BindView(R.id.edsharetcode)
    EditText edsharetcode;
    @BindView(R.id.txtlogoin)
    TextView txtlogoin;

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(linback) //可以为任意view，如果是自定义xml实现标题栏的话，标题栏根节点不能为RelativeLayout或者ConstraintLayout，以及其子类
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resiger;
    }

    @Override
    public void initView() {
        //txttitle.setText("Register");
        String time = SPUtils.getInstance().getString("reopt", "0");
        long sen = UtilTool.getTimeMISO(time, UtilTool.gettimenow());
        if (sen > 0) {
            new CommonTimer(sen * 1000 + 200, 1000, btncode).start();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.linback,R.id.txtlogoin, R.id.btncode, R.id.btnmodiftinfo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linback:
                finish();
                break;
            case R.id.txtlogoin:
                finish();
                break;
            case R.id.btncode:
                if (edtname.getText().toString().trim().length() < 10 || edtname.getText().toString().trim().length() > 11) {
                    ToastUtils.showShort("Please enter the correct mobile phone number");
                    return;
                }
                ShowLoading();
                // Map<String, Object> map = new HashMap<String, Object>();
                // String json = createJsonString(map, "mobile", etPhone.getText().toString().trim());
                //System.out.println("----------" + json);
                RxHttp.postForm("/v1/mobiles/" + edtname.getText().toString().trim() + "/auth-code")
                        // .setJsonParams(json)
                        .asObject(BaseBean.class)
                        .subscribeOn(Schedulers.io())
                        .as(RxLife.asOnMain(this))
                        .subscribe(new BaseObserver<BaseBean>() {
                            @Override
                            public void onNext(BaseBean baseBean) {
                                HideLoading();
                                if (baseBean.getHead().getCode() == 1) {
                                    new CommonTimer(150 * 1000 + 200, 1000, btncode).start();
                                    ToastUtils.showShort("Sent Success");
                                    SPUtils.getInstance().put("reopt", UtilTool.gettimenow());

                                } else
                                    ToastUtils.showShort(baseBean.getHead().getMessage());
                            }


                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                HideLoading();
                            }
                        });
                break;
            case R.id.btnmodiftinfo:
                if (edtname.getText().toString().trim().length() < 10 || edtname.getText().toString().trim().length() > 11) {
                    ToastUtils.showShort("Please enter your phone number");
                    return;
                }
                if (edtpassword.getText().toString().length() == 0) {
                    ToastUtils.showShort("Please enter your password");
                    return;
                }
                if (edtusername.getText().toString().length() == 0) {
                    ToastUtils.showShort("Please enter your username");
                    return;
                }
                if (edsharetcode.getText().toString().length() == 0) {
                    ToastUtils.showShort("Please enter your share code");
                    return;
                }
                if (!(edtpassword.getText().toString().equals(edtrepassword.getText().toString()))) {
                    ToastUtils.showShort("please check your password");
                    return;
                }
//                if (edtsms.getText().toString().length() == 0) {
//                    ToastUtils.showShort("Please enter the verification code");
//                    return;
//                }
                ShowLoading();

                //checkCode();
                postResiger();
                break;
        }
    }

    public void postResiger() {


        Map<String, Object> map = new HashMap<String, Object>();
        String json = createJsonString(map, "name", edtusername.getText().toString().trim());
        json = createJsonString(map, "password", edtpassword.getText().toString().trim());
        json = createJsonString(map, "mobile", edtname.getText().toString().trim());
        json = createJsonString(map, "shareCode", edsharetcode.getText().toString().trim());
        json = createJsonString(map, "version", CommonUtils.getVersionName(ResigerActivity.this));
        System.out.println("----------" + json);

        RxHttp.postJson("/v1/members/sign-up")
                .setJsonParams(json)
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            ResigerBean resigerBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), ResigerBean.class);
                            SPUtils.getInstance().put("TOKEN", resigerBean.getBody().getData().getToken());
                            ACache.get(Utils.getApp()).put("USER_BEAN", GsonUtils.toJson(resigerBean));
                            SPUtils.getInstance().put("username", resigerBean.getBody().getData().getName() + "");

                            SPUtils.getInstance().put("id", resigerBean.getBody().getData().getId() + "");
                            finish();
                            Goto(MainActivity.class);


                        } else


                            ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        HideLoading();
                    }
                });

    }

    public void checkCode() {
        RxHttp.get("/v1/mobiles/" + edtname.getText().toString().trim() + "/auth-code/" + edtsms.getText().toString().trim())
                // .setJsonParams(json)
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        // HideLoading();
                        if (baseBean.getHead().getCode() == 1) {

                            postResiger();

                        } else {
                            HideLoading();
                            ToastUtils.showShort(baseBean.getHead().getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        HideLoading();
                    }
                });
    }
}
