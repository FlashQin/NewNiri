package com.flashqin.niri.activity;


import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.flashqin.niri.utlis.CommonUtils;
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


public class LoginActivity extends BaseActivity {


    boolean islook = false;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.imglook)
    ImageView imglook;
    @BindView(R.id.txtforget)
    TextView txtforget;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tvresiger)
    TextView tvresiger;
    @BindView(R.id.linbar)
    LinearLayout linbar;

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(linbar) //可以为任意view，如果是自定义xml实现标题栏的话，标题栏根节点不能为RelativeLayout或者ConstraintLayout，以及其子类
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imglook, R.id.btn_login, R.id.tvresiger, R.id.txtforget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imglook:
                if (islook == false) {
                    islook = true;
                    imglook.setBackgroundResource(R.drawable.kan1);
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    islook = false;
                    imglook.setBackgroundResource(R.drawable.kan);
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.btn_login:
                if (etPhone.getText().toString().length() == 0) {
                    ToastUtils.showShort("Please enter your phone number");
                    return;
                }
                if (etPassword.getText().toString().length() == 0) {
                    ToastUtils.showShort("Please enter your password");
                    return;
                }
                ShowLoading();
                Map<String, Object> map = new HashMap<String, Object>();
                String json = createJsonString(map, "password", etPassword.getText().toString().trim());
                json = createJsonString(map, "mobile", etPhone.getText().toString().trim());
                json = createJsonString(map, "version", CommonUtils.getVersionName(LoginActivity.this));
                System.out.println("----------" + json);
                RxHttp.postJson("/v1/members/sign-in")
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
                                    SPUtils.getInstance().put("id", resigerBean.getBody().getData().getId() + "");
                                    SPUtils.getInstance().put("username", resigerBean.getBody().getData().getName() + "");
                                    ACache.get(Utils.getApp()).put("USER_BEAN", GsonUtils.toJson(resigerBean));
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
                break;
            case R.id.tvresiger:
                Goto(ResigerActivity.class);
                break;
            case R.id.txtforget:
                Goto(ForgetFundPassActivity.class);
                break;
        }
    }
}
