package com.flashqin.niri.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flashqin.niri.R;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.net.BaseObserver;
import com.flashqin.niri.utlis.CommonTimer;
import com.flashqin.niri.utlis.UtilTool;
import com.gyf.immersionbar.ImmersionBar;
import com.rxjava.rxlife.RxLife;

import net.qiujuer.genius.ui.widget.Button;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

public class ForgetFundPassActivity extends BaseActivity {


    @BindView(R.id.edtname)
    EditText edtname;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.edtsms)
    EditText edtsms;
    @BindView(R.id.btncode)
    android.widget.Button btncode;
    @BindView(R.id.edtpassword)
    EditText edtpassword;
    @BindView(R.id.btnmodiftinfo)
    Button btnmodiftinfo;
    @BindView(R.id.linback)
    RelativeLayout linback;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgetfunpwd;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(linback) //可以为任意view，如果是自定义xml实现标题栏的话，标题栏根节点不能为RelativeLayout或者ConstraintLayout，以及其子类
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public void initView() {


        String time = SPUtils.getInstance().getString("fdopt", "0");
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


    @OnClick({R.id.linback, R.id.btncode, R.id.btnmodiftinfo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linback:
                finish();
                break;
            case R.id.btncode:
                if (edtname.getText().toString().trim().length() < 8) {
                    ToastUtils.showShort("Please enter your phone number");
                    return;
                }
                ShowLoading();
                // Map<String, Object> map = new HashMap<String, Object>();
                // String json = createJsonString(map, "mobile", etPhone.getText().toString().trim());
                //System.out.println("----------" + json);
                RxHttp.postJson("/v1/mobiles/" + edtname.getText().toString().trim() + "/auth-code")
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

                                    ToastUtils.showShort("Send Success");
                                    SPUtils.getInstance().put("fdopt", UtilTool.gettimenow());

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
                if (edtname.getText().toString().length() < 8) {
                    ToastUtils.showShort("Please enter your correct phone number");
                    return;
                }
                if (edtsms.getText().toString().length() == 0) {
                    ToastUtils.showShort("SMS verification code");
                    return;
                }
                if (edtpassword.getText().toString().length() == 0) {
                    ToastUtils.showShort("please check your password");
                    return;
                }
                postResiger();
                break;

        }
    }

    public void postResiger() {
        Map<String, Object> map = new HashMap<String, Object>();
        String json = createJsonString(map, "password", edtpassword.getText().toString().trim());
        json = createJsonString(map, "authCode", edtsms.getText().toString().trim());
        // System.out.println("----------" + json);
        RxHttp.postJson("/v1/mobiles/" + edtname.getText().toString().trim() + "/forget-password")
                .setJsonParams(json)
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {

                            ToastUtils.showShort("Success");
                            finish();
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
}
